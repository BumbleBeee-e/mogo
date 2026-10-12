package com.jarves.mh.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ---------------------------------------------------------------------------
// SHANE-AI brand palette — navy blue
// Replaces the original orange-accent scheme. Keep the `Pocket*` symbol names so
// the ~200 existing call sites across the UI keep compiling unchanged.
// ---------------------------------------------------------------------------

// Brand blue, used for primary actions and the active selection state.
val PocketOrange = Color(0xFF3B82F6)
val PocketBlue = Color(0xFF60A5FA)
val PocketGreen = Color(0xFF38BDF8)

// Deep navy surfaces, darkest to lightest.
val PocketBackground = Color(0xFF0A1220)
val PocketSurface = Color(0xFF111C2E)
val PocketSurfaceVariant = Color(0xFF17253A)
val PocketOutline = Color(0xFF24354C)

private val DarkColors = darkColorScheme(
    primary = PocketOrange,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1E3A5F),
    onPrimaryContainer = Color(0xFFD3E3FD),
    secondary = PocketBlue,
    onSecondary = Color(0xFF06152E),
    tertiary = PocketGreen,
    onTertiary = Color(0xFF04202E),
    background = PocketBackground,
    onBackground = Color(0xFFE2E8F0),
    surface = PocketSurface,
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = PocketSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = PocketOutline,
    outlineVariant = Color(0xFF33455E),
    error = Color(0xFFEF6B6B),
    onError = Color(0xFF2B0A0A),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF1D4ED8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDCE7FE),
    onPrimaryContainer = Color(0xFF0B1F4D),
    secondary = Color(0xFF2563EB),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF0369A1),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF5F7FB),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE6EDF7),
    onSurfaceVariant = Color(0xFF52627A),
    outline = Color(0xFFC7D2E4),
    outlineVariant = Color(0xFFDCE4F0),
)

enum class AppThemeMode { SYSTEM, DARK, LIGHT }

@Composable
fun PocketTheme(themeMode: AppThemeMode = AppThemeMode.SYSTEM, content: @Composable () -> Unit) {
    val isDark = when (themeMode) {
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !isDark
            insetsController.isAppearanceLightNavigationBars = !isDark
        }
    }

    MaterialTheme(
        colorScheme = if (isDark) DarkColors else LightColors,
        content = content,
    )
}
