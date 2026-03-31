package com.example.stackoverflow

import com.example.stackoverflow.data.remote.dtos.UserDto
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto

class TestHelper {
    companion object {
        
        const val USER_ID = 100L
        const val USER_NAME = "Rana Ranvijay Singh"
        const val USER_IMAGE_URL = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG"
        const val USER_REPUTATION = 1001
        val userDto = UserDto(
            accountId = USER_ID,
            displayName = USER_NAME,
            profileImage = USER_IMAGE_URL,
            reputation = USER_REPUTATION
        )

        val usersResponseDto = UsersResponseDto(
            items = listOf(userDto)
        )
    }
}