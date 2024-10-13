using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Service;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos
{
    public class CreateTaskDto
    {
        [Required]
        [Display(Name = "Название")]
        public string Title { get; set; } = string.Empty;
        [Required]
        [DataType(DataType.Date)]
        [Display(Name = "Дедлайн")]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        public DateOnly Deadline { get; set; }
        [Display(Name = "Id дисциплины")]
        public int? IdDiscipline { get; set; }
        [Required]
        [Display(Name = "Описание")]
        public string Description { get; set; } = string.Empty;

    }

}
