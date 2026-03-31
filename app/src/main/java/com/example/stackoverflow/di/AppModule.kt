package com.example.stackoverflow.di

import com.example.stackoverflow.data.remote.apis.StackOverflowApiService
import com.example.stackoverflow.data.repositoryimpls.UserRepositoryImpl
import com.example.stackoverflow.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: StackOverflowApiService): UserRepository =
        UserRepositoryImpl(apiService)
}