using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface IRequirementRepository
    {
        public bool RequirementIsExists(int id);
        public List<Requirement> GetReqListByDisc(int idDisc);
        public Requirement AddRequirement(Requirement r);
        public bool DeleteRequirement(int IdRequirement);

    }
}
