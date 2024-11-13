using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface INoteRepository
    {
        public bool NoteIsExists(int id);
        public List<Note> GetNoteListByExam(int idExam);
        public List<Note> GetAllNoteUser(Guid idUser);
        public Note AddNote(Note n);
        public bool DeleteNote(int IdNote);
        public bool UpdateNote(Note el);
        public bool IsDuplicate(Note el);
    }
}
