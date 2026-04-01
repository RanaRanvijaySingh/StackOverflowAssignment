package com.example.stackoverflow.data.repositoryimpls

import com.example.stackoverflow.FakeApiService
import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.local.entities.UserEntity
import com.example.stackoverflow.data.remote.dtos.UsersResponseDto
import com.example.stackoverflow.fakes.FakeUserDao
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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

        val result = repository.syncUsersFromRemote()

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
        val result = repository.syncUsersFromRemote()
        assertTrue(result.isEmpty())
        assertTrue(userDao.getUsers().isEmpty())
    }

    @Test
    fun `getStoredUsers returns data from database without syncing remote`() = runBlocking {
        val userDao = FakeUserDao()
        userDao.insertUser(
            UserEntity(
                id = 7,
                name = "Local only",
                imageUrl = null,
                isFollowing = true,
                repo = 99
            )
        )
        val repository = UserRepositoryImpl(
            FakeApiService(
                Response.error(
                    500,
                    "unused".toResponseBody("text/plain".toMediaTypeOrNull())
                )
            ),
            userDao
        )

        val result = repository.getStoredUsers()

        assertEquals(1, result.size)
        assertEquals(7, result.first().id)
        assertEquals("Local only", result.first().name)
        assertTrue(result.first().isFollowing)
    }

    @Test
    fun `syncUsersFromRemote preserves isFollowing when refreshing from api`() = runBlocking {
        val userDao = FakeUserDao()
        userDao.insertUser(
            UserEntity(
                id = TestHelper.USER_ID.toInt(),
                name = "Stale",
                imageUrl = "old",
                isFollowing = true,
                repo = 1
            )
        )
        val repository = UserRepositoryImpl(
            FakeApiService(Response.success(TestHelper.usersResponseDto)),
            userDao
        )

        val result = repository.syncUsersFromRemote()

        assertEquals(1, result.size)
        assertTrue(result.first().isFollowing)
        assertEquals(TestHelper.USER_NAME, result.first().name)
        assertEquals(TestHelper.USER_REPUTATION, result.first().repo)
    }

    @Test
    fun `syncUsersFromRemote returns cached database rows when api fails`() = runBlocking {
        val userDao = FakeUserDao()
        userDao.insertUser(
            UserEntity(
                id = 99,
                name = "Cached User",
                imageUrl = null,
                isFollowing = false,
                repo = 42
            )
        )
        val repository = UserRepositoryImpl(
            FakeApiService(
                Response.error(
                    500,
                    "error".toResponseBody("text/plain".toMediaTypeOrNull())
                )
            ),
            userDao
        )

        val result = repository.syncUsersFromRemote()

        assertEquals(1, result.size)
        assertEquals(99, result.first().id)
        assertEquals("Cached User", result.first().name)
    }

    @Test
    fun `setUserFollowing persists follow flag in database`() = runBlocking {
        val userDao = FakeUserDao()
        userDao.insertUser(
            UserEntity(
                id = 1,
                name = "User",
                imageUrl = null,
                isFollowing = false,
                repo = 10
            )
        )
        val repository = UserRepositoryImpl(
            FakeApiService(
                Response.error(
                    500,
                    "unused".toResponseBody("text/plain".toMediaTypeOrNull())
                )
            ),
            userDao
        )

        repository.setUserFollowing(1, true)

        assertTrue(userDao.getUsers().single().isFollowing)
        repository.setUserFollowing(1, false)
        assertFalse(userDao.getUsers().single().isFollowing)
    }
}
