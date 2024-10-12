using StudyBudyAPI.Models.Account;

namespace StudyBudyAPI.Models.DB
{
    public class User
    {
        public Guid IdUser { get; set; }
        public string Nickname { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        //Для связей
        public ICollection<Exam> Exams { get; set; } = []; //Реализации связи один ко многим к таблице пользователей, у пользователя может быть множество экзаменов
        public ICollection<Discipline> Disciplines { get; set; } = [];
        public ICollection<Task> Tasks { get; set; } = [];
        public AppUser AppUser { get; set; }
    }

}
