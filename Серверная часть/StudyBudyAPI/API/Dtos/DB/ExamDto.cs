using StudyBudyAPI.Service;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.DB
{
    public class ExamDto
    {
        [Required]
        public int IdExam { get; set; }
        [Required]
        [DefaultValue("3 часа")]
        public string Duration { get; set; } = string.Empty; //Длительность экзамена
        [Required]
        [DefaultValue("30")]
        public int NumberTickets { get; set; } = 0;
        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        [DefaultValue("2024-27-11")]
        public DateOnly DateExam { get; set; }
        [Required]
        [DefaultValue("1 модуль")]
        public string Title { get; set; }
    }

}
