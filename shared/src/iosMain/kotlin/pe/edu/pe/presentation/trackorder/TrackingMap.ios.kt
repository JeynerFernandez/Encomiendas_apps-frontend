package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun TrackingMap(
    origen: String,
    destino: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    )
}