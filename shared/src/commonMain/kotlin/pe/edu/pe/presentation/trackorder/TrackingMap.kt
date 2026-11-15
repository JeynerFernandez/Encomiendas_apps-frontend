package pe.edu.pe.presentation.trackorder

import androidx.compose.runtime.Composable


@Composable
expect fun TrackingMap(
    latitud: Double,
    longitud: Double,
    tieneUbicacion: Boolean
)