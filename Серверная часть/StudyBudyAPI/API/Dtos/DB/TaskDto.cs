using StudyBudyAPI.Service;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.DB
{
    public class TaskDto
    {
        [Required]
        public int IdTask { get; set; }
        [Required]
        public string Title { get; set; } = string.Empty;
        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        public DateOnly Deadline { get; set; }
        [Required]
        public int? IdDiscipline { get; set; }
        [Required]
        public string Description { get; set; } = string.Empty;
        [Required]
        public bool IsCompleted { get; set; } = false;

    }
}
