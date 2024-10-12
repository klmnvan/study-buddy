using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using StudyBudyAPI.Configurations;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Data
{
    public class StudyBuddyDbContext(DbContextOptions<StudyBuddyDbContext> options) : IdentityDbContext<AppUser, Role, Guid>(options)
    {

        //public StudyBuddyDbContext(DbContextOptions<StudyBuddyDbContext> options) : base(options) { }

        //добавление сущностей в контекст БД
        public DbSet<User> Users { get; set; }
        public DbSet<Models.DB.Task> Tasks { get; set; }
        public DbSet<Exam> Exams { get; set; }
        public DbSet<Note> Notes { get; set; }
        public DbSet<Discipline> Disciplines { get; set; }
        public DbSet<Teacher> Teachers { get; set; }
        public DbSet<Requirement> Requirements { get; set; }

        //при запуске миграции, EF итак создаст таблицы (без конфигурации), но, чтобы быть уверенным в том, чтобы все связи и вся БД создалась правильно, делают конфигурацию

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            //подключили конфигурации для всех таблиц БД
            modelBuilder.ApplyConfiguration(new AppUserConfiguration());
            modelBuilder.ApplyConfiguration(new UserConfiguration());
            modelBuilder.ApplyConfiguration(new NoteConfiguration());
            modelBuilder.ApplyConfiguration(new TeacherConfiguration());
            modelBuilder.ApplyConfiguration(new DisciplineConfiguration());
            modelBuilder.ApplyConfiguration(new ExamConfiguration());
            modelBuilder.ApplyConfiguration(new RequirementConfiguration());
            modelBuilder.ApplyConfiguration(new TaskConfiguration());

            List<Role> roles = new List<Role>
            {
                new Role
                {
                    Name = "Admin",
                    NormalizedName = "ADMIN"
                },
                new Role
                {
                    Name = "User",
                    NormalizedName = "USER"
                },
            };

            modelBuilder.Entity<Role>().HasData(roles);
        }

    }
}
