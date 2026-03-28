package com.example.stackoverflow.constants.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = OrangeDark,
    onPrimary = White,
    secondary = OrangeLight,
    onSecondary = GrayDark,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    outline = Gray,
    error = Red,
    onError = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = White,
    secondary = OrangeLight,
    onSecondary = GrayDark,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    onSurfaceVariant = Gray,
    outline = Gray,
    error = Red,
    onError = White,
)

@Composable
fun StackOverflowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}