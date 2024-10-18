using FluentAssertions;
using Microsoft.EntityFrameworkCore;
using StudyBudyAPI.Data;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tests.Repository
{
    public class ExamRepositoryTests
    {

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
        public async void ExamRepository_GetExamsUser_ReturnsExamsUser()
        {
            //Arrange
            var dbContext = await GetDatabaseContext();
            var pokemonRepository = new ExamRepository(dbContext);
            var user = dbContext.Users.Select(it => it).Where(it => it.Email == "markmarkovich@mail.ru").FirstOrDefault();

            //Act
            var result = pokemonRepository.GetExamListUser(user.IdUser);

            //Assert
            result.Should().NotBeNull();
            result.Should().BeOfType<List<Exam>>();
        }

    }
}
