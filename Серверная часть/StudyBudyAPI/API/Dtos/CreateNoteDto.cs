using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos
{
    public class CreateNoteDto
    {
        [Display(Name = "Id экзамена")]
        public int IdExam { get; set; }
        [Display(Name = "Заметки")]
        public string Content { get; set; } = string.Empty;
    }

}
