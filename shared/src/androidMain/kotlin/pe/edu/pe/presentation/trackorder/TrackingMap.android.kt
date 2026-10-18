package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay

private val coordenadasSedes: Map<String, LatLng> = mapOf(
    "Tarapoto" to LatLng(-6.4854, -76.3654),
    "Moyobamba" to LatLng(-6.0340, -76.9716),
    "Rioja" to LatLng(-6.0620, -77.1667),
    "Nueva Cajamarca" to LatLng(-5.9389, -77.3056),
    "Pomacochas" to LatLng(-5.7083, -77.8167),
    "Pedro Ruiz" to LatLng(-5.9500, -78.0333),
    "Bagua Grande" to LatLng(-5.7500, -78.4500),
    "Lima" to LatLng(-12.0464, -77.0428),
    "Arequipa" to LatLng(-16.4090, -71.5375)
)

@Composable
actual fun TrackingMap(
    origen: String,
    destino: String
) {

    val origenLatLng =
        coordenadasSedes[origen] ?: LatLng(-6.4854, -76.3654)

    val destinoLatLng =
        coordenadasSedes[destino] ?: LatLng(-12.0464, -77.0428)

    val centroMapa = LatLng(
        (origenLatLng.latitude + destinoLatLng.latitude) / 2,
        (origenLatLng.longitude + destinoLatLng.longitude) / 2
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            centroMapa,
            6f
        )
    }

    // Ruta simulada del conductor
    val rutaSimulada = remember {
        listOf(
            LatLng(-6.4854, -76.3654),
            LatLng(-6.4500, -76.4200),
            LatLng(-6.3800, -76.5000),
            LatLng(-6.2500, -76.6000),
            LatLng(-6.1000, -76.7500),
            LatLng(-5.9500, -76.9000),
            LatLng(-5.8000, -77.0000)
        )
    }

    val conductorMarkerState = rememberMarkerState(
        position = rutaSimulada.first()
    )

    LaunchedEffect(Unit) {
        while (true) {

            rutaSimulada.forEach { posicion ->

                conductorMarkerState.position = posicion

                delay(2000)
            }
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        cameraPositionState = cameraPositionState
    ) {

        Marker(
            state = rememberMarkerState(
                position = origenLatLng
            ),
            title = "Origen",
            snippet = origen
        )

        Marker(
            state = rememberMarkerState(
                position = destinoLatLng
            ),
            title = "Destino",
            snippet = destino
        )

        // Movilidad
        Marker(
            state = conductorMarkerState,
            title = "Movilidad",
            snippet = "Pedido en tránsito"
        )

        // Ruta simulada
        Polyline(
            points = rutaSimulada,
            color = Color(0xFF0F2A46),
            width = 10f
        )
    }
}