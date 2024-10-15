using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Interfaces
{
    public interface IDisciplineRepository
    {
        public bool DisciplineIsExists(int? id);
        public List<Discipline> GetDisciplineListUser(Guid idUser); //Получение списка предметов пользователя
        public Discipline AddDisc(Discipline d);
        public bool DeleteDisc(int IdDisc);
    }
}
