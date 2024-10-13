using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;

namespace StudyBudyAPI.Repository
{
    public class NoteRepository : INoteRepository
    {
        private readonly StudyBuddyDbContext _context;

        public NoteRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public bool NoteIsExists(int id)
        {
            throw new NotImplementedException();
        }
    }
}
