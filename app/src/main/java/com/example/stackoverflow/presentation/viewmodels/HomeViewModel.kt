package com.example.stackoverflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stackoverflow.presentation.states.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState: StateFlow<UserUiState> = _userUiState

    init {
        getUsers()
    }

    private fun getUsers() {

    }
}
