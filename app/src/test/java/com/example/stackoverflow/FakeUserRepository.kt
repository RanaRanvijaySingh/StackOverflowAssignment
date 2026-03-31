package com.example.stackoverflow.fakes

import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.repositories.UserRepository

class FakeUserRepository : UserRepository {

    var usersResult: List<User> = emptyList()
    var errorToThrow: Throwable? = null

    override suspend fun getUsers(): List<User> {
        errorToThrow?.let { throw it }
        return usersResult
    }
}
