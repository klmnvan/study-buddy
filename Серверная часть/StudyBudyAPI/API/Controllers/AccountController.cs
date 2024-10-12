using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.DotNet.Scaffolding.Shared.Messaging;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using StudyBudyAPI.Data;
using StudyBudyAPI.Dtos.Account;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Controllers
{
    [Route("api/account")]
    [ApiController]
    public class AccountController : Controller
    {
        private readonly UserManager<AppUser> _userManager;
        private readonly ITokenService _tokenService;
        private readonly StudyBuddyDbContext _context;
        private readonly SignInManager<AppUser> _signInManager;

        public AccountController(UserManager<AppUser> userManager, ITokenService tokenService, StudyBuddyDbContext context, 
            SignInManager<AppUser> signInManager)
        {
            _userManager = userManager;
            _tokenService = tokenService;
            _context = context;
            _signInManager = signInManager;
        }

        [HttpPost("register")] //аннотация вида http запроса
        public async Task<IActionResult> Register([FromBody] RegisterDto registerDto)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    var firstError = ModelState.Values.SelectMany(v => v.Errors).FirstOrDefault()?.ErrorMessage;
                    return BadRequest(new { message = firstError });
                }

                var appUser = new AppUser
                {
                    //Username должен быть уникальным, поэтому передаём сюда email
                    Email = registerDto.Email,
                    UserName = registerDto.Email
                };

                //Создается пользователь и возвращается в переменную 
                var createdUser = await _userManager.CreateAsync(appUser, registerDto.Password);

                if (createdUser.Succeeded)
                {
                    //Добавление роли User к createdUser
                    var roleResult = await _userManager.AddToRoleAsync(appUser, "User"); 
                    if (roleResult.Succeeded)
                    {
                        //Получаем объект пользователя по его Email (который уникальный кстати)
                        var user = await _userManager.FindByEmailAsync(registerDto.Email);

                        if(user != null)
                        {
                            User newUser = new()
                            {
                                Nickname = registerDto.Nickname,
                                Email = registerDto.Email
                            };

                            newUser.IdUser = user.Id;
                            _context.Users.Add(newUser);
                            _context.SaveChanges();

                            return Ok(
                                new NewUserDto
                                {
                                    Nickname = newUser.Nickname,
                                    Email = appUser.Email,
                                    Token = _tokenService.CreateToken(appUser)
                                }
                            );
                        }
                        else
                        {
                            return StatusCode(500, "Ошибка создания пользователя");
                        }
                    }
                    else
                    {
                        return StatusCode(500, roleResult.Errors.FirstOrDefault().Description);
                    }
                }
                else
                {
                    var error = createdUser.Errors.FirstOrDefault();
                    if (error.Code == "DuplicateUserName")
                        return BadRequest("Такой пользователь уже есть");
                    return StatusCode(500, error);
                }
            }
            catch (Exception e)
            {
                var json = JsonConvert.SerializeObject(e)!;
                return StatusCode(500, e);
            }
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginDto loginDto)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            //Поиск пользователя в системе (как схема auth в supabase?)
            var appUser = await _userManager.Users.FirstOrDefaultAsync(x => x.Email == loginDto.Email.ToLower());

            if (appUser == null) return Unauthorized("Такого пользователя нет в базе");

            //Проверка совпадения пароля с тем, что в системе
            var result = await _signInManager.CheckPasswordSignInAsync(appUser, loginDto.Password, false);

            if (!result.Succeeded) return Unauthorized("Неверный пароль");

            var user = _context.Users.FirstOrDefault(u => u.IdUser == appUser.Id);

            return Ok(
                new NewUserDto
                {
                    Nickname = user.Nickname,
                    Email = user.Email,
                    Token = _tokenService.CreateToken(appUser)
                }
            );
        }
    }
}
