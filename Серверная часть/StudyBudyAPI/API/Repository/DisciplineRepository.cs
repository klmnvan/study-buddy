using Humanizer;
using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Repository
{
    public class DisciplineRepository : IDisciplineRepository
    {
        private readonly StudyBuddyDbContext _context;

        public DisciplineRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Discipline AddDisc(Discipline d)
        {
            var result = _context.Disciplines.Add(d);
            _context.SaveChanges();
            return result.Entity;
        }

        public bool DeleteDisc(int IdDisc)
        {
            Discipline el = _context.Disciplines.FirstOrDefault(x => x.IdDiscipline == IdDisc);
            if (el != null)
            {
                _context.Remove(el);
                return Save();
            }
            return false;
        }

        public bool DisciplineIsExists(int? id)
        {
            return _context.Disciplines.Any(it => it.IdDiscipline == id);
        }

        public List<Discipline> GetDisciplineListUser(Guid idUser)
        {
            List<Discipline> listD = _context.Disciplines.Select(it => it).Where(it => it.IdUser == idUser).ToList();
            return listD;
        }

        public bool Save()
        {
            var saved = _context.SaveChanges();
            return saved > 0 ? true : false; //было ли изменено хотя бы одно значение в базе данных
        }

    }
}
