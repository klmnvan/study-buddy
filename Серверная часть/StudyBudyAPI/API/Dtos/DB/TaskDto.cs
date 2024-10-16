using StudyBudyAPI.Service;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.DB
{
    public class TaskDto
    {
        [Required]
        public int IdTask { get; set; }
        [Required]
        [DefaultValue("Название")]
        public string Title { get; set; } = string.Empty;
        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        [DefaultValue("2024-27-11")]
        public DateOnly Deadline { get; set; }
        [DefaultValue("null")]
        public int? IdDiscipline { get; set; }
        [Required]
        [DefaultValue("Описание")]
        public string Description { get; set; } = string.Empty;
        [Required]
        public bool IsCompleted { get; set; } = false;

    }
}
