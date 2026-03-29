package com.example.stackoverflow.domain.models

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val isFollowing: Boolean,
    val repo: Int
)