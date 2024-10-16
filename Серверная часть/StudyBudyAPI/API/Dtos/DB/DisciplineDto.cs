using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class DisciplineDto
    {
        [Required]
        public int IdDiscipline { get; set; }
        [Required]
        [DefaultValue("Другое название")]
        public string Title { get; set; } = string.Empty;
        [DefaultValue("null")]
        public int? IdTeacher { get; set; }
    }
}
