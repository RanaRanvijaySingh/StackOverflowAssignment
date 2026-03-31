package com.example.stackoverflow.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.presentation.states.UserUiState
import com.example.stackoverflow.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackOverflowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePageView(
                        modifier = Modifier.padding(innerPadding),
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }

}

@Composable
fun HomePageView(
    modifier: Modifier,
    homeViewModel: HomeViewModel
) {
    val userUiState by homeViewModel.userUiState.collectAsState()
    when (val state = userUiState) {
        is UserUiState.Loading -> Text(text = "Loading users...", modifier = modifier)
        is UserUiState.Error -> Text(text = state.message, modifier = modifier)
        is UserUiState.Success -> UserListView(users = state.users, onFollowClick = {})
    }
}

val users = listOf<User>(
    User(1, "Rana Singh", "", false, 100),
    User(2, "Ranvijay Singh", "", false, 101)
)

@Preview
@Composable
fun HomePageViewPreview() {
    StackOverflowTheme {
        UserListView(users, {})
    }
}