namespace StudyBudyAPI.Models.DB
{
    public class Note
    {
        public int IdNote { get; set; }
        public int IdExam { get; set; }
        public string Content { get; set; } = string.Empty;
        //Для связей
        public Exam Exam { get; set; } //Реализации связи один ко многим к таблице экзаменов, ссылка на экзамен
    }
}
