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

        public bool DisciplineIsExists(int? id)
        {
            return _context.Disciplines.Any(it => it.IdDiscipline == id);
        }

        public List<Discipline> GetDisciplineListUser(Guid idUser)
        {
            List<Discipline> listD = _context.Disciplines.Select(it => it).Where(it => it.IdUser == idUser).ToList();
            return listD;
        }

    }
}
