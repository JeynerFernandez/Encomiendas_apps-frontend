package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
actual fun TrackingMap(
    latitud: Double,
    longitud: Double,
    tieneUbicacion: Boolean
) {

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(
                    280.dp
                ),

        contentAlignment =
            Alignment.Center
    ) {

        Text(
            text =
                "Mapa disponible en Android",

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant,

            textAlign =
                TextAlign.Center
        )
    }
}