using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Repository
{
    public class RequirementRepository : IRequirementRepository
    {
        private readonly StudyBuddyDbContext _context;

        public RequirementRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Requirement AddRequirement(Requirement r)
        {
            var result = _context.Requirements.Add(r);
            _context.SaveChanges();
            return result.Entity;
        }

        public bool DeleteRequirement(int IdRequirement)
        {
            Requirement el = _context.Requirements.FirstOrDefault(x => x.IdRequirement == IdRequirement);
            if (el != null)
            {
                _context.Remove(el);
                return Save();
            }
            return false;
        }

        public List<Requirement> GetReqListByDisc(int idDisc)
        {
            List<Requirement> listR = _context.Requirements.Select(it => it).Where(it => it.IdDiscipline == idDisc).ToList();
            return listR;
        }

        public bool IsDuplicate(Requirement el, Guid idUser)
        {
            return _context.Requirements.Any(it => it.Content == el.Content && it.Discipline.IdUser == idUser);
        }

        public bool RequirementIsExists(int id)
        {
            return _context.Requirements.Any(it => it.IdRequirement == id);
        }

        public bool Save()
        {
            var saved = _context.SaveChanges();
            return saved > 0 ? true : false; //было ли изменено хотя бы одно значение в базе данных
        }

        public bool UpdateRequirement(Requirement el)
        {
            _context.Update(el);
            return Save();
        }
    }
}
