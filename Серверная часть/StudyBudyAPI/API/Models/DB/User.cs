using StudyBudyAPI.Models.Account;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class User
    {
        public Guid IdUser { get; set; }
        public string Nickname { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        //Для связей
        [JsonIgnore]
        public ICollection<Exam> Exams { get; set; } = []; //Реализации связи один ко многим к таблице пользователей, у пользователя может быть множество экзаменов
        [JsonIgnore]
        public ICollection<Discipline> Disciplines { get; set; } = [];
        [JsonIgnore]
        public ICollection<Teacher> Teachers { get; set; } = [];
        [JsonIgnore]
        public ICollection<Task> Tasks { get; set; } = [];
        [JsonIgnore]
        public AppUser AppUser { get; set; }
    }

}
