package com.example.stackoverflow.data.mappers

import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.local.entities.UserEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UserMapperTest {

    @Test
    fun `toDomain maps dto fields to user model`() {
        val dto = TestHelper.userDto

        val result = dto.toDomain()

        assertEquals(TestHelper.USER_ID.toInt(), result.id)
        assertEquals(TestHelper.USER_NAME, result.name)
        assertEquals(TestHelper.USER_IMAGE_URL, result.imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, result.repo)
    }

    @Test
    fun `toDomain sets isFollowing to false by default`() {
        val dto = TestHelper.userDto

        val result = dto.toDomain()

        assertFalse(result.isFollowing)
    }

    @Test
    fun `toUiModel maps entity fields to user model`() {
        val entity = UserEntity(
            id = TestHelper.USER_ID.toInt(),
            name = TestHelper.USER_NAME,
            imageUrl = TestHelper.USER_IMAGE_URL,
            isFollowing = false,
            repo = TestHelper.USER_REPUTATION
        )

        val result = entity.toUiModel()

        assertEquals(TestHelper.USER_ID.toInt(), result.id)
        assertEquals(TestHelper.USER_NAME, result.name)
        assertEquals(TestHelper.USER_IMAGE_URL, result.imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, result.repo)
        assertFalse(result.isFollowing)
    }

    @Test
    fun `toUiModel preserves isFollowing when true`() {
        val entity = UserEntity(
            id = 1,
            name = "User",
            imageUrl = null,
            isFollowing = true,
            repo = 50
        )

        val result = entity.toUiModel()

        assertTrue(result.isFollowing)
    }

    @Test
    fun `toUiModel preserves null imageUrl`() {
        val entity = UserEntity(
            id = 2,
            name = "No Avatar",
            imageUrl = null,
            isFollowing = false,
            repo = 0
        )

        val result = entity.toUiModel()

        assertNull(result.imageUrl)
    }
}
