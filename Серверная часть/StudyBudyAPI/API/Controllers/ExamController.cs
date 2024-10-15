﻿using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using StudyBudyAPI.Dtos.CreateEntity;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;
using StudyBudyAPI.Repository;

namespace StudyBudyAPI.Controllers
{
    [Route("api/exam")]
    [ApiController]
    public class ExamController : Controller
    {
        private readonly IExamRepository _examRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly ILogger<DisciplineController> _logger;

        public ExamController(IExamRepository examRepository, 
            UserManager<AppUser> userManager, ILogger<DisciplineController> logger)
        {
            _examRepository = examRepository;
            _userManager = userManager;
            _logger = logger;
        }

        [HttpGet("getExamsUser")]
        public async Task<ActionResult<List<Exam>>> GetExamsUser()
        {
            try
            {
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var listEntity = _examRepository.GetExamListUser(appUser.Id);
                return Ok(listEntity);
            }
            catch (Exception ex)
            {
                return StatusCode(500, ex.Message);
            }
        }

        [HttpPost("createExam")]
        public async Task<ActionResult<Exam>> CreateExam(CreateExamDto dto)
        {
            try
            {
                if (dto.NumberTickets < 0) return BadRequest("Количество билетов не может быть отрицательным");
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                var newEntity = new Exam
                {
                    IdUser = appUser.Id,
                    Title = dto.Title,
                    Duration = dto.Duration,
                    NumberTickets = dto.NumberTickets,
                    DateExam = dto.DateExam
                };
                var createdEntity = _examRepository.AddExam(newEntity);
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

        [HttpDelete("deleteExam")]
        public async Task<ActionResult> DeleteExam([FromQuery(Name = "Id экзамена")] int IdExam)
        {
            try
            {
                if (!_examRepository.ExamIsExists(IdExam))
                {
                    return BadRequest("Такого экзамена нет");
                }
                var appUser = await _userManager.FindByNameAsync(User.Identity.Name);
                if (appUser == null)
                {
                    return Unauthorized();
                }
                if (!_examRepository.DeleteExam(IdExam))
                {
                    return StatusCode(500, "Ошибка удаления экзамена");
                }
                return Ok("Экзамен удален");
            }
            catch (Exception e)
            {
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogTrace($"Ошибка {e.GetType}:{e.Message}");
                _logger.LogInformation($"Ошибка {e}");
                return StatusCode(500, "Ошибка со стороны сервера");
            }
        }

        [HttpPut("updateExam")]
        public async Task<ActionResult> UpdateExam([FromBody] ExamDto dto)
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

                var newEl = new Exam
                {
                    IdExam = dto.IdExam,
                    Duration = dto.Duration,
                    NumberTickets = dto.NumberTickets,
                    DateExam = dto.DateExam,
                    IdUser = appUser.Id,
                };

                if (!_examRepository.UpdateExam(newEl))
                {
                    return StatusCode(500, "Ошибка изменения экзамена");
                }
                return Ok("Экзамен изменен");
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
