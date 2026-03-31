package com.example.stackoverflow.presentation.viewmodel

import com.example.stackoverflow.MainDispatcherRule
import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.mappers.toDomain
import com.example.stackoverflow.domain.usecases.GetUsersUseCase
import com.example.stackoverflow.fakes.FakeUserRepository
import com.example.stackoverflow.presentation.states.UserUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial load emits success with users from use case`() {
        val expectedUser = TestHelper.userDto.toDomain()
        val repository = FakeUserRepository().apply {
            usersResult = listOf(expectedUser)
        }
        val viewModel = HomeViewModel(GetUsersUseCase(repository))

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertEquals(1, (state as UserUiState.Success).users.size)
        assertEquals(TestHelper.USER_ID.toInt(), state.users.first().id)
        assertEquals(TestHelper.USER_NAME, state.users.first().name)
        assertEquals(TestHelper.USER_IMAGE_URL, state.users.first().imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, state.users.first().repo)
    }

    @Test
    fun `initial load emits success with empty list when repository returns no users`() {
        val repository = FakeUserRepository().apply { usersResult = emptyList() }
        val viewModel = HomeViewModel(GetUsersUseCase(repository))

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertTrue((state as UserUiState.Success).users.isEmpty())
    }
}
