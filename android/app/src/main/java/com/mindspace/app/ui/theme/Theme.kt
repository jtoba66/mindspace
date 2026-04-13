package com.mindspace.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    outline = Outline,
    outlineVariant = OutlineVariant,
    surfaceTint = SurfaceTint
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryFixedDim,
    onPrimary = Color(0xFF3A0D28),
    primaryContainer = Color(0xFF5A2146),
    onPrimaryContainer = Color(0xFFFFD6EE),
    inversePrimary = Primary,
    secondary = SecondaryFixedDim,
    onSecondary = Color(0xFF221434),
    secondaryContainer = Color(0xFF3D2858),
    onSecondaryContainer = Color(0xFFEEDCFF),
    tertiary = TertiaryFixedDim,
    onTertiary = Color(0xFF002837),
    tertiaryContainer = Color(0xFF11495F),
    onTertiaryContainer = Color(0xFFC8EAFF),
    background = Color(0xFF17121A),
    onBackground = Color(0xFFF6ECF4),
    surface = Color(0xFF17121A),
    onSurface = Color(0xFFF6ECF4),
    surfaceVariant = Color(0xFF342A36),
    onSurfaceVariant = Color(0xFFD9C2D8),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFFA992AB),
    outlineVariant = Color(0xFF524354),
    surfaceTint = PrimaryFixedDim
)

@Composable
fun MindSpaceTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
