package com.example.stackoverflow.utils

class StringUtils {
    companion object {
        fun getInitials(name: String): String {
            val initials = name
                .trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .take(2)
                .joinToString("") { it.first().uppercaseChar().toString() }
            return initials
        }
    }
}