package pe.edu.pe.presentation.reception.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import pe.edu.pe.presentation.theme.Background
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.Success
import pe.edu.pe.presentation.theme.Surface

@Composable
fun ShipmentConfirmationScreen(
    trackingCode: String,
    shipmentPassword: String,
    senderDni: String,
    recipientDni: String,
    originBranch: String,
    destinationBranch: String,
    weight: String,
    height: String,
    width: String,
    length: String,
    billableWeight: String,
    shippingCost: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()              // margen para la barra de estado
            .padding(
                start = 20.dp,
                top = 16.dp,                  // margen de la cabecera
                end = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Cabecera con flecha de retroceso
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Ink
                )
            }

            Text(
                text = "Confirmación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = Success,
            modifier = Modifier.size(48.dp)
        )

        // ... el resto (textos, tarjeta y botón "Volver a recepción") queda igual
        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Encomienda registrada",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Text(
            text = "Entrega estos datos al cliente",
            fontSize = 13.sp,
            color = Muted
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Surface,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Código de seguimiento",
                fontSize = 13.sp,
                color = Muted
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = trackingCode,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Contraseña",
                fontSize = 13.sp,
                color = Muted
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = shipmentPassword,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Datos del envío",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "DNI del remitente",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = senderDni,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "DNI del destinatario",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = recipientDni,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Ruta",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "$originBranch → $destinationBranch",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Peso",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "$weight kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Dimensiones",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "$height × $width × $length cm",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Peso facturable",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "$billableWeight kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Costo",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "S/ $shippingCost",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Navy,
                contentColor = Surface
            )
        ) {
            Text(
                text = "Volver a recepción",
                fontWeight = FontWeight.Bold
            )
        }
    }
}