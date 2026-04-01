package com.example.stackoverflow.domain.repositories

import com.example.stackoverflow.domain.models.User

interface UserRepository {

    suspend fun getStoredUsers(): List<User>

    suspend fun syncUsersFromRemote(): List<User>

    suspend fun setUserFollowing(userId: Int, isFollowing: Boolean)
}