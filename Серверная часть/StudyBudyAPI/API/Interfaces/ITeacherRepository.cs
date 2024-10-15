using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface ITeacherRepository
    {
        public bool TeacherIsExists(int? id);
        public List<Teacher> GetTeacherListUser(Guid idUser);
        public Teacher AddTeacher(Teacher d);
        public bool DeleteTeacher(int IdTeacher);
        public bool UpdateTeacher(Teacher el);
    }
}
