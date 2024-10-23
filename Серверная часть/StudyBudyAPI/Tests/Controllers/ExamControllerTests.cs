using AutoMapper;
using FakeItEasy;
using FluentAssertions;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using StudyBudyAPI.Controllers;
using StudyBudyAPI.Dtos.DB;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tests.Controllers
{
    public class ExamControllerTests
    {
        private readonly IExamRepository _examRepository;
        private readonly UserManager<AppUser> _userManager;
        private readonly IMapper _mapper;
        private readonly ILogger<ExamController> _logger;

        public ExamControllerTests()
        {
            _examRepository = A.Fake<IExamRepository>();
            _userManager = A.Fake<UserManager<AppUser>>();
            _mapper = A.Fake<IMapper>();
            _logger = A.Fake<ILogger<ExamController>>();
        }

        [Fact]
        public void ExamControllerTests_GetExams_ReturnOK()
        {
            //Arrange
            var exams = A.Fake<ICollection<ExamDto>>();
            var examList = A.Fake<List<ExamDto>>();
            A.CallTo(() => _mapper.Map<List<ExamDto>>(exams)).Returns(examList);
            var controller = new ExamController(_examRepository, _userManager, _logger);

            //Act
            var result = controller.GetExamsUser();

            //Assert
            result.Should().NotBeNull();
            result.Should().BeOfType(typeof(OkObjectResult));
        }
    }
}
