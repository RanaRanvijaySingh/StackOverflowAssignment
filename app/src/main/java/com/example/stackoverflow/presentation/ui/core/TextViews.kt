package com.example.stackoverflow.presentation.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.constants.theme.Typography

@Composable
fun TextViewPrimary(text: String) {
    Text(
        text = text,
        style = Typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun TextViewSecondary(text: String) {
    Text(
        text = text,
        style = Typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview(showBackground = true)
@Composable
fun TextViewPrimaryPreview() {
    StackOverflowTheme {
        Column() {
            TextViewPrimary("Primary text example")
            TextViewSecondary("Secondary text example")
        }
    }
}