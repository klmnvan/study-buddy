using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.Account
{
    public class NewUserDto
    {
        [Required]
        public string Nickname { get; set; }
        public string Email { get; set; }
        public string Token { get; set; }
    }
}
