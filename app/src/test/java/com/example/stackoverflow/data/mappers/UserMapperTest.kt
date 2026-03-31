package com.example.stackoverflow.data.mappers

import com.example.stackoverflow.TestHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
}
