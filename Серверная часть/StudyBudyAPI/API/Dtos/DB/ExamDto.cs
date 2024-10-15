using StudyBudyAPI.Service;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.DB
{
    public class ExamDto
    {
        [Required]
        public int IdExam { get; set; }
        [Required]
        public string Duration { get; set; } = string.Empty; //Длительность экзамена
        [Required]
        public int NumberTickets { get; set; } = 0;
        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        public DateOnly DateExam { get; set; }
        [Required]
        public string Title { get; set; }
    }

}
