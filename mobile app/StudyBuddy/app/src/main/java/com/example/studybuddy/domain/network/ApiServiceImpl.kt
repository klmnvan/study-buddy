package com.example.studybuddy.domain.network

import android.util.Log
import com.example.studybuddy.data.dto.LoginDto
import com.example.studybuddy.data.dto.RegisterDto
import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.responses.GetTasksResp
import com.example.studybuddy.data.responses.LoginResp
import com.example.studybuddy.data.responses.RegisterResp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

/** Реализация интерфейса, в котором описаны все методы для запросов к API */
class ApiServiceImpl(private val client: HttpClient): ApiService {

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
            val response = client.post {
                url(HttpRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${token}")
                }
            }
            val responseBody = response.body<List<TaskEnt>>()
            GetTasksResp(listTask = responseBody)
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

}