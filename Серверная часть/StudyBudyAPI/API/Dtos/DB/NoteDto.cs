using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.DB
{
    public class NoteDto
    {
        [Required]
        public int IdNote { get; set; }
        [Required]
        public int IdExam { get; set; }
        [Required]
        [DefaultValue("Заметка")]
        public string Content { get; set; } = string.Empty;
    }
}
