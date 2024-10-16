using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;

namespace StudyBudyAPI.Controllers
{
    [Route("api/discipline")]
    [ApiController]
    public class DisciplineController : Controller
    {
        private readonly IDisciplineRepository _disciplineRepository;
        private readonly ITeacherRepository _teacherRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<DisciplineController> _logger;

        public DisciplineController(IDisciplineRepository disciplineRepository,ITeacherRepository teacherRepository,
            UserManager<AppUser> userManager, ILogger<DisciplineController> logger)
        {
            _disciplineRepository = disciplineRepository;
            _teacherRepository = teacherRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [HttpGet("getDisciplineUser")]
        public async Task<ActionResult<List<Discipline>>> GetDisciplinesUser()
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _disciplineRepository.GetDisciplineListUser(appUser.Id);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost("createDiscipline")]
        public async Task<ActionResult<Discipline>> CreateDiscipline(CreateDisciplineDto dto)
        {
            try
            {
                if (!(_teacherRepository.TeacherIsExists(dto.IdTeacher) || dto.IdTeacher == null))
                {
                    return BadRequest("Такого преподавателя нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var newEntity = new Discipline
                {
                    IdUser = appUser.Id,
                    Title = dto.Title,
                    IdTeacher = dto.IdTeacher
                };
                if (_disciplineRepository.IsDuplicate(newEntity))
                {
                    return BadRequest("Предмет с таким названием уже есть");
                }
                var createdEntity = _disciplineRepository.AddDisc(newEntity);
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

        [HttpDelete("deleteDiscipline")]
        public async Task<ActionResult> DeleteDiscipline([FromQuery(Name = "Id предмета")] int IdDiscipline)
        {
            try
            {
                if (!_disciplineRepository.DisciplineIsExists(IdDiscipline))
                {
                    return BadRequest("Такого предмета нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_disciplineRepository.DeleteDisc(IdDiscipline))
                {
                    return StatusCode(500, "Ошибка удаления предмета");
                }
                return Ok("Предмет удален");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [HttpPut("updateDiscipline")]
        public async Task<ActionResult> UpdateDiscipline([FromBody] DisciplineDto dto)
        {
            try
            {
                if (!_disciplineRepository.DisciplineIsExists(dto.IdDiscipline))
                {
                    return BadRequest("Такого предмета нет");
                }

                if (!_teacherRepository.TeacherIsExists(dto.IdTeacher))
                {
                    return BadRequest("Такого преподавателя нет");
                }

                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);

                if (appUser == null)
                {
                    return Unauthorized();
                }

                var newEl = new Discipline
                {
                    IdDiscipline = dto.IdDiscipline,
                    Title = dto.Title,
                    IdTeacher = dto.IdTeacher,
                    IdUser = appUser.Id,
                };
                if (_disciplineRepository.IsDuplicate(newEl))
                {
                    return BadRequest("Предмет с таким названием уже есть");
                }
                if (!_disciplineRepository.UpdateDisc(newEl))
                {
                    return StatusCode(500, "Ошибка изменения предмета");
                }
                return Ok("Предмет изменен");
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
