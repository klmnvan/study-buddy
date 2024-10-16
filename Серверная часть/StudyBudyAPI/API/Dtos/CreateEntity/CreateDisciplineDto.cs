using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateDisciplineDto
    {
        [Required]
        [Display(Name = "Название")]
        [DefaultValue("МДК 01.04. Системное программирование")]
        public string Title { get; set; } = string.Empty;
        [Display(Name = "Id преподавателя")]
        [DefaultValue("null")]
        public int? IdTeacher { get; set; }

    }

}
