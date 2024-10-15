using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Repository
{
    public class ExamRepository : IExamRepository
    {
        private readonly StudyBuddyDbContext _context;

        public ExamRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Exam AddExam(Exam e)
        {
            var result = _context.Exams.Add(e);
            _context.SaveChanges();
            return result.Entity;
        }

        public bool ExamIsExists(int id)
        {
            return _context.Exams.Any(it => it.IdExam == id);
        }

        public List<Exam> GetExamListUser(Guid idUser)
        {
            List<Exam> listE = _context.Exams.Select(it => it).Where(it => it.IdUser == idUser).ToList();
            return listE;
        }
    }
}
