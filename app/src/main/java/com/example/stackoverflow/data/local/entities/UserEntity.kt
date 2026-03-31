package com.example.stackoverflow.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val isFollowing: Boolean,
    val repo: Int
)
