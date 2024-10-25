package com.example.studybuddy.domain.network

import android.util.Log
import com.example.studybuddy.data.dto.LoginDto
import com.example.studybuddy.data.dto.UserDto
import com.example.studybuddy.data.responses.LoginResp
import com.example.studybuddy.data.screens.LoginSt
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType


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
        } catch (e: Exception) {
            Log.d("Error ${e.message}", e.message.toString())
            return LoginResp(error = e.message.toString())
        }
    }

}