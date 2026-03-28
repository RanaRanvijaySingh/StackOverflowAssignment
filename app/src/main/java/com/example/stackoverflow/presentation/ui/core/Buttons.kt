package com.example.stackoverflow.presentation.ui.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflow.constants.retry
import com.example.stackoverflow.constants.theme.StackOverflowTheme

@Composable
fun RetryButton(onClick: () -> Unit, modifier: Modifier) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = retry, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun RetryButtonPreview() {
    StackOverflowTheme {
        RetryButton({}, modifier = Modifier)
    }
}