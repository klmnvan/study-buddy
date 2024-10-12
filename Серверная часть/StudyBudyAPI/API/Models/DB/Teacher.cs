namespace StudyBudyAPI.Models.DB
{
    public class Teacher
    {
        public int IdTeacher { get; set; }
        public string FullName { get; set; } = string.Empty;
        public string OfficeNumber { get; set; } = string.Empty;
        //Для связей
        public ICollection<Discipline> Disciplines { get; set; } = [];
    }
}

