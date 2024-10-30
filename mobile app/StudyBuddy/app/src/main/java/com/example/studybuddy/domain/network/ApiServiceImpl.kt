package com.example.studybuddy.domain.network

import android.util.Log
import androidx.room.RoomDatabase
import com.example.studybuddy.data.dto.LoginDto
import com.example.studybuddy.data.dto.RegisterDto
import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.ExamEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.responses.DefaultResp
import com.example.studybuddy.data.responses.GetExamsResp
import com.example.studybuddy.data.responses.GetTasksResp
import com.example.studybuddy.data.responses.LoginResp
import com.example.studybuddy.data.responses.RegisterResp
import com.example.studybuddy.domain.room.dao.DisciplineDao
import com.example.studybuddy.domain.room.dao.TaskDao
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

/** Реализация интерфейса, в котором описаны все методы для запросов к API + их кэшировние в локальную базу данных Room*/
class ApiServiceImpl(
    private val client: HttpClient,
    private val database: StudyBuddyDatabase,
    ): ApiService {

    override suspend fun signIn(email: String, password: String): LoginResp {
        return try {
            val response = client.post {
                url(HttpRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(LoginDto(email, password))
            }
            val responseBody = response.body<UserDto>()
            LoginResp(user = responseBody)
        }
        catch (e: ClientRequestException) {
            //приходит text/plain иногда, поэтому проверять надо всегда
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return LoginResp(error = errorMessage)
            }
            LoginResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            LoginResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            LoginResp(error = e.message.toString())
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConf: String,
        nickname: String
    ): RegisterResp {
        return try {
            val response = client.post {
                url(HttpRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                setBody(RegisterDto(nickname,email, password, passwordConf))
            }
            val responseBody = response.body<UserDto>()
            RegisterResp(user = responseBody)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return RegisterResp(error = errorMessage)
            }
            RegisterResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            RegisterResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            RegisterResp(error = e.message.toString())
        }
    }

    override suspend fun getTasks(token: String): GetTasksResp {
        return try {
            val tasks = client.get {
                url(HttpRoutes.GET_TASKS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val tasksBody = tasks.body<List<TaskEnt>>()
            database.taskDao.deleteAllTask()
            database.taskDao.insertTask(tasksBody)
            val disciplines = client.get {
                url(HttpRoutes.GET_DISCIPLINES)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val disciplinesBody = disciplines.body<List<DisciplineEnt>>()
            database.disciplineDao.deleteAllDisc()
            database.disciplineDao.insertDisc(disciplinesBody)
            GetTasksResp(listTask = tasksBody, listDisc = disciplinesBody)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return GetTasksResp(error = errorMessage)
            }
            GetTasksResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            GetTasksResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            GetTasksResp(error = e.message.toString())
        }
    }

    override suspend fun getExams(token: String): GetExamsResp {
        return try {
            val exams = client.get {
                url(HttpRoutes.GET_EXAMS)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val body = exams.body<List<ExamEnt>>()
            database.examDao.deleteAllExams()
            database.examDao.insertExam(body)
            GetExamsResp(listExams = body)
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return GetExamsResp(error = errorMessage)
            }
            GetExamsResp(error = e.response.body<String>())
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            GetExamsResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            GetExamsResp(error = e.message.toString())
        }
    }

    override suspend fun updateTask(token: String, task: TaskEnt): DefaultResp {
        return try {
            val response = client.put {
                url(HttpRoutes.UPDATE_TASK)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
                setBody(task)
            }
            if(response.status.isSuccess()) {
                database.taskDao.updateTask(task)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

    override suspend fun deleteTask(token: String, task: TaskEnt): DefaultResp {
        return try {
            val response = client.delete {
                url(HttpRoutes.DELETE_TASK + "?Id задачи=${task.idTask}")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            if(response.status.isSuccess()) {
                database.taskDao.deleteTask(task)
            }
            DefaultResp()
        }
        catch (e: ClientRequestException) {
            if(e.response.contentType()?.match(ContentType.Application.Json) == true){
                val errorMessage = Json.decodeFromString<String>(e.response.body())
                return DefaultResp(error = errorMessage)
            }
            DefaultResp(error = "Ошибка: ${e}")
        }
        catch (e: ServerResponseException) {
            Log.d("Error ${e.response.status}", e.message)
            DefaultResp(error = "Ошибка сервера: ${e.response.status}")
        }
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            DefaultResp(error = "Ошибка: ${e.message.toString()}")
        }
    }

}