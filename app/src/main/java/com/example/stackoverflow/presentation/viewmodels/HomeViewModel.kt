package com.example.stackoverflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflow.constants.ERROR_LOAD_USERS
import com.example.stackoverflow.domain.usecases.GetUsersUseCase
import com.example.stackoverflow.presentation.states.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: GetUsersUseCase
) : ViewModel() {
    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState: StateFlow<UserUiState> = _userUiState

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            _userUiState.value = UserUiState.Loading
            try {
                val users = usecase()
                _userUiState.value = UserUiState.Success(users)
            } catch (exception: Exception) {
                _userUiState.value =
                    UserUiState.Error(exception.message ?: ERROR_LOAD_USERS)
            }
        }
    }
}
