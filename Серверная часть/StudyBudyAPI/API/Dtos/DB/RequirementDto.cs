using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class RequirementDto
    {
        [Required]
        public int IdRequirement { get; set; }
        [Required]
        public int IdDiscipline { get; set; }
        [Required]
        [DefaultValue("Требование")]
        public string Content { get; set; } = string.Empty;
    }
}
