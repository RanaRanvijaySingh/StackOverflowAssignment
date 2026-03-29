package com.example.stackoverflow.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stackoverflow.constants.repo
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.domain.models.User
import com.example.stackoverflow.presentation.ui.core.FollowButtonView
import com.example.stackoverflow.presentation.ui.core.ProfilePicView
import com.example.stackoverflow.presentation.ui.core.TextViewPrimary
import com.example.stackoverflow.presentation.ui.core.TextViewSecondary

@Composable
fun UserListItemView(user: User, onFollowClick: (User) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProfilePicView(
            imageUrl = user.imageUrl,
            name = user.name,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextViewPrimary(user.name)
            TextViewSecondary("${user.repo} $repo")
        }
        FollowButtonView(
            onClick = { onFollowClick(user) },
            modifier = Modifier,
            isFollowing = user.isFollowing
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemViewPreview() {
    StackOverflowTheme {
        UserListItemView(
            user = User(
                1,
                "Rana Ranvijay Singh",
                null, false, 100
            ),
            onFollowClick = { }
        )
    }
}