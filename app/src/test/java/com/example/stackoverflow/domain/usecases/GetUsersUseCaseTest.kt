package com.example.stackoverflow.domain.usecases

import com.example.stackoverflow.FakeApiService
import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import com.example.stackoverflow.data.repositoryimpls.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class GetUsersUseCaseTest {

    @Test
    fun `invoke returns users from repository`() = runBlocking {
        val repository = UserRepositoryImpl(
            FakeApiService(Response.success(TestHelper.usersResponseDto))
        )
        val getUsersUseCase = GetUsersUseCase(repository)

        val result = getUsersUseCase()

        assertEquals(1, result.size)
        assertEquals(TestHelper.USER_ID.toInt(), result.first().id)
        assertEquals(TestHelper.USER_NAME, result.first().name)
        assertEquals(TestHelper.USER_IMAGE_URL, result.first().imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, result.first().repo)
        assertTrue(!result.first().isFollowing)
    }

    @Test
    fun `invoke returns empty list when repository has no users`() = runBlocking {
        val repository = UserRepositoryImpl(
            FakeApiService(Response.success(UsersResponseDto(items = emptyList())))
        )
        val getUsersUseCase = GetUsersUseCase(repository)

        val result = getUsersUseCase()

        assertTrue(result.isEmpty())
    }
}
