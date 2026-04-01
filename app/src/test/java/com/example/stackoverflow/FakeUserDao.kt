package com.example.stackoverflow.fakes

import com.example.stackoverflow.data.local.daos.UserDao
import com.example.stackoverflow.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserDao : UserDao {

    private val items = mutableListOf<UserEntity>()

    override fun observeUsers(): Flow<List<UserEntity>> = flow {
        emit(items.toList().sortedByDescending { it.repo })
    }

    override suspend fun getUsers(): List<UserEntity> =
        items.toList().sortedByDescending { it.repo }

    override suspend fun insertUsers(users: List<UserEntity>) {
        users.forEach { user -> items.removeAll { it.id == user.id } }
        items.addAll(users)
    }

    override suspend fun insertUser(user: UserEntity) {
        items.removeAll { it.id == user.id }
        items.add(user)
    }

    override suspend fun deleteAll() {
        items.clear()
    }
}
