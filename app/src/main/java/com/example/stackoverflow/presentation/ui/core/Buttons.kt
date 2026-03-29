package com.example.stackoverflow.presentation.ui.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflow.constants.follow
import com.example.stackoverflow.constants.following
import com.example.stackoverflow.constants.retry
import com.example.stackoverflow.constants.theme.StackOverflowTheme

@Composable
fun RetryButtonView(onClick: () -> Unit, modifier: Modifier) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = retry, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun FollowButtonView(onClick: () -> Unit, isFollowing: Boolean, modifier: Modifier) {
    val containerColor by animateColorAsState(
        targetValue = if (isFollowing)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.secondary,
        label = "containerColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isFollowing)
            MaterialTheme.colorScheme.onPrimaryContainer
        else
            MaterialTheme.colorScheme.onSecondaryContainer,
        label = "contentColor"
    )
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            containerColor = containerColor
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = if (isFollowing) following else follow,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RetryButtonViewPreview() {
    StackOverflowTheme {
        RetryButtonView({}, modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun FollowButtonViewPreview() {
    StackOverflowTheme {
        Column() {
            FollowButtonView({}, modifier = Modifier, isFollowing = false)
            FollowButtonView({}, modifier = Modifier, isFollowing = true)
        }
    }
}