using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class DisciplineDto
    {
        [Required]
        public int IdDiscipline { get; set; }
        [Required]
        public string Title { get; set; } = string.Empty;
        [Required]
        public int? IdTeacher { get; set; }
    }
}
