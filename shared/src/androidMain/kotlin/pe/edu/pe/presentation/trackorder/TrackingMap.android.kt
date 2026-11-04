package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
actual fun TrackingMap(
    latitud: Double,
    longitud: Double,
    tieneUbicacion: Boolean
) {

    // =====================================================
    // NO EXISTE UBICACIÓN REGISTRADA
    // =====================================================

    if (
        !tieneUbicacion
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
                    "La movilidad todavía no registra una ubicación",

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }

        return
    }


    // =====================================================
    // UBICACIÓN REAL DEVUELTA POR EL BACKEND
    // =====================================================

    val posicionMovilidad =
        LatLng(
            latitud,
            longitud
        )


    // =====================================================
    // CÁMARA
    // =====================================================

    val cameraPositionState =
        rememberCameraPositionState {

            position =
                CameraPosition
                    .fromLatLngZoom(
                        posicionMovilidad,
                        15f
                    )
        }


    // =====================================================
    // MARCADOR
    // =====================================================

    val markerState =
        rememberMarkerState(
            position =
                posicionMovilidad
        )


    // =====================================================
    // ACTUALIZAR POSICIÓN CUANDO CAMBIE BACKEND
    // =====================================================

    LaunchedEffect(
        latitud,
        longitud
    ) {

        val nuevaPosicion =
            LatLng(
                latitud,
                longitud
            )


        markerState.position =
            nuevaPosicion


        cameraPositionState
            .animate(

                CameraUpdateFactory
                    .newLatLngZoom(
                        nuevaPosicion,
                        15f
                    )
            )
    }


    // =====================================================
    // GOOGLE MAPS
    // =====================================================

    GoogleMap(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(
                    280.dp
                ),

        cameraPositionState =
            cameraPositionState
    ) {

        Marker(
            state =
                markerState,

            title =
                "Movilidad",

            snippet =
                "Última ubicación registrada"
        )
    }
}