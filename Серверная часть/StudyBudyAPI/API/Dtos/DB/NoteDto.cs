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
        public string Content { get; set; } = string.Empty;
    }
}
