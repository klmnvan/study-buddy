using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;

namespace StudyBudyAPI.Repository
{
    public class ExamRepository : IExamRepository
    {
        private readonly StudyBuddyDbContext _context;

        public ExamRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public bool ExamIsExists(int id)
        {
            throw new NotImplementedException();
        }
    }
}
