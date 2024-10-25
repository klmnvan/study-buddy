package com.example.studybuddy.domain.network

import android.util.Log
import com.example.studybuddy.data.dto.LoginDto
import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.responses.LoginResp
import com.example.studybuddy.data.screens.LoginSt
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.json.Json


class ApiServiceImpl(private val client: HttpClient): ApiService {

    override suspend fun signIn(email: String, password: String): LoginResp {
        return try {
            val response = client.request(HttpRoutes.LOGIN) {
                method = HttpMethod.Post
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
        catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            LoginResp(error = e.message.toString())
        }
    }

}