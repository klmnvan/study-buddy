namespace StudyBudyAPI.Models.DB
{
    public class Discipline
    {
        public int IdDiscipline { get; set; }
        public string Title { get; set; } = string.Empty;
        public int IdTeacher { get; set; }
        public Guid IdUser { get; set; }
        //Для связей
        public User User { get; set; } //Реализации связи один ко многим к таблице пользователей, ссылка на пользователя
        public Teacher Teacher { get; set; } //Реализации связи один ко многим к таблице преподавателей, ссылка на преподавателя
        public ICollection<Task> Tasks { get; set; } = [];
        public ICollection<Requirement> Requirements { get; set; } = [];
    }
}
