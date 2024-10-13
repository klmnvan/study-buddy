namespace StudyBudyAPI.Dtos.Account
{
    public class UserInfoDto
    {
        public Guid IdUser { get; set; }
        public string Nickname { get; set; }
        public string Email { get; set; } 
        public string Role { get; set; }
    }
}
