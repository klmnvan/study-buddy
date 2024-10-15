using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface INoteRepository
    {
        public bool NoteIsExists(int id);
        public List<Note> GetNoteListByExam(int idExam);
        public Note AddNote(Note n);
        public bool DeleteNote(int IdNote);
    }
}
