using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class TeacherDto
    {
        [Required]
        public int IdTeacher { get; set; }
        [Required]
        [RegularExpression(@"^[a-zA-Zа-яА-ЯёЁ\s]+$", ErrorMessage = "Имя преподавателя может состоять только из симолов латиницы или кирилицы и пробелов")]
        [DefaultValue("Иванов Иван Иванович")]
        public string FullName { get; set; } = string.Empty;
        [Range(1, int.MaxValue, ErrorMessage = "Номер кабинета должен быть положительным")]
        [DefaultValue("101")]
        public int? OfficeNumber { get; set; }
    }
}

