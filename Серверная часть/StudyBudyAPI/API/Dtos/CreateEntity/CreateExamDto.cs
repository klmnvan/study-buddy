using StudyBudyAPI.Service;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateExamDto
    {

        [Required]
        [Display(Name = "Название")]
        public string Title { get; set; }
        [Required]
        [Display(Name = "Длительность")]
        public string Duration { get; set; } = string.Empty; //Длительность экзамена
        [Required]
        [Display(Name = "Количество билетов")]
        public int NumberTickets { get; set; } = 0;
        [Required]
        [DataType(DataType.Date)]
        [Display(Name = "Дата экзамена")]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        public DateOnly DateExam { get; set; }

    }

}
