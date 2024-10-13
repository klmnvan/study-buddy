using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos
{
    public class CreateDisciplineDto
    {
        [Required]
        [Display(Name = "Название")]
        public string Title { get; set; } = string.Empty;
        [Display(Name = "Id преподавателя")]
        public int? IdTeacher { get; set; }

    }

}
