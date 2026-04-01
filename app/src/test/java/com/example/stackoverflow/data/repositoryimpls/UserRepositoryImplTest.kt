package com.example.stackoverflow.data.repositoryimpls

import com.example.stackoverflow.FakeApiService
import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import com.example.stackoverflow.fakes.FakeUserDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {

    @Test
    fun `getUsers persists api data to database then returns users from database`() = runBlocking {
        val api = FakeApiService(
            response = Response.success(TestHelper.usersResponseDto)
        )
        val userDao = FakeUserDao()
        val repository = UserRepositoryImpl(api, userDao)

        val result = repository.getUsers()

        assertEquals(1, result.size)
        assertEquals(TestHelper.USER_ID.toInt(), result.first().id)
        assertEquals(TestHelper.USER_NAME, result.first().name)
        assertEquals(TestHelper.USER_IMAGE_URL, result.first().imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, result.first().repo)
        assertTrue(!result.first().isFollowing)
        assertEquals(1, userDao.getUsers().size)
    }

    @Test
    fun `getUsers returns empty list when response body has empty items`() = runBlocking {
        val api = FakeApiService(
            response = Response.success(UsersResponseDto(items = emptyList()))
        )
        val userDao = FakeUserDao()
        val repository = UserRepositoryImpl(api, userDao)
        val result = repository.getUsers()
        assertTrue(result.isEmpty())
        assertTrue(userDao.getUsers().isEmpty())
    }
}
