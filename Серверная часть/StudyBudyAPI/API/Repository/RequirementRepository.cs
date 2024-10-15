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

        public List<Requirement> GetReqListByDisc(int idDisc)
        {
            List<Requirement> listR = _context.Requirements.Select(it => it).Where(it => it.IdDiscipline == idDisc).ToList();
            return listR;
        }

        public bool RequirementIsExists(int id)
        {
            return _context.Requirements.Any(it => it.IdRequirement == id);
        }
    }
}
