package com.example.stackoverflow.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class UsersResponseDto(
    @SerializedName("items")
    val items: List<UserDto>
)

data class UserDto(
    @SerializedName("account_id")
    val accountId: Long,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("reputation")
    val reputation: Int
)
