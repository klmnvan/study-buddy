using System.Security.Claims;
using FluentAssertions;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Moq;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Data;
using StudyBudyAPI.Dtos.Account;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Service;
using SignInResult = Microsoft.AspNetCore.Identity.SignInResult;
using Task = System.Threading.Tasks.Task;


namespace Tests.Controllers
{
    public class AccountControllerTests
    {
        private readonly ITokenService _tokenService;
        private readonly StudyBuddyDbContext _context;
        private readonly ILogger<AccountController> _logger;
        private Guid idUser;

        public AccountControllerTests()
        {
            _logger = new Mock<ILogger<AccountController>>().Object;
            var config = new Mock<IConfiguration>();
            config.Setup(c => c["JWT:SigningKey"]).Returns("2a3b4c5d6e7f8g9h1j0klmnpqrs0tuvwx1yz234567890abcde[ghij1klmnopqrstuvwxyz");
            _tokenService = new TokenService(config.Object);
            _context = GetDatabaseContext().Result;
        }

        private async Task<StudyBuddyDbContext> GetDatabaseContext()
        {

            var options = new DbContextOptionsBuilder<StudyBuddyDbContext>()
                    .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
                    .Options;
            var databaseContext = new StudyBuddyDbContext(options);
            databaseContext.Database.EnsureCreated();

            //Заполняем базу данных 
            if (await databaseContext.Users.CountAsync() <= 0)
            {
                idUser = Guid.NewGuid();
                databaseContext.Users.Add(
                    new User()
                    {
                        AppUser = new AppUser()
                        {
                            Id = idUser
                        },
                        IdUser = idUser,
                        Nickname = "Марк",
                        Email = "markmarkovich1@mail.ru"
                    });
                await databaseContext.SaveChangesAsync();

            }
            return databaseContext;
        }

        //Тестирование регистрации
        [Fact]
        public async Task AccountControllerTests_Register_ReturnOKAsync()
        {
            //Arrange
            var dto = new RegisterDto()
            {
                Nickname = "Mark",
                Email = "markmarkovich@mail.ru",
                Password = "12345678",
                ConfirmPassword = "12345678",
            };

            //Mocks
            var userManagerMock = new Mock<UserManager<AppUser>>(
                new Mock<IUserStore<AppUser>>().Object,
                new Mock<IOptions<IdentityOptions>>().Object,
                new Mock<IPasswordHasher<AppUser>>().Object,
                new IUserValidator<AppUser>[0],
                new IPasswordValidator<AppUser>[0],
                new Mock<ILookupNormalizer>().Object,
                new Mock<IdentityErrorDescriber>().Object,
                new Mock<IServiceProvider>().Object,
                new Mock<ILogger<UserManager<AppUser>>>().Object);
            userManagerMock
                .Setup(userManager => userManager
                .CreateAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.AddToRoleAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.FindByEmailAsync(dto.Email)).
                ReturnsAsync(new AppUser { UserName = dto.Email, Email = dto.Email });

            var signInManager = new Mock<SignInManager<AppUser>>(
                userManagerMock.Object, Mock.Of<IHttpContextAccessor>(),
                Mock.Of<IUserClaimsPrincipalFactory<AppUser>>(), null, null, null, null);
            
            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, signInManager.Object, _logger);

            //Act
            var result = await controller.Register(dto);

            //Assert
            if (result is OkObjectResult okResult)
            {
                var resultDto = (NewUserDto)okResult.Value;
                var newUserInContext = _context.Users.FirstOrDefault(it => it.Email == resultDto.Email);
                Assert.NotNull(newUserInContext);
            }
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<OkObjectResult>();
        }
        
        //Тестирование регистрации при уже существующем пользователе
        [Fact]
        public async Task AccountControllerTests_Register_ReturnBadRequest()
        {
            //Arrange
            //Пользователь, который уже есть в БД, создан в контексте
            var dto = new RegisterDto()
            {
                Nickname = "Mark",
                Email = "markmarkovich1@mail.ru",
                Password = "12345678",
                ConfirmPassword = "12345678",
            };

            //Mocks
            var userManagerMock = new Mock<UserManager<AppUser>>(
                new Mock<IUserStore<AppUser>>().Object,
                new Mock<IOptions<IdentityOptions>>().Object,
                new Mock<IPasswordHasher<AppUser>>().Object,
                new IUserValidator<AppUser>[0],
                new IPasswordValidator<AppUser>[0],
                new Mock<ILookupNormalizer>().Object,
                new Mock<IdentityErrorDescriber>().Object,
                new Mock<IServiceProvider>().Object,
                new Mock<ILogger<UserManager<AppUser>>>().Object);
            userManagerMock
                .Setup(userManager => userManager
                .CreateAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Failed(new IdentityError { Code = "DuplicateUserName" })));
            userManagerMock
                .Setup(um => um.AddToRoleAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.FindByEmailAsync(dto.Email)).
                ReturnsAsync(new AppUser { UserName = dto.Email, Email = dto.Email });

            var signInManager = new Mock<SignInManager<AppUser>>(
                userManagerMock.Object, Mock.Of<IHttpContextAccessor>(),
                Mock.Of<IUserClaimsPrincipalFactory<AppUser>>(), null, null, null, null);

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, signInManager.Object, _logger);

            //Act
            var result = await controller.Register(dto);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<BadRequestObjectResult>();
        }

        //Тестирование авторизации и получения токена
        [Fact]
        public async Task AccountControllerTests_Login_ReturnOKAsyncAndToken()
        {
            //Arrange
            var dto = new LoginDto()
            {
                Email = "markmarkovich1@mail.ru",
                Password = "12345678",
            };

            //Mocks
            var userManagerMock = new Mock<UserManager<AppUser>>(
                new Mock<IUserStore<AppUser>>().Object,
                new Mock<IOptions<IdentityOptions>>().Object,
                new Mock<IPasswordHasher<AppUser>>().Object,
                new IUserValidator<AppUser>[0],
                new IPasswordValidator<AppUser>[0],
                new Mock<ILookupNormalizer>().Object,
                new Mock<IdentityErrorDescriber>().Object,
                new Mock<IServiceProvider>().Object,
                new Mock<ILogger<UserManager<AppUser>>>().Object);
            userManagerMock
                .Setup(userManager => userManager
                .CreateAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.AddToRoleAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.FindByEmailAsync(dto.Email)).
                ReturnsAsync(new AppUser { UserName = dto.Email, Email = dto.Email, Id = idUser });

            var signInManager = new Mock<SignInManager<AppUser>>(
                userManagerMock.Object, Mock.Of<IHttpContextAccessor>(),
                Mock.Of<IUserClaimsPrincipalFactory<AppUser>>(), null, null, null, null);

            signInManager
                .Setup(s => s.CheckPasswordSignInAsync(It.IsAny<AppUser>(), dto.Password, false))
                .ReturnsAsync(SignInResult.Success);

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, signInManager.Object, _logger);

            //Act
            var result = await controller.Login(dto);

            //Assert
            if (result is OkObjectResult okResult)
            {
                var resultDto = (NewUserDto)okResult.Value;
                Assert.NotNull(resultDto.Token);
            }
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<OkObjectResult>();
        }

        //Тестирование авторизации при неправильном пароле
        [Fact]
        public async Task AccountControllerTests_Login_ReturnUnauthorized()
        {
            //Arrange
            var dto = new LoginDto()
            {
                Email = "invaliduser",
                Password = "invalidpassword",
            };

            //Mocks
            var userManagerMock = new Mock<UserManager<AppUser>>(
                new Mock<IUserStore<AppUser>>().Object,
                new Mock<IOptions<IdentityOptions>>().Object,
                new Mock<IPasswordHasher<AppUser>>().Object,
                new IUserValidator<AppUser>[0],
                new IPasswordValidator<AppUser>[0],
                new Mock<ILookupNormalizer>().Object,
                new Mock<IdentityErrorDescriber>().Object,
                new Mock<IServiceProvider>().Object,
                new Mock<ILogger<UserManager<AppUser>>>().Object);
            userManagerMock
                .Setup(userManager => userManager
                .CreateAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.AddToRoleAsync(It.IsAny<AppUser>(), It.IsAny<string>()))
                .Returns(System.Threading.Tasks.Task.FromResult(IdentityResult.Success));
            userManagerMock
                .Setup(um => um.FindByEmailAsync(dto.Email)).
                ReturnsAsync(new AppUser { UserName = dto.Email, Email = dto.Email, Id = idUser });

            var signInManager = new Mock<SignInManager<AppUser>>(
                userManagerMock.Object, Mock.Of<IHttpContextAccessor>(),
                Mock.Of<IUserClaimsPrincipalFactory<AppUser>>(), null, null, null, null);

            signInManager
                .Setup(s => s.CheckPasswordSignInAsync(It.IsAny<AppUser>(), dto.Password, false))
                .ReturnsAsync(SignInResult.Failed);

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, signInManager.Object, _logger);

            //Act
            var result = await controller.Login(dto);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<UnauthorizedObjectResult>();
        }

        //Тестирование получения информации
        [Fact]
        public async Task AccountControllerTests_AccountInfo_ReturnOKAsync()
        {
            //Mocks
            var userManagerMock = new Mock<UserManager<AppUser>>(
                new Mock<IUserStore<AppUser>>().Object,
                new Mock<IOptions<IdentityOptions>>().Object,
                new Mock<IPasswordHasher<AppUser>>().Object,
                new IUserValidator<AppUser>[0],
                new IPasswordValidator<AppUser>[0],
                new Mock<ILookupNormalizer>().Object,
                new Mock<IdentityErrorDescriber>().Object,
                new Mock<IServiceProvider>().Object,
                new Mock<ILogger<UserManager<AppUser>>>().Object);
            userManagerMock
                .Setup(um => um.FindByNameAsync("markmarkovich1@mail.ru")).
                ReturnsAsync(new AppUser { UserName = "markmarkovich1@mail.ru", Email = "markmarkovich1@mail.ru", Id = idUser });
            userManagerMock
                .Setup(um => um.GetRolesAsync(It.IsAny<AppUser>()))
                .ReturnsAsync(new List<string> { "User" });

            var signInManager = new Mock<SignInManager<AppUser>>(
                userManagerMock.Object, Mock.Of<IHttpContextAccessor>(),
                Mock.Of<IUserClaimsPrincipalFactory<AppUser>>(), null, null, null, null);

            //Моки для эмуляции авторизации пользователя в системе 
            var httpContextMock = new Mock<HttpContext>();
            var userPrincipalMock = new Mock<ClaimsPrincipal>();
            var identityMock = new Mock<ClaimsIdentity>();

            identityMock.Setup(i => i.Name).Returns("markmarkovich1@mail.ru");
            userPrincipalMock.Setup(p => p.Identity).Returns(identityMock.Object);

            httpContextMock.Setup(c => c.User).Returns(userPrincipalMock.Object);

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, signInManager.Object, _logger);
            controller.ControllerContext = new ControllerContext()
            {
                HttpContext = httpContextMock.Object,
            };
            var result = await controller.AccountInfo();

            if (result is ActionResult<UserInfoDto> okResult)
            {
                var resultDto = okResult.Result;
                Assert.NotNull(resultDto);
            }
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<ActionResult<UserInfoDto>>();
        }
    }
}
