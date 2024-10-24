using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Moq;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using Task = System.Threading.Tasks.Task;


namespace Tests.Controllers
{
    public class ExamControllerTests
    {
        private readonly IExamRepository _examRepository;
        private readonly ILogger<ExamController> _logger;
        private Guid idUser;

        public ExamControllerTests()
        {
            _examRepository = new ExamRepository(GetDatabaseContext().Result);
            _logger = new Mock<ILogger<ExamController>>().Object;
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

        public async Task ExamControllerTests_GetExamsUser_ReturnOKAsync()
        {

        }
    }
}
