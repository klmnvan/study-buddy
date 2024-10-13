using Microsoft.AspNetCore.Mvc;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class Task
    {
        public int IdTask { get; set; }
        public string Title { get; set; } = string.Empty;
        public DateOnly Deadline { get; set; }
        public Guid IdUser { get; set; }
        public int? IdDiscipline { get; set; }
        public string Description { get; set; } = string.Empty;
        public bool IsCompleted { get; set; } = false;
        //Для связей
        [JsonIgnore]
        public User User { get; set; } //Реализации связи один ко многим к таблице пользователей, ссылка на пользователя
        [JsonIgnore]
        public Discipline? Discipline { get; set; }

    }
}
