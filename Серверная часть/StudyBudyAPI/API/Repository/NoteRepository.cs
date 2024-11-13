using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;
using System.Diagnostics.Metrics;

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

        public bool DeleteNote(int IdNote)
        {
            Note note = _context.Notes.FirstOrDefault(x => x.IdNote == IdNote);
            if(note != null)
            {
                _context.Remove(note);
                return Save();
            }
            return false;
        }

        public List<Note> GetAllNoteUser(Guid idUser)
        {
            List<Note> list = _context.Notes.Select(it => it).Where(it => it.Exam.IdUser == idUser).ToList();
            return list;
        }

        public List<Note> GetNoteListByExam(int idExam)
        {
            List<Note> listN = _context.Notes.Select(it => it).Where(it => it.IdExam == idExam).ToList();
            return listN;
        }

        public bool IsDuplicate(Note el)
        {
            return _context.Notes.Any(it => (it.Content == el.Content && it.IdExam == el.IdExam));
        }

        public bool NoteIsExists(int id)
        {
            return _context.Notes.Any(it => it.IdNote == id);
        }

        public bool Save()
        {
            var saved = _context.SaveChanges();
            return saved > 0 ? true : false; //было ли изменено хотя бы одно значение в базе данных
        }

        public bool UpdateNote(Note el)
        {
            _context.Update(el);
            return Save();
        }
    }
}
