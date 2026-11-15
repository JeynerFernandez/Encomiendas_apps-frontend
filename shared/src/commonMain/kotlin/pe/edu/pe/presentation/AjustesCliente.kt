package pe.edu.pe.presentation.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.pe.presentation.theme.*

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    fontSizeScale: Float,
    onFontSizeScaleChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Navy,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            ScaledText(
                text = "Ajustes",
                fontSize = 25f,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Border)
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- TEMA ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Border),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ScaledText(
                        text = "TEMA",
                        fontSize = 14f,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Modo Claro
                    ThemeOptionRow(
                        icon = Icons.Default.LightMode,
                        label = "Modo Claro",
                        selected = !isDarkMode,
                        onClick = { onDarkModeChange(false) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Modo Oscuro
                    ThemeOptionRow(
                        icon = Icons.Default.DarkMode,
                        label = "Modo Oscuro",
                        selected = isDarkMode,
                        onClick = { onDarkModeChange(true) }
                    )
                }
            }

            // --- TAMAÑO DE TEXTO ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Border),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(NavySoft, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TextFields,
                                contentDescription = null,
                                tint = Navy,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        ScaledText(
                            text = "TAMAÑO DE TEXTO",
                            fontSize = 14f,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FontSizeOptionRow(
                        label = "Pequeño",
                        selected = fontSizeScale == 0.85f,
                        onClick = { onFontSizeScaleChange(0.85f) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FontSizeOptionRow(
                        label = "Normal",
                        selected = fontSizeScale == 1.0f,
                        onClick = { onFontSizeScaleChange(1.0f) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FontSizeOptionRow(
                        label = "Grande",
                        selected = fontSizeScale == 1.15f,
                        onClick = { onFontSizeScaleChange(1.15f) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FontSizeOptionRow(
                        label = "Muy grande",
                        selected = fontSizeScale == 1.3f,
                        onClick = { onFontSizeScaleChange(1.3f) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ThemeOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (selected) NavySoft else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Navy else Muted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        ScaledText(
            text = label,
            fontSize = 14f,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Navy,
                unselectedColor = Muted
            )
        )
    }
}

@Composable
private fun FontSizeOptionRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (selected) NavySoft else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScaledText(
            text = label,
            fontSize = 14f,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Navy,
                unselectedColor = Muted
            )
        )
    }
}