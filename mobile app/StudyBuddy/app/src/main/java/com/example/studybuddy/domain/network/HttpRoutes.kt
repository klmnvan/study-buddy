package com.example.studybuddy.domain.network

/** Константы для обращения к API */
object HttpRoutes {
    private const val BASE_URL = "https://iis.ngknn.ru/ngknn/МамшеваЮС/10/api"
    const val LOGIN = "$BASE_URL/account/login"
    const val REGISTER = "$BASE_URL/account/register"
    const val GET_TASKS = "$BASE_URL/task/getTaskUSer"
    const val GET_DISCIPLINES = "$BASE_URL/discipline/getDisciplineUser"
    const val GET_TEACHERS = "$BASE_URL/teacher/getTeachersUser"
    const val GET_EXAMS = "$BASE_URL/exam/getExamsUser"
    const val GET_VALUES = "https://api.it-reshalo.ru/available?values=cabinet%2Cgroup%2Cteacher"
    const val GET_REQUIREMENTS = "$BASE_URL/requirement/GetAllRequirementsUser"
    const val GET_NOTES = "$BASE_URL/note/GetAllNotesUser"
    const val UPDATE_TASK = "$BASE_URL/task/updateTask"
    const val UPDATE_TEACHER = "$BASE_URL/teacher/updateTeacher"
    const val UPDATE_REQUIREMENT = "$BASE_URL/requirement/updateRequirement"
    const val UPDATE_DISCIPLINE = "$BASE_URL/discipline/updateDiscipline"
    const val UPDATE_EXAM = "$BASE_URL/exam/updateExam"
    const val UPDATE_NOTE = "$BASE_URL/note/updateNote"
    const val DELETE_TASK = "$BASE_URL/task/deleteTask"
    const val DELETE_TEACHER = "$BASE_URL/teacher/deleteTeacher"
    const val DELETE_DISCIPLINE = "$BASE_URL/discipline/deleteDiscipline"
    const val DELETE_REQUIREMENT = "$BASE_URL/requirement/deleteRequirement"
    const val DELETE_EXAM = "$BASE_URL/exam/deleteExam"
    const val DELETE_NOTE = "$BASE_URL/note/deleteNote"
    const val CREATE_TASK = "$BASE_URL/task/createTask"
    const val CREATE_DISC = "$BASE_URL/discipline/createDiscipline"
    const val CREATE_TEACHER = "$BASE_URL/teacher/createTeacher"
    const val CREATE_REQUIREMENT = "$BASE_URL/requirement/createRequirement"
    const val CREATE_EXAM = "$BASE_URL/exam/createExam"
    const val CREATE_NOTE = "$BASE_URL/note/createNote"
}