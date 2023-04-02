package com.filimonov.mycrochet.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF984062),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9E3),
    onPrimaryContainer = Color(0xFF3E001E),

    secondary = Color(0xFF74565F),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFD9E3),
    onSecondaryContainer = Color(0xFF2B151C),

//    tertiary = Color.White,
//    onTertiary = Color.White,
//    tertiaryContainer = Color.White,
//    onTertiaryContainer = Color.White,

    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF201A1B),
    surfaceVariant = Color(0xFFF2DDE2),
    onSurfaceVariant = Color(0xFF514347),

    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A1B),

    outline = Color(0xFF837377)
)

@Composable
fun MyCrochetTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}