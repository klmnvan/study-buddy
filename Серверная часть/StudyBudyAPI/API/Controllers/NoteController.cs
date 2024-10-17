using AutoMapper;
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

        [SwaggerOperation(Summary = "Получение всех заметок у экзамена")]
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

        [SwaggerOperation(Summary = "Создание заметки к экзамену")]
        [HttpPost("createNote")]
        public async Task<ActionResult<Note>> CreateNote(CreateNoteDto dto)
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
                if (_noteRepository.IsDuplicate(newN, appUser.Id))
                {
                    return BadRequest("Заметка с таким содержанием уже есть");
                }
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

        [SwaggerOperation(Summary = "Удаление заметки к экзамену")]
        [HttpDelete("deleteNote")]
        public async Task<ActionResult> DeleteNote([FromQuery(Name = "Id заметки")] int IdNote)
        {
            try
            {
                if (!_noteRepository.NoteIsExists(IdNote))
                {
                    return BadRequest("Такой заметки нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_noteRepository.DeleteNote(IdNote))
                {
                    return StatusCode(500, "Ошибка удаления заметки");
                }
                return Ok("Заметка удалена");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [SwaggerOperation(Summary = "Изменение заметки у экзамена")]
        [HttpPut("updateNote")]
        public async Task<ActionResult> UpdateNote([FromBody] NoteDto dto)
        {
            try
            {
                if (!_noteRepository.NoteIsExists(dto.IdNote))
                {
                    return BadRequest("Такой заметки нет");
                }
                if (!_examRepository.ExamIsExists(dto.IdExam))
                {
                    return BadRequest("Такого экзамена нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }

                var newEl = new Note
                {
                    IdNote = dto.IdNote,
                    IdExam = dto.IdExam,
                    Content = dto.Content,
                };

                if (_noteRepository.IsDuplicate(newEl, appUser.Id))
                {
                    return BadRequest("Заметка с таким содержанием уже есть");
                }

                if (!_noteRepository.UpdateNote(newEl))
                {
                    return StatusCode(500, "Ошибка изменения заметки");
                }
                return Ok("Заметка изменена");
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
