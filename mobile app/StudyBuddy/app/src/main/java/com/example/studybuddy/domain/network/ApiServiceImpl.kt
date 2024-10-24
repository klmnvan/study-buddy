package com.example.studybuddy.domain.network

import io.ktor.client.HttpClient

class ApiServiceImpl(private val client: HttpClient): ApiService {
}