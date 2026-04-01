package com.example.stackoverflow.fakes

import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.repositories.UserRepository

class FakeUserRepository : UserRepository {

    var storedUsers: List<User> = emptyList()
    var syncResult: List<User> = emptyList()
    var errorOnSync: Throwable? = null

    override suspend fun getStoredUsers(): List<User> = storedUsers

    override suspend fun syncUsersFromRemote(): List<User> {
        errorOnSync?.let { throw it }
        storedUsers = syncResult
        return storedUsers
    }

    override suspend fun setUserFollowing(userId: Int, isFollowing: Boolean) {
        storedUsers = storedUsers.map { user ->
            if (user.id == userId) user.copy(isFollowing = isFollowing) else user
        }
    }
}
