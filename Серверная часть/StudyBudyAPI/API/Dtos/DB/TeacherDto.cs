using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class TeacherDto
    {
        [Required]
        public int IdTeacher { get; set; }
        [Required]
        [RegularExpression(@"^[a-zA-Zа-яА-ЯёЁ\s]+$", ErrorMessage = "Имя преподавателя может состоять только из симолов латиницы или кирилицы и пробелов")]
        public string FullName { get; set; } = string.Empty;
        [Required]
        [Range(1, int.MaxValue, ErrorMessage = "Номер кабинета должен быть положительным")]
        public int? OfficeNumber { get; set; }
    }
}

