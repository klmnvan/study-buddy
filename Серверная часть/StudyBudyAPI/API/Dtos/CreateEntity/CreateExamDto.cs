using StudyBudyAPI.Service;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateExamDto
    {

        [Required]
        [Display(Name = "Название")]
        [DefaultValue("1 модуль")]
        public string Title { get; set; }
        [Required]
        [Display(Name = "Длительность")]
        [DefaultValue("3 часа")]
        public string Duration { get; set; } = string.Empty; //Длительность экзамена
        [Required]
        [Display(Name = "Количество билетов")]
        [DefaultValue("30")]
        public int NumberTickets { get; set; } = 0;
        [Required]
        [DataType(DataType.Date)]
        [Display(Name = "Дата экзамена")]
        [DisplayFormat(DataFormatString = "{yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        [DefaultValue("2024-27-11")]
        public DateOnly DateExam { get; set; }

    }

}
