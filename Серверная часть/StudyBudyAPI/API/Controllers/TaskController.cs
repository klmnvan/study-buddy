using Humanizer;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;
using System.ComponentModel.DataAnnotations;

namespace StudyBudyAPI.Controllers
{
    [Route("api/task")]
    [ApiController]
    public class TaskController : Controller
    {
        private readonly ITaskRepository _taskRepository;
        private readonly IDisciplineRepository _disciplineRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<TaskController> _logger;

        public TaskController(ITaskRepository taskRepository, IDisciplineRepository disciplineRepository,
            UserManager<AppUser> userManager, ILogger<TaskController> logger)
        {
            _taskRepository = taskRepository;
            _disciplineRepository = disciplineRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [HttpGet("getTaskUser")]
        public async Task<ActionResult<List<Models.DB.Task>>> GetTaskUser()
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _taskRepository.GetTaskListUser(appUser.Id);
                return Ok(listEntity);
            } 
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost("createTask")]
        public async Task<ActionResult<Models.DB.Task>> CreateTask(CreateTaskDto dto)
        {
            try
            {
                if (!(_disciplineRepository.DisciplineIsExists(dto.IdDiscipline) || dto.IdDiscipline == null))
                {
                    return BadRequest("Такого предмета нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var newTask = new Models.DB.Task
                {
                    Title = dto.Title,
                    Deadline = dto.Deadline,
                    IdDiscipline = dto.IdDiscipline,
                    Description = dto.Description,
                    IdUser = appUser.Id,
                    IsCompleted = false
                };
                if (dto.IdDiscipline == null) newTask.IdDiscipline = null;
                var createdTask = _taskRepository.AddTask(newTask);
                return Ok(createdTask);
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}"); 
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [HttpDelete("deleteTask")]
        public async Task<ActionResult> DeleteTask([FromQuery(Name = "Id задачи")] int IdTask)
        {
            try
            {
                if (!_taskRepository.TaskIsExists(IdTask))
                {
                    return BadRequest("Такой задачи нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_taskRepository.DeleteTask(IdTask))
                {
                    return StatusCode(500, "Ошибка удаления задачи");
                }
                return Ok("Задача удалена");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [HttpPut("updateTask")]
        public async Task<ActionResult> UpdateTask([FromBody] TaskDto dto)
        {
            try
            {
                if (!_taskRepository.TaskIsExists(dto.IdTask))
                {
                    return BadRequest("Такой задачи нет");
                }

                if (!_disciplineRepository.DisciplineIsExists(dto.IdDiscipline) && dto.IdDiscipline != null)
                {
                    return BadRequest("Такого предмета нет");
                }

                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);

                if (appUser == null)
                {
                    return Unauthorized();
                }

                var newEl = new Models.DB.Task
                {
                   IdTask = dto.IdTask,
                   Title = dto.Title,
                   Deadline = dto.Deadline,
                   IdUser = appUser.Id,
                   IdDiscipline = dto.IdDiscipline,
                   Description = dto.Description,
                   IsCompleted = dto.IsCompleted,
                };

                if (!_taskRepository.UpdateTask(newEl))
                {
                    return StatusCode(500, "Ошибка изменения задачи");
                }
                return Ok("Задача изменена");
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
