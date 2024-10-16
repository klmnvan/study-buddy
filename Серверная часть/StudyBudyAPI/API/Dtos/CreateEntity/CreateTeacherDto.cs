using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateTeacherDto
    {
        [Required]
        [Display(Name = "Полное имя")]
        [RegularExpression(@"^[a-zA-Zа-яА-ЯёЁ\s]+$", ErrorMessage = "Имя преподавателя может состоять только из симолов латиницы или кирилицы и пробелов")]
        [DefaultValue("Иванов Иван Иванович")]
        public string FullName { get; set; } = string.Empty;
        [Required]
        [Display(Name = "Номер кабинета")]
        [Range(1, int.MaxValue, ErrorMessage = "Номер кабинета должен быть положительным")]
        [DefaultValue("101")]
        public int? OfficeNumber { get; set; } = null;
    }

}
