package com.example.stackoverflow.presentation.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.stackoverflow.constants.imageSize
import com.example.stackoverflow.constants.theme.GrayDark
import com.example.stackoverflow.constants.theme.OrangeLight
import com.example.stackoverflow.constants.theme.StackOverflowTheme
import com.example.stackoverflow.utils.StringUtils

@Composable
fun ProfilePicView(
    imageUrl: String?,
    name: String,
    modifier: Modifier = Modifier
) {
    var showInitials by remember { mutableStateOf(imageUrl.isNullOrBlank()) }
    if (!showInitials) {
        Box(
            modifier = Modifier.size(imageSize),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "$name prifile picture",
                onError = { showInitials = true },
                onSuccess = { showInitials = false },
                onLoading = { showInitials = true },
            )
        }
    } else {
        ProfileInitialsView(name)
    }
}

@Composable
fun ProfileInitialsView(name: String) {
    Box(
        modifier = Modifier
            .size(imageSize)
            .clip(CircleShape)
            .background(color = OrangeLight),
        contentAlignment = Alignment.Center,

        ) {
        Text(
            text = StringUtils.getInitials(name),
            color = GrayDark,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicViewPreview() {
    StackOverflowTheme {
        ProfilePicView(
            "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
            name = "Rana Ranvijay Singh",
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileInitialsViewPreview() {
    StackOverflowTheme {
        ProfileInitialsView(name = "Rana Ranvijay Singh")
    }
}