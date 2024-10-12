using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.Account
{

    public class RegisterDto
    {
        [Required (ErrorMessage = "Не указан ник")] //свойство должно быть обязательно установлено
        [Display(Name = "Ник")]
        public string Nickname { get; set; }

        [Required(ErrorMessage = "Не указана почта")]
        [EmailAddress]
        [Display(Name = "Email")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Не указан пароль")]
        [Display(Name = "Пароль")]
        [DataType(DataType.Password)]
        [StringLength(64, MinimumLength = 8, ErrorMessage = "Длина должна быть от 8 до 64 символов")]
        public string Password { get; set; }

        [Required(ErrorMessage = "Не указан повторяющийся пароль")]
        [Display(Name = "Подтверждение пароля")]
        [DataType(DataType.Password)]
        [StringLength(64, MinimumLength = 8, ErrorMessage = "Длина должна быть от 8 до 64 символов")]
        [Compare("Password", ErrorMessage = "Пароли не совпадают")]
        public string ConfirmPassword { get; set; }
    }

}
