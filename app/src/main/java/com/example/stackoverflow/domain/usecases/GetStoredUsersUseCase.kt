package com.example.stackoverflow.domain.usecases

import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.repositories.UserRepository
import javax.inject.Inject

class GetStoredUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): List<User> = userRepository.getStoredUsers()
}
