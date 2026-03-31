package com.example.stackoverflow.data.repositoryimpls

import com.example.stackoverflow.data.mappers.toDomain
import com.example.stackoverflow.data.remote.apis.StackOverflowApiService
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: StackOverflowApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        val response = apiService.getUsers()
        if (!response.isSuccessful) return emptyList()

        val users = response.body()?.items.orEmpty()
        return users.map { dto -> dto.toDomain() }
    }
}
