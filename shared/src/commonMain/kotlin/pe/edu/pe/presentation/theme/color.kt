package pe.edu.pe.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores fijos
val Navy2 = Color(0xFF1B3F63)
val Orange = Color(0xFFE8672B)
val OrangeDark = Color(0xFFC4501B)
val OrangeSoft = Color(0xFFFCE7DB)
val Success = Color(0xFF2E9E5C)
val Danger = Color(0xFFD64545)
val ErrorRed = Color(0xFFD32F2F)
val Muted2 = Color(0xFF8A94A2)
val TealPrimary = Color(0xFF00C)
val PurpleSecondary = Color(0xFF6A1B9A)


// --- Colores dinámicos ---

val Navy: Color
    @Composable get() = MaterialTheme.colorScheme.primary

val NavySoft: Color
    @Composable get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)

val LightBackground: Color
    @Composable get() = MaterialTheme.colorScheme.background

val Surface: Color
    @Composable get() = MaterialTheme.colorScheme.surface

val SurfaceWhite: Color
    @Composable get() = MaterialTheme.colorScheme.surface

val Ink: Color
    @Composable get() = MaterialTheme.colorScheme.onSurface

val TextPrimary: Color
    @Composable get() = MaterialTheme.colorScheme.onSurface

val Muted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

val TextSecondary: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

val Border: Color
    @Composable get() = MaterialTheme.colorScheme.outlineVariant

val InputBorder: Color
    @Composable get() = MaterialTheme.colorScheme.outlineVariant

val Background: Color
    @Composable get() = MaterialTheme.colorScheme.background