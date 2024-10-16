using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Dtos.Account
{

    public class RegisterDto
    {
        [Required(ErrorMessage = "Не указан ник")] //свойство должно быть обязательно установлено
        [Display(Name = "Ник")]
        [DefaultValue("nickname")]
        [RegularExpression(@"^[a-zA-Zа-яА-ЯёЁ\s]+$", ErrorMessage = "Никнейм может состоять только из симолов латиницы или кирилицы и пробелов")]
        public string Nickname { get; set; } = string.Empty;

        [Required(ErrorMessage = "Не указана почта")]
        [EmailAddress]
        [Display(Name = "Email")]
        public string Email { get; set; } = string.Empty;

        [Required(ErrorMessage = "Не указан пароль")]
        [Display(Name = "Пароль")]
        [DataType(DataType.Password)]
        [StringLength(64, MinimumLength = 8, ErrorMessage = "Длина должна быть от 8 до 64 символов")]
        [DefaultValue("12345678")]
        public string Password { get; set; } = string.Empty;

        [Required(ErrorMessage = "Не указан повторяющийся пароль")]
        [Display(Name = "Подтверждение пароля")]
        [DataType(DataType.Password)]
        [StringLength(64, MinimumLength = 8, ErrorMessage = "Длина должна быть от 8 до 64 символов")]
        [Compare("Password", ErrorMessage = "Пароли не совпадают")]
        [DefaultValue("12345678")]
        public string ConfirmPassword { get; set; } = string.Empty;
    }

}
