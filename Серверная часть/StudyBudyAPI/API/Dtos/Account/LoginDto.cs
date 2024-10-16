using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.Account
{
    public class LoginDto
    {
        [Required(ErrorMessage = "Не указана почта")]
        [EmailAddress]
        [Display(Name = "Email")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Не указан пароль")]
        [Display(Name = "Пароль")]
        [DataType(DataType.Password)]
        [DefaultValue("12345678")]
        public string Password { get; set; }
    }
}
