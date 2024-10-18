using AutoMapper;
using FakeItEasy;
using FluentAssertions;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Data;
using StudyBudyAPI.Dtos.Account;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tests.Controllers
{
    public class ExamControllerTests
    {
        private readonly IExamRepository _examRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly IMapper _mapper;
        private readonly ILogger<ExamController> _logger;

        public ExamControllerTests()
        {
            _examRepository = A.Fake<IExamRepository>();
            _userManager = A.Fake<UserManager<AppUser>>();
            _mapper = A.Fake<IMapper>();
            _logger = A.Fake<ILogger<ExamController>>();
        }

        [Fact]
        public void ExamControllerTests_GetExams_ReturnOK()
        {
            //Arrange
            var exams = A.Fake<ICollection<ExamDto>>();
            var examList = A.Fake<List<ExamDto>>();
            A.CallTo(() => _mapper.Map<List<ExamDto>>(exams)).Returns(examList);
            var controller = new ExamController(_examRepository, _userManager, _logger);

            //Act
            var result = controller.GetExamsUser();

            //Assert
            result.Should().NotBeNull();
            result.Should().BeOfType(typeof(OkObjectResult));
        }
    }

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
            _userManager = A.Fake<UserManager<AppUser>>();
            _mapper = A.Fake<IMapper>();
            _logger = A.Fake<ILogger<AccountController>>();
            _tokenService = A.Fake<ITokenService>();
            _signInManager = A.Fake<SignInManager<AppUser>>();
            _context = A.Fake<StudyBuddyDbContext>();
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
                        Email = "markmarkovich@mail.ru"
                    });
                await databaseContext.SaveChangesAsync();
         
            }
            return databaseContext;
        }

            [Fact]
        public void AccountControllerTests_Register_ReturnOK()
        {
            //Arrange
            var dto = new RegisterDto()
            {
                Nickname = "Mark",
                Email = "markmarkovich@mail.ru",
                Password = "12345678",
                ConfirmPassword = "12345678",
            };
            var controller = new AccountController(_userManager, _tokenService, _context, _signInManager, _logger);

            //Act
            var result = controller.Register(dto);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeOfType(typeof(OkObjectResult));
        }
    }
}
