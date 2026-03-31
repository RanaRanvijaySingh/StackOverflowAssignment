package com.example.stackoverflow.data.repositoryimpls

import FakeApiService
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import com.example.stackoverflow.TestHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {

    @Test
    fun `getUsers returns mapped users on successful response`() = runBlocking {
        val api = FakeApiService(
            response = Response.success(TestHelper.usersResponseDto)
        )
        val repository = UserRepositoryImpl(api)

        val result = repository.getUsers()

        assertEquals(1, result.size)
        assertEquals(TestHelper.USER_ID.toInt(), result.first().id)
        assertEquals(TestHelper.USER_NAME, result.first().name)
        assertEquals(TestHelper.USER_IMAGE_URL, result.first().imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, result.first().repo)
        assertTrue(!result.first().isFollowing)
    }

    @Test
    fun `getUsers returns empty list when response body has empty items`() = runBlocking {
        val api = FakeApiService(
            response = Response.success(UsersResponseDto(items = emptyList()))
        )
        val repository = UserRepositoryImpl(api)
        val result = repository.getUsers()
        assertTrue(result.isEmpty())
    }
}
