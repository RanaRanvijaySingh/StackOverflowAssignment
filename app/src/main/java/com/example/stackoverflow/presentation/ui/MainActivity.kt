package com.example.stackoverflow.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.presentation.states.UserUiState
import com.example.stackoverflow.presentation.ui.core.RetryButtonView
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
        is UserUiState.Loading -> LoadingView(modifier = modifier)

        is UserUiState.Error ->
            ErrorContentView(
                message = state.message,
                modifier = modifier,
                onRetry = { homeViewModel.retryLoadUsers() }
            )

        is UserUiState.Success ->
            UserListView(
                users = state.users,
                onFollowClick = { user -> homeViewModel.onFollowClick(user) }
            )
    }
}

@Composable
private fun LoadingView(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContentView(
    message: String,
    modifier: Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = message,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            RetryButtonView(
                onClick = onRetry,
                modifier = Modifier
            )
        }
    }
}
 
@Preview
@Composable
fun HomePageViewPreview() {
    StackOverflowTheme {
        UserListView(listOf<User>(
            User(1, "Rana Singh", "", false, 100),
            User(2, "Ranvijay Singh", "", false, 101)
        ), {})
    }
}