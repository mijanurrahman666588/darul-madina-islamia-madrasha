package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BentoPrimary,
    onPrimary = Color.White,
    primaryContainer = BentoPrimaryDark,
    onPrimaryContainer = BentoMintBorder,
    secondary = GoldLight,
    onSecondary = Color.Black,
    secondaryContainer = GoldAccent,
    tertiary = SkyLight,
    background = DarkCanvas,
    surface = DarkSurface,
    onBackground = BentoMintBorder,
    onSurface = Color.White,
    outline = BentoPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = BentoPrimaryDark,
    onPrimary = Color.White,
    primaryContainer = BentoMintSurface,
    onPrimaryContainer = BentoPrimaryDark,
    secondary = GoldAccent,
    onSecondary = Color.White,
    secondaryContainer = GoldContainer,
    tertiary = SkyAccent,
    tertiaryContainer = SkyLight,
    background = BentoLightBg,
    surface = BentoWhite,
    onBackground = BentoPrimaryDark,
    onSurface = BentoPrimaryDark,
    outline = BentoMintBorder
)

@Composable
fun DarulMadinaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded Islamic emerald theme active by default
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

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    DarulMadinaTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}

