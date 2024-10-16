using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Service;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateTaskDto
    {
        [Required]
        [Display(Name = "Название")]
        [DefaultValue("Название")]
        public string Title { get; set; } = string.Empty;
        [Required]
        [DataType(DataType.Date)]
        [Display(Name = "Дедлайн")]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        [DefaultValue("2024-27-11")]
        public DateOnly Deadline { get; set; }
        [DefaultValue("null")]
        public int? IdDiscipline { get; set; }
        [Required]
        [Display(Name = "Описание")]
        [DefaultValue("Описание")]
        public string Description { get; set; } = string.Empty;

    }

}
