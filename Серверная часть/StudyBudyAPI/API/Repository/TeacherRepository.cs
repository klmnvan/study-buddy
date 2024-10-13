using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Repository
{
    public class TeacherRepository : ITeacherRepository
    {
        private readonly StudyBuddyDbContext _context;

        public TeacherRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Teacher AddTeacher(Teacher t)
        {
            var result = _context.Teachers.Add(t);
            _context.SaveChanges();
            return result.Entity;
        }

        public List<Teacher> GetTeacherListUser(Guid idUser)
        {
            List<Teacher> list = _context.Teachers.Select(it => it).Where(it => it.IdUser == idUser).ToList();
            return list;
        }

        public bool TeacherIsExists(int? id)
        {
            return _context.Teachers.Any(it => it.IdTeacher == id);
        }
    }
}
