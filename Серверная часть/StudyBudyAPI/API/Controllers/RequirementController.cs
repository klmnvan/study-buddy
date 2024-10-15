using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;

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

        [HttpGet("getRequirementsDiscipline")]
        public async Task<ActionResult<List<Requirement>>> GetRequirementsDiscipline(
            [FromQuery(Name = "Id преподавателя")] int IdDiscipline)
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
    }

    [Route("api/note")]
    [ApiController]
    public class NoteController : Controller
    {
        private readonly INoteRepository _noteRepository;
        private readonly IExamRepository _examRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<TaskController> _logger;

        public NoteController(INoteRepository noteRepository, IExamRepository examRepository,
            UserManager<AppUser> userManager, ILogger<TaskController> logger)
        {
            _noteRepository = noteRepository;
            _examRepository = examRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [HttpGet("getNotesExam")]
        public async Task<ActionResult<List<Note>>> GetNotesExam(
            [FromQuery(Name = "Id экзамена")] int IdExam)
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _noteRepository.GetNoteListByExam(IdExam);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost("createNote")]
        public async Task<ActionResult<Note>> CreateTask(CreateNoteDto dto)
        {
            try
            {
                if (!_examRepository.ExamIsExists(dto.IdExam))
                {
                    return BadRequest("Такого экзамена нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var newN = new Note
                {
                    IdExam = dto.IdExam,
                    Content = dto.Content,
                };
                var createdN = _noteRepository.AddNote(newN);
                return Ok(createdN);
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
