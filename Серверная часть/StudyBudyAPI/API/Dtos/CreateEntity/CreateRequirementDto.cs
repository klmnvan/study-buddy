using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateRequirementDto
    {
        [Display(Name = "Id дисциплины")]
        public int IdDiscipline { get; set; }
        [Display(Name = "Требование")]
        public string Content { get; set; } = string.Empty;
    }

}
