package com.example.stackoverflow.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.domain.models.User

@Composable
fun UserListView(
    users: List<User>,
    onFollowClick: (User) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(
            items = users,
            key = { it.id }
        ) { user ->
            UserListItemView(user = user, onFollowClick)
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 0.5.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListViewPreview() {
    StackOverflowTheme {
        UserListView(
            users = listOf(
                User(1, "Rana Singh", "", false, 100),
                User(2, "Ranvijay Singh", "", false, 101),
            ),
            {}
        )
    }
}