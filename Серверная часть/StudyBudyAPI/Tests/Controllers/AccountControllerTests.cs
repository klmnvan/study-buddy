using AutoMapper;
using FakeItEasy;
using FluentAssertions;
using Humanizer;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Moq;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Data;
using StudyBudyAPI.Dtos.Account;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using SignInResult = Microsoft.AspNetCore.Identity.SignInResult;

namespace Tests.Controllers
{
    public class AccountControllerTests
    {
        private readonly UserManager<AppUser> _userManager;
        private readonly ITokenService _tokenService;
        private readonly StudyBuddyDbContext _context;
        private readonly IMapper _mapper;
        private readonly SignInManager<AppUser> _signInManager;
        private readonly ILogger<AccountController> _logger;

        public AccountControllerTests()
        {
            _mapper = A.Fake<IMapper>();
            _logger = A.Fake<ILogger<AccountController>>();
            _tokenService = A.Fake<ITokenService>();
            _signInManager = A.Fake<SignInManager<AppUser>>();
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
                var idUser = Guid.NewGuid();
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

        [Fact]
        public async System.Threading.Tasks.Task AccountControllerTests_Register_ReturnOKAsync()
        {
            //Arrange
            var dto = new RegisterDto()
            {
                Nickname = "Mark1",
                Email = "markmarkovich1@mail.ru",
                Password = "12345678",
                ConfirmPassword = "12345678",
            };

            //Mock
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

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, _signInManager, _logger);

            //Act
            var result = await controller.Register(dto);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<OkObjectResult>();
        }

        [Fact]
        public async System.Threading.Tasks.Task AccountControllerTests_Login_ReturnOKAsync()
        {
            //Arrange
            var dto = new LoginDto()
            {
                Email = "markmarkovich1@mail.ru",
                Password = "12345678",
            };

            //Mock
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

            var signInManager = new Mock<SignInManager<AppUser>>();

            signInManager.Setup(s => s.CheckPasswordSignInAsync(It.IsAny<AppUser>(), dto.Password, false))
                .Returns(System.Threading.Tasks.Task.FromResult(SignInResult.Success));

            var controller = new AccountController(userManagerMock.Object, _tokenService, _context, _signInManager, _logger);

            //Act
            var result = await controller.Login(dto);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<OkObjectResult>();
        }
    }
}
