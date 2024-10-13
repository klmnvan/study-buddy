using System.Text.Json.Serialization;

namespace StudyBudyAPI.Models.DB
{
    public class Requirement
    {
        public int IdRequirement { get; set; }
        public int IdDiscipline { get; set; }
        public string Content { get; set; } = string.Empty;
        //Для связей
        [JsonIgnore]
        public Discipline Discipline { get; set; }
    }
}
