package com.example.stackoverflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflow.constants.ERROR_LOAD_USERS
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.domain.usecases.GetStoredUsersUseCase
import com.example.stackoverflow.domain.usecases.GetUsersUseCase
import com.example.stackoverflow.domain.usecases.SetUserFollowingUseCase
import com.example.stackoverflow.presentation.states.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoredUsersUseCase: GetStoredUsersUseCase,
    private val syncUsersFromRemoteUseCase: GetUsersUseCase,
    private val setUserFollowingUseCase: SetUserFollowingUseCase
) : ViewModel() {
    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState: StateFlow<UserUiState> = _userUiState

    init {
        loadUsers()
    }

    fun retryLoadUsers() {
        loadUsers()
    }

    fun onFollowClick(user: User) {
        viewModelScope.launch {
            val isFollowing = !user.isFollowing
            setUserFollowingUseCase(user.id, isFollowing)
            val current = _userUiState.value
            if (current is UserUiState.Success) {
                _userUiState.value = UserUiState.Success(
                    current.users.map { u ->
                        if (u.id == user.id) u.copy(isFollowing = isFollowing) else u
                    }
                )
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val cached = getStoredUsersUseCase()
            if (cached.isNotEmpty()) {
                _userUiState.value = UserUiState.Success(cached)
            } else {
                _userUiState.value = UserUiState.Loading
            }
            try {
                val fresh = syncUsersFromRemoteUseCase()
                _userUiState.value = UserUiState.Success(fresh)
            } catch (exception: Exception) {
                when (val current = _userUiState.value) {
                    is UserUiState.Success -> Unit
                    else -> {
                        _userUiState.value =
                            UserUiState.Error(exception.message ?: ERROR_LOAD_USERS)
                    }
                }
            }
        }
    }
}
