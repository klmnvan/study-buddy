using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos
{
    public class CreateRequirementDto
    {
        [Display(Name = "Id дисциплины")]
        public int IdDiscipline { get; set; }
        [Display(Name = "Требование")]
        public string Content { get; set; } = string.Empty;
    }

}
