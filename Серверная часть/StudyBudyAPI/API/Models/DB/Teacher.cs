using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class Teacher
    {
        public int IdTeacher { get; set; }
        public string FullName { get; set; } = string.Empty;
        public string OfficeNumber { get; set; } = string.Empty;
        public Guid IdUser { get; set; }
        //Для связей
        [JsonIgnore]
        public ICollection<Discipline> Disciplines { get; set; } = [];

        [JsonIgnore]
        public User User { get; set; } //Реализации связи один ко многим к таблице пользователей, ссылка на пользователя
    }
}

