package com.funkyotc.puzzleverse.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.funkyotc.puzzleverse.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim
)

private val InterFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider)
)

private val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

@Composable
fun PuzzleVerseTheme(
    activeTheme: String = "default",
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when (activeTheme) {
        "ocean" -> {
            if (darkTheme) {
                darkColorScheme(
                    primary = Color(0xFF0288D1),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFF01579B),
                    onPrimaryContainer = Color(0xFFE1F5FE),
                    secondary = Color(0xFF00B0FF),
                    background = Color(0xFF001F3F),
                    onBackground = Color(0xFFE1F5FE),
                    surface = Color(0xFF002F5F),
                    onSurface = Color(0xFFE1F5FE),
                    surfaceVariant = Color(0xFF003F7F),
                    onSurfaceVariant = Color(0xFFB3E5FC),
                    outline = Color(0xFF0288D1)
                )
            } else {
                lightColorScheme(
                    primary = Color(0xFF0277BD),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFFB3E5FC),
                    onPrimaryContainer = Color(0xFF01579B),
                    secondary = Color(0xFF0091EA),
                    background = Color(0xFFF0F9FF),
                    onBackground = Color(0xFF01579B),
                    surface = Color(0xFFE1F5FE),
                    onSurface = Color(0xFF01579B),
                    surfaceVariant = Color(0xFFB3E5FC),
                    onSurfaceVariant = Color(0xFF0277BD),
                    outline = Color(0xFF0277BD)
                )
            }
        }
        "forest" -> {
            if (darkTheme) {
                darkColorScheme(
                    primary = Color(0xFF90A955),
                    onPrimary = Color(0xFF132A13),
                    primaryContainer = Color(0xFF31572C),
                    onPrimaryContainer = Color(0xFFECF39E),
                    secondary = Color(0xFF4F772D),
                    background = Color(0xFF132A13),
                    onBackground = Color(0xFFECF39E),
                    surface = Color(0xFF1A3A1A),
                    onSurface = Color(0xFFECF39E),
                    surfaceVariant = Color(0xFF2D5A27),
                    onSurfaceVariant = Color(0xFFECF39E),
                    outline = Color(0xFF90A955)
                )
            } else {
                lightColorScheme(
                    primary = Color(0xFF2E7D32),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFFC7EFCF),
                    onPrimaryContainer = Color(0xFF1B431C),
                    secondary = Color(0xFF68A357),
                    background = Color(0xFFF1F7ED),
                    onBackground = Color(0xFF1B431C),
                    surface = Color(0xFFE8F5E9),
                    onSurface = Color(0xFF1B431C),
                    surfaceVariant = Color(0xFFC7EFCF),
                    onSurfaceVariant = Color(0xFF2E7D32),
                    outline = Color(0xFF2E7D32)
                )
            }
        }
        "sunset" -> {
            if (darkTheme) {
                darkColorScheme(
                    primary = Color(0xFFFF7043),
                    onPrimary = Color(0xFF2B1009),
                    primaryContainer = Color(0xFFBF360C),
                    onPrimaryContainer = Color(0xFFFFE0B2),
                    secondary = Color(0xFFE65100),
                    background = Color(0xFF2B1009),
                    onBackground = Color(0xFFFFE0B2),
                    surface = Color(0xFF3E1A0F),
                    onSurface = Color(0xFFFFE0B2),
                    surfaceVariant = Color(0xFF5D2A1B),
                    onSurfaceVariant = Color(0xFFFFE0B2),
                    outline = Color(0xFFFF7043)
                )
            } else {
                lightColorScheme(
                    primary = Color(0xFFD84315),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFFFFE0B2),
                    onPrimaryContainer = Color(0xFFD84315),
                    secondary = Color(0xFFFF7043),
                    background = Color(0xFFFFF8F6),
                    onBackground = Color(0xFF3E2723),
                    surface = Color(0xFFFBE9E7),
                    onSurface = Color(0xFF3E2723),
                    surfaceVariant = Color(0xFFFFE0B2),
                    onSurfaceVariant = Color(0xFFD84315),
                    outline = Color(0xFFD84315)
                )
            }
        }
        "cyberpunk" -> {
            darkColorScheme(
                primary = Color(0xFFFF003C),
                onPrimary = Color(0xFF0D0221),
                primaryContainer = Color(0xFF900020),
                onPrimaryContainer = Color(0xFF00FF9F),
                secondary = Color(0xFF00FF9F),
                secondaryContainer = Color(0xFF0A2E24),
                onSecondaryContainer = Color(0xFF00FF9F),
                background = Color(0xFF0D0221),
                onBackground = Color(0xFF00FF9F),
                surface = Color(0xFF1A0A3A),
                onSurface = Color(0xFF00FF9F),
                surfaceVariant = Color(0xFF2A0E4E),
                onSurfaceVariant = Color(0xFF00FF9F),
                outline = Color(0xFFFF003C)
            )
        }
        "dark" -> DarkColorScheme
        "light" -> LightColorScheme
        else -> {
            if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (darkTheme) DarkColorScheme else LightColorScheme
            }
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.setStatusBarColor(colorScheme.primary.toArgb())
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
