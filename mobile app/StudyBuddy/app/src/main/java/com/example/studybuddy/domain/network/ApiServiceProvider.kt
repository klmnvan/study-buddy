package com.example.studybuddy.domain.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceProvider {

    @Provides
    fun provideService(): ApiServiceImpl {
        return ApiService.create()
    }

}