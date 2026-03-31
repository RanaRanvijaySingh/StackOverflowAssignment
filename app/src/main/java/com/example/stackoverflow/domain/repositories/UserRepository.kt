package com.example.stackoverflow.domain.repositories

import com.example.stackoverflow.domain.models.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}