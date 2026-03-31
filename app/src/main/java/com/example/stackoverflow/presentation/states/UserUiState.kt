package com.example.stackoverflow.presentation.states

import com.example.stackoverflow.domain.models.User

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}
