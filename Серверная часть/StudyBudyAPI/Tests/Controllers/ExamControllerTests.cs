using FluentAssertions;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Moq;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Data;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using System.Security.Claims;
using Task = System.Threading.Tasks.Task;


namespace Tests.Controllers
{
    public class ExamControllerTests
    {
        private readonly ExamRepository _examRepository;
        private readonly ILogger<ExamController> _logger;
        private readonly UserManager<AppUser> _userManager;
        private readonly ExamController controller;
        private Guid idUser;

        public ExamControllerTests()
        {
            _examRepository = new ExamRepository(GetDatabaseContext().Result);
            _logger = new Mock<ILogger<ExamController>>().Object;
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
            .Setup(um => um.FindByEmailAsync("markmarkovich@mail.ru")).
                ReturnsAsync(new AppUser { UserName = "markmarkovich@mail.ru", Email = "markmarkovich@mail.ru", Id = idUser });
            userManagerMock
            .Setup(um => um.FindByNameAsync("markmarkovich@mail.ru")).
                ReturnsAsync(new AppUser { UserName = "markmarkovich@mail.ru", Email = "markmarkovich@mail.ru", Id = idUser });

            _userManager = userManagerMock.Object;

            //Mocks
            var httpContextMock = new Mock<HttpContext>();
            var userPrincipalMock = new Mock<ClaimsPrincipal>();
            var identityMock = new Mock<ClaimsIdentity>();

            identityMock.Setup(i => i.Name).Returns("markmarkovich@mail.ru");
            userPrincipalMock.Setup(p => p.Identity).Returns(identityMock.Object);

            httpContextMock.Setup(c => c.User).Returns(userPrincipalMock.Object);

            controller = new ExamController(_examRepository, _userManager, _logger);

            controller.ControllerContext = new ControllerContext()
            {
                HttpContext = httpContextMock.Object,
            };
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
                        Email = "markmarkovich@mail.ru"
                    });
                await databaseContext.SaveChangesAsync();
            }
            if (await databaseContext.Exams.CountAsync() <= 0)
            {
                for (int i = 1; i <= 10; i++)
                {
                    databaseContext.Exams.Add(
                    new Exam()
                    {
                        IdUser = databaseContext.Users.Select(it => it).Where(it => it.Email == "markmarkovich@mail.ru").FirstOrDefault().IdUser,
                        Duration = $"{i} часа",
                        NumberTickets = i * 4,
                        DateExam = new DateOnly(2024, 11, i),
                        Title = $"{i} Экзамен",
                        Notes = new List<Note>()
                        {
                            new Note() { Content = "Заметка 1" },
                            new Note() { Content = "Заметка 2" },
                            new Note() { Content = "Заметка 3" },
                            new Note() { Content = "Заметка 4" },
                            new Note() { Content = "Заметка 5" },
                        }
                    });
                    await databaseContext.SaveChangesAsync();
                }
            }
            return databaseContext;
        }

        [Fact]
        public async Task ExamControllerTests_GetExamsUser_ReturnOKAsync()
        {
            //Arrange
            var controller = new ExamController(_examRepository, _userManager, _logger);

            //Act
            var result = await controller.GetExamsUser();

            //Assert
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<ActionResult<List<Exam>>>();
        }

        [Fact]
        public async Task ExamControllerTests_CreateExam_ReturnOKAsync()
        {
            //Arrange
            var dto = new CreateExamDto
            {
                Title = "Новый экзамен",
                DateExam = new DateOnly(2024, 11, 29),
                Duration = "3 часа",
                NumberTickets = 24
            };

            //Act
            var countExamsBefore = _examRepository.GetExamListUser(idUser).Count;
            var result = await controller.CreateExam(dto);
            var countExamsAfter = _examRepository.GetExamListUser(idUser).Count;

            //Assert
            Assert.True(countExamsBefore < countExamsAfter);
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<ActionResult<Exam>>();
        }

        [Fact]
        public async Task ExamControllerTests_DeleteExam_ReturnOKAsync()
        {
            //Arrange
            var deletedExam = _examRepository.GetExamListUser(idUser).First().IdExam; //Получили id экзамена, который будем удалять

            //Act
            var countExamsBefore = _examRepository.GetExamListUser(idUser).Count;
            var result = await controller.DeleteExam(deletedExam);
            var countExamsAfter = _examRepository.GetExamListUser(idUser).Count;

            //Assert
            Assert.True(countExamsBefore > countExamsAfter); //Проверяем, что количество экзаменов изменилось полсе удаления (их стало меньше)
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<ActionResult>();
        }

        [Fact]
        public async Task ExamControllerTests_UpdateExam_ReturnOKAsync()
        {
            //Arrange
            var updatedExam = new ExamDto 
            { 
                IdExam = 1,
                Title = "Заголовок новый",
                NumberTickets = 40, 
                DateExam = new DateOnly(2024, 11, 12),   
                Duration = "3 часа",
            };
            
            updatedExam.NumberTickets = 100; //Изменили количество билетов

            //Act
            //Exam examBefore = _examRepository.GetExamListUser(idUser).First(); //Получили экзамен, который будем изменять
            //_examRepository.Save();
            var result = await controller.UpdateExam(updatedExam);
            //Exam examAfter = _examRepository.GetExamListUser(idUser).FirstOrDefault(it => it.IdExam == 1);

            //Assert
            //Assert.NotEqual(examBefore, examAfter);
            result.Should().NotBeNull();
            result.Should().BeAssignableTo<ActionResult>();
        }
    }
}
