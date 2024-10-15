using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface IExamRepository
    {
        public bool ExamIsExists(int id);
        public List<Exam> GetExamListUser(Guid idUser); 
        public Exam AddExam(Exam e);
        public bool DeleteExam(int IdExam);
    }
}
