package com.example.stackoverflow.data.mappers

import com.example.stackoverflow.data.remote.dtos.UserDto
import com.example.stackoverflow.domain.models.User

fun UserDto.toDomain(): User =
    User(
        id = accountId.toInt(),
        name = displayName,
        imageUrl = profileImage,
        isFollowing = false,
        repo = reputation
    )
