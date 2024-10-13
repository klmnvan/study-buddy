using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;

namespace StudyBudyAPI.Repository
{
    public class TaskRepository : ITaskRepository
    {
        private readonly StudyBuddyDbContext _context;

        public TaskRepository(StudyBuddyDbContext context)
        {
            _context = context;
        }

        public Models.DB.Task AddTask(Models.DB.Task newTask)
        {
            var task = _context.Tasks.Add(newTask);
            _context.SaveChanges();
            return task.Entity;
        }

        public List<Models.DB.Task> GetTaskListUser(Guid idUser)
        {
            List<Models.DB.Task> listTask = _context.Tasks.Select(it => it).Where(it => it.IdUser == idUser).ToList();
            return listTask;
        }

        public bool TaskIsExists(int? id)
        {
            return _context.Tasks.Any(it => it.IdTask == id);
        }
    }
}
