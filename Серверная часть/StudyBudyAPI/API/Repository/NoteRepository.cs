using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Repository
{
    public class NoteRepository : INoteRepository
    {
        private readonly StudyBuddyDbContext _context;

        public NoteRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Note AddNote(Note n)
        {
            var result = _context.Notes.Add(n);
            _context.SaveChanges();
            return result.Entity;
        }

        public List<Note> GetNoteListByExam(int idExam)
        {
            List<Note> listN = _context.Notes.Select(it => it).Where(it => it.IdExam == idExam).ToList();
            return listN;
        }

        public bool NoteIsExists(int id)
        {
            return _context.Notes.Any(it => it.IdNote == id);
        }
    }
}
