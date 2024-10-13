using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;

namespace StudyBudyAPI.Repository
{
    public class RequirementRepository : IRequirementRepository
    {
        private readonly StudyBuddyDbContext _context;

        public RequirementRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public bool RequirementIsExists(int id)
        {
            throw new NotImplementedException();
        }
    }
}
