package com.example.stackoverflow.data.mappers

import com.example.stackoverflow.data.local.entities.UserEntity
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

fun UserEntity.toUiModel(): User =
    User(
        id = id,
        name = name,
        imageUrl = imageUrl,
        isFollowing = isFollowing,
        repo = repo
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        isFollowing = isFollowing,
        repo = repo
    )
