package com.example.stackoverflow.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun `getInitials returns two initials for full name`() {
        val result = StringUtils.getInitials("Rana Singh")
        assertEquals("RS", result)
    }

    @Test
    fun `getInitials returns one initials for one name`() {
        val result = StringUtils.getInitials("Rana")
        assertEquals("R", result)
    }

    @Test
    fun `getInitials returns capital initials for name in small cases`() {
        val result = StringUtils.getInitials("rana singh")
        assertEquals("RS", result)
    }

    @Test
    fun `getInitials returns single capital initials for name in small case single work`() {
        val result = StringUtils.getInitials("rana")
        assertEquals("R", result)
    }

    @Test
    fun `getInitials returns uppercase initials for mixed case name`() {
        val result = StringUtils.getInitials("rAna siNgh")
        assertEquals("RS", result)
    }

    @Test
    fun `getInitials handles leading and trailing spaces`() {
        val result = StringUtils.getInitials(" rana singh ")
        assertEquals("RS", result)
    }

    @Test
    fun `getInitials handles multiple spaces between words`() {
        val result = StringUtils.getInitials(" rana       singh ")
        assertEquals("RS", result)
    }
}