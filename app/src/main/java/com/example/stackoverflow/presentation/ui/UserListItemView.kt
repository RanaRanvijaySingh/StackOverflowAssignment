package com.example.stackoverflow.presentation.ui

import android.widget.Space
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
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.presentation.ui.core.ProfilePicView
import com.example.stackoverflow.presentation.ui.core.TextViewPrimary
import com.example.stackoverflow.presentation.ui.core.TextViewSecondary

@Composable
fun UserListItemView() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProfilePicView(
            imageUrl = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
            name = "Rana Ranvijay Singh", modifier = Modifier
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextViewPrimary("Rana Ranvijay Singh")
            TextViewSecondary("8606 repo")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemViewPreview() {
    StackOverflowTheme {
        UserListItemView()
    }
}