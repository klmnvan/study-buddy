using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class Discipline
    {
        public int IdDiscipline { get; set; }
        public string Title { get; set; } = string.Empty;
        public int? IdTeacher { get; set; }
        public Guid IdUser { get; set; }
        //Для связей
        [JsonIgnore]
        public User User { get; set; } //Реализации связи один ко многим к таблице пользователей, ссылка на пользователя
        [JsonIgnore]
        public Teacher? Teacher { get; set; } //Реализации связи один ко многим к таблице преподавателей, ссылка на преподавателя
        [JsonIgnore]
        public ICollection<Task> Tasks { get; set; } = [];
        [JsonIgnore]
        public ICollection<Requirement> Requirements { get; set; } = [];
    }
}
