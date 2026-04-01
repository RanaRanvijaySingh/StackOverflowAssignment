package com.example.stackoverflow.presentation.viewmodel

import com.example.stackoverflow.MainDispatcherRule
import com.example.stackoverflow.TestHelper
import com.example.stackoverflow.data.mappers.toDomain
import com.example.stackoverflow.domain.usecases.GetStoredUsersUseCase
import com.example.stackoverflow.domain.usecases.GetUsersUseCase
import com.example.stackoverflow.domain.usecases.SetUserFollowingUseCase
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
    fun `initial load shows loading then success when cache is empty and sync succeeds`() {
        val expectedUser = TestHelper.userDto.toDomain()
        val repository = FakeUserRepository().apply {
            storedUsers = emptyList()
            syncResult = listOf(expectedUser)
        }
        val viewModel = homeViewModel(repository)

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertEquals(1, (state as UserUiState.Success).users.size)
        assertEquals(TestHelper.USER_ID.toInt(), state.users.first().id)
        assertEquals(TestHelper.USER_NAME, state.users.first().name)
        assertEquals(TestHelper.USER_IMAGE_URL, state.users.first().imageUrl)
        assertEquals(TestHelper.USER_REPUTATION, state.users.first().repo)
    }

    @Test
    fun `initial load emits success with empty list when cache and sync have no users`() {
        val repository = FakeUserRepository().apply {
            storedUsers = emptyList()
            syncResult = emptyList()
        }
        val viewModel = homeViewModel(repository)

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertTrue((state as UserUiState.Success).users.isEmpty())
    }

    @Test
    fun `shows cached users immediately when database has data and sync succeeds`() {
        val cached = TestHelper.userDto.toDomain().copy(name = "Cached")
        val fresh = TestHelper.userDto.toDomain().copy(name = "Fresh")
        val repository = FakeUserRepository().apply {
            storedUsers = listOf(cached)
            syncResult = listOf(fresh)
        }
        val viewModel = homeViewModel(repository)

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertEquals("Fresh", (state as UserUiState.Success).users.single().name)
    }

    @Test
    fun `keeps cached users when sync fails`() {
        val cached = TestHelper.userDto.toDomain()
        val repository = FakeUserRepository().apply {
            storedUsers = listOf(cached)
            errorOnSync = RuntimeException("network")
        }
        val viewModel = homeViewModel(repository)

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Success)
        assertEquals(TestHelper.USER_NAME, (state as UserUiState.Success).users.single().name)
    }

    @Test
    fun `emits error when cache empty and sync fails`() {
        val repository = FakeUserRepository().apply {
            storedUsers = emptyList()
            errorOnSync = RuntimeException("network")
        }
        val viewModel = homeViewModel(repository)

        val state = viewModel.userUiState.value
        assertTrue(state is UserUiState.Error)
        assertEquals("network", (state as UserUiState.Error).message)
    }

    @Test
    fun `onFollowClick toggles following and persists via repository`() {
        val user = TestHelper.userDto.toDomain()
        val repository = FakeUserRepository().apply {
            storedUsers = emptyList()
            syncResult = listOf(user)
        }
        val viewModel = homeViewModel(repository)

        viewModel.onFollowClick(user)

        assertTrue(viewModel.userUiState.value is UserUiState.Success)
        assertTrue((viewModel.userUiState.value as UserUiState.Success).users.single().isFollowing)
        assertTrue(repository.storedUsers.single().isFollowing)
    }

    private fun homeViewModel(repository: FakeUserRepository): HomeViewModel =
        HomeViewModel(
            GetStoredUsersUseCase(repository),
            GetUsersUseCase(repository),
            SetUserFollowingUseCase(repository)
        )
}
