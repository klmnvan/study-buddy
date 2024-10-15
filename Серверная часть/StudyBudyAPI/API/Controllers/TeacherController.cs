using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;

namespace StudyBudyAPI.Controllers
{
    [Route("api/teacher")]
    [ApiController]
    public class TeacherController : Controller
    {
        private readonly ITeacherRepository _teacherRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<TeacherController> _logger;

        public TeacherController(ITeacherRepository teacherRepository,
            UserManager<AppUser> userManager, ILogger<TeacherController> logger)
        {
            _teacherRepository = teacherRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [HttpGet("getTeachersUser")]
        public async Task<ActionResult<List<Teacher>>> GetTeachersUser()
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _teacherRepository.GetTeacherListUser(appUser.Id);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost("createTeacher")]
        public async Task<ActionResult<Teacher>> CreateTeacher(CreateTeacherDto dto)
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null) return Unauthorized();
                var newEntity = new Teacher
                {
                    IdUser = appUser.Id,
                    FullName = dto.FullName,
                    OfficeNumber = dto.OfficeNumber
                };
                var createdEntity = _teacherRepository.AddTeacher(newEntity);
                return Ok(createdEntity);
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [HttpDelete("deleteTeacher")]
        public async Task<ActionResult> DeleteTeacher([FromQuery(Name = "Id преподавателя")] int IdTeacher)
        {
            try
            {
                if (!_teacherRepository.TeacherIsExists(IdTeacher))
                {
                    return BadRequest("Такого преподавателя нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_teacherRepository.DeleteTeacher(IdTeacher))
                {
                    return StatusCode(500, "Ошибка удаления преподавателя");
                }
                return Ok("Преподаватель удален");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

    }

}
