package com.example.studybuddy.domain.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Hilt Модуль, в котором описано как получать зависимость  с типом ApiServiceImpl  */
@Module
@InstallIn(SingletonComponent::class)
class ApiServiceProvider {

    @Provides
    fun provideService(): ApiServiceImpl {
        return ApiService.create()
    }

}