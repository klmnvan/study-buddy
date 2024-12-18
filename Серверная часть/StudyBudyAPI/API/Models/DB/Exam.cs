﻿using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class Exam
    {
        public int IdExam { get; set; }
        public string Duration { get; set; } = string.Empty; //Длительность экзамена
        public int NumberTickets { get; set; } = 0;
        public DateOnly DateExam { get; set; }
        public Guid IdUser { get; set; }
        public string Title { get; set; }
        //Для связей

        [JsonIgnore]
        public User User { get; set; } //Реализации связи один ко многим к таблице пользователей, ссылка на пользователя
        [JsonIgnore]
        public ICollection<Note> Notes { get; set; } = [];
    }

}
