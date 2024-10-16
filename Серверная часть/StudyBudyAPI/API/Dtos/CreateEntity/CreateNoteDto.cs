using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.CreateEntity
{
    public class CreateNoteDto
    {
        [Display(Name = "Id экзамена")]
        public int IdExam { get; set; }
        [Display(Name = "Заметки")]
        [DefaultValue("Заметка")]
        public string Content { get; set; } = string.Empty;
    }

}
