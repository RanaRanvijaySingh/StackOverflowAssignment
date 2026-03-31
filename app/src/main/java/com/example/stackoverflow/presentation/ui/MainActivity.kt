package com.example.stackoverflow.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackOverflowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePageView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

}

@Composable
fun HomePageView(modifier: Modifier) {

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