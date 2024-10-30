package com.example.studybuddy.domain.network

/** Константы для обращения к API */
object HttpRoutes {
    private const val BASE_URL = "https://iis.ngknn.ru/ngknn/МамшеваЮС/10/api"
    const val LOGIN = "$BASE_URL/account/login"
    const val REGISTER = "$BASE_URL/account/register"
    const val INFORMATION = "$BASE_URL/account/accountInfo"
    const val GET_TASKS = "$BASE_URL/task/getTaskUSer"
    const val GET_DISCIPLINES = "$BASE_URL/discipline/getDisciplineUser"
    const val GET_EXAMS = "$BASE_URL/exam/getExamsUser"
    const val UPDATE_TASK = "$BASE_URL/task/updateTask"
    const val DELETE_TASK = "$BASE_URL/task/deleteTask"
}