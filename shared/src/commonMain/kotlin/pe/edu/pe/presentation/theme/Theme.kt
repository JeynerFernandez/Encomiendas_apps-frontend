package pe.edu.pe.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0F2A46),        // Azul marino (Navy)
    onPrimary = Color.White,
    secondary = Color(0xFFE8672B),      // Naranja
    onSecondary = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF1A1A1A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = Color(0xFF6B7280),
    outlineVariant = Color(0xFFE3E7ED),
    error = Color(0xFFD64545),
    onError = Color.White
)


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),        // Azul claro visible en fondo oscuro
    onPrimary = Color(0xFF0F172A),
    secondary = Color(0xFFFB923C),
    onSecondary = Color(0xFF0F172A),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFF94A3B8),
    outlineVariant = Color(0xFF334155),
    error = Color(0xFFEF5350),
    onError = Color.White
)


@Composable
fun MandaYaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontSizeScale: Float = 1.0f,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val base = Typography()

    val scaledTypography = Typography(
        displayLarge = base.displayLarge.copy(fontSize = base.displayLarge.fontSize * fontSizeScale),
        displayMedium = base.displayMedium.copy(fontSize = base.displayMedium.fontSize * fontSizeScale),
        displaySmall = base.displaySmall.copy(fontSize = base.displaySmall.fontSize * fontSizeScale),
        headlineLarge = base.headlineLarge.copy(fontSize = base.headlineLarge.fontSize * fontSizeScale),
        headlineMedium = base.headlineMedium.copy(fontSize = base.headlineMedium.fontSize * fontSizeScale),
        headlineSmall = base.headlineSmall.copy(fontSize = base.headlineSmall.fontSize * fontSizeScale),
        titleLarge = base.titleLarge.copy(fontSize = base.titleLarge.fontSize * fontSizeScale),
        titleMedium = base.titleMedium.copy(fontSize = base.titleMedium.fontSize * fontSizeScale),
        titleSmall = base.titleSmall.copy(fontSize = base.titleSmall.fontSize * fontSizeScale),
        bodyLarge = base.bodyLarge.copy(fontSize = base.bodyLarge.fontSize * fontSizeScale),
        bodyMedium = base.bodyMedium.copy(fontSize = base.bodyMedium.fontSize * fontSizeScale),
        bodySmall = base.bodySmall.copy(fontSize = base.bodySmall.fontSize * fontSizeScale),
        labelLarge = base.labelLarge.copy(fontSize = base.labelLarge.fontSize * fontSizeScale),
        labelMedium = base.labelMedium.copy(fontSize = base.labelMedium.fontSize * fontSizeScale),
        labelSmall = base.labelSmall.copy(fontSize = base.labelSmall.fontSize * fontSizeScale)
    )

    CompositionLocalProvider(LocalFontSizeScale provides fontSizeScale) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = scaledTypography,
            content = content
        )
    }
}