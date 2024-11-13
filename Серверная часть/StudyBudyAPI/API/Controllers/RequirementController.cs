using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using Swashbuckle.AspNetCore.Annotations;

namespace StudyBudyAPI.Controllers
{
    [Route("api/requirement")]
    [ApiController]
    public class RequirementController : Controller
    {
        private readonly IRequirementRepository _requirementRepository;
        private readonly IDisciplineRepository _disciplineRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<TaskController> _logger;

        public RequirementController(IRequirementRepository requirementRepository, IDisciplineRepository disciplineRepository,
            UserManager<AppUser> userManager, ILogger<TaskController> logger)
        {
            _requirementRepository = requirementRepository;
            _disciplineRepository = disciplineRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [SwaggerOperation(Summary = "Получение всех требований к предметам у пользователя")]
        [HttpGet("GetAllRequirementsUser")]
        public async Task<ActionResult<List<Exam>>> GetAllRequirementsUser()
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _requirementRepository.GetAllRequirements(appUser.Id);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [SwaggerOperation(Summary = "Получение всех требований к предмету")]
        [HttpGet("getRequirementsDiscipline")]
        public async Task<ActionResult<List<Requirement>>> GetRequirementsDiscipline(
            [FromQuery(Name = "Id предмета")] int IdDiscipline)
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _requirementRepository.GetReqListByDisc(IdDiscipline);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [SwaggerOperation(Summary = "Создание требования к предмету")]
        [HttpPost("createRequirement")]
        public async Task<ActionResult<Requirement>> CreateTask(CreateRequirementDto dto)
        {
            try
            {
                if (!_disciplineRepository.DisciplineIsExists(dto.IdDiscipline))
                {
                    return BadRequest("Такого предмета нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var newR = new Requirement
                {
                    IdDiscipline = dto.IdDiscipline,
                    Content = dto.Content,
                }; 
                if (_requirementRepository.IsDuplicate(newR))
                {
                    return BadRequest("Требование с таким содержанием уже есть");
                }
                var createdR = _requirementRepository.AddRequirement(newR);
                return Ok(createdR);
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [SwaggerOperation(Summary = "Удаление требования к предмету")]
        [HttpDelete("deleteRequirement")]
        public async Task<ActionResult> DeleteNote([FromQuery(Name = "Id требования")] int IdRequirement)
        {
            try
            {
                if (!_requirementRepository.RequirementIsExists(IdRequirement))
                {
                    return BadRequest("Такого требования нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_requirementRepository.DeleteRequirement(IdRequirement))
                {
                    return StatusCode(500, "Ошибка удаления требования");
                }
                return Ok("Требование удалено");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [SwaggerOperation(Summary = "Изменение требования к предмету")]
        [HttpPut("updateRequirement")]
        public async Task<ActionResult> UpdateRequirement([FromBody] RequirementDto dto)
        {
            try
            {
                if (!_requirementRepository.RequirementIsExists(dto.IdRequirement))
                {
                    return BadRequest("Такого требования нет");
                }

                if (!_disciplineRepository.DisciplineIsExists(dto.IdDiscipline))
                {
                    return BadRequest("Такого предмета нет");
                }

                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);

                if (appUser == null)
                {
                    return Unauthorized();
                }

                var newEl = new Requirement
                {
                    IdRequirement = dto.IdRequirement, 
                    IdDiscipline = dto.IdDiscipline,    
                    Content = dto.Content, 
                };

                if (_requirementRepository.IsDuplicate(newEl))
                {
                    return BadRequest("Требование с таким содержанием уже есть");
                }

                if (!_requirementRepository.UpdateRequirement(newEl))
                {
                    return StatusCode(500, "Ошибка изменения требования");
                }
                return Ok("Требование изменено");
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
