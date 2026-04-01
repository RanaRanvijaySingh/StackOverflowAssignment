package com.example.stackoverflow.domain.usecases

import com.example.stackoverflow.domain.repositories.UserRepository
import javax.inject.Inject

class SetUserFollowingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userId: Int, isFollowing: Boolean) {
        userRepository.setUserFollowing(userId, isFollowing)
    }
}
