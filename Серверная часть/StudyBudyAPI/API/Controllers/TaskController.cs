using Humanizer;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
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
    }

}
