package com.example.stackoverflow.data.repositoryimpls

import com.example.stackoverflow.data.local.daos.UserDao
import com.example.stackoverflow.data.mappers.toDomain
import com.example.stackoverflow.data.mappers.toEntity
import com.example.stackoverflow.data.mappers.toUiModel
import com.example.stackoverflow.data.remote.apis.StackOverflowApiService
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: StackOverflowApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getStoredUsers(): List<User> =
        userDao.getUsers().map { entity -> entity.toUiModel() }

    override suspend fun syncUsersFromRemote(): List<User> {
        val response = apiService.getUsers()
        if (response.isSuccessful) {
            val items = response.body()?.items.orEmpty()
            val followingByUserId = userDao.getUsers().associate { it.id to it.isFollowing }
            val entities = items.map { dto ->
                val id = dto.accountId.toInt()
                dto.toDomain()
                    .copy(isFollowing = followingByUserId[id] ?: false)
                    .toEntity()
            }
            userDao.deleteAll()
            if (entities.isNotEmpty()) {
                userDao.insertUsers(entities)
            }
        }
        return userDao.getUsers().map { entity -> entity.toUiModel() }
    }

    override suspend fun setUserFollowing(userId: Int, isFollowing: Boolean) {
        userDao.updateFollowing(userId, isFollowing)
    }
}
