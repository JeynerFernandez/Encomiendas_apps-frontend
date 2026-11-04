package pe.edu.pe.presentation.reception.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.*


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

    paymentMethod: String,

    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Background
            )
            .statusBarsPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                start = 20.dp,
                top = 16.dp,
                end = 20.dp,
                bottom = 20.dp
            ),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {


        // Cabecera
        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick =
                    onBack
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored
                            .Outlined
                            .ArrowBack,

                    contentDescription =
                        "Regresar",

                    tint =
                        Ink
                )
            }


            Text(
                text =
                    "Confirmación",

                fontSize =
                    18.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    20.dp
                )
        )


        Icon(
            imageVector =
                Icons.Outlined.CheckCircle,

            contentDescription =
                null,

            tint =
                Success,

            modifier =
                Modifier.size(
                    58.dp
                )
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        Text(
            text =
                "Encomienda registrada",

            fontSize =
                22.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Text(
            text =
                "Entrega el código y PIN al cliente",

            fontSize =
                13.sp,

            color =
                Muted
        )


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // Código y PIN
        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    16.dp
                ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Surface
                    )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        20.dp
                    )
            ) {

                Text(
                    text =
                        "Código de seguimiento",

                    fontSize =
                        13.sp,

                    color =
                        Muted
                )


                Text(
                    text =
                        trackingCode,

                    fontSize =
                        26.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Navy
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )


                Text(
                    text =
                        "PIN de seguridad",

                    fontSize =
                        13.sp,

                    color =
                        Muted
                )


                Text(
                    text =
                        shipmentPassword,

                    fontSize =
                        24.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    16.dp
                )
        )


        // Datos de la encomienda
        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    16.dp
                ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Surface
                    )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        20.dp
                    )
            ) {

                Text(
                    text =
                        "Datos del envío",

                    fontSize =
                        17.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            16.dp
                        )
                )


                ConfirmationData(
                    label =
                        "DNI del remitente",

                    value =
                        senderDni
                )


                ConfirmationData(
                    label =
                        "DNI del destinatario",

                    value =
                        recipientDni
                )


                ConfirmationData(
                    label =
                        "Sede de origen",

                    value =
                        originBranch
                )


                ConfirmationData(
                    label =
                        "Sede de destino",

                    value =
                        destinationBranch
                )


                ConfirmationData(
                    label =
                        "Ruta",

                    value =
                        "$originBranch → $destinationBranch"
                )


                ConfirmationData(
                    label =
                        "Peso",

                    value =
                        "$weight kg"
                )


                ConfirmationData(
                    label =
                        "Dimensiones",

                    value =
                        "$height × $width × $length cm"
                )


                ConfirmationData(
                    label =
                        "Peso usado para tarifa",

                    value =
                        "$billableWeight kg"
                )


                ConfirmationData(
                    label =
                        "Modalidad de pago",

                    value =
                        paymentMethod
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )


                HorizontalDivider(
                    color =
                        Border
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            16.dp
                        )
                )


                Text(
                    text =
                        "Costo total",

                    fontSize =
                        13.sp,

                    color =
                        Muted
                )


                Text(
                    text =
                        "S/ $shippingCost",

                    fontSize =
                        25.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Navy
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        Button(
            onClick =
                onBack,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        52.dp
                    ),

            shape =
                RoundedCornerShape(
                    10.dp
                ),

            colors =
                ButtonDefaults
                    .buttonColors(

                        containerColor =
                            Navy,

                        contentColor =
                            Surface
                    )
        ) {

            Text(
                text =
                    "Volver a recepción",

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}


@Composable
private fun ConfirmationData(
    label: String,
    value: String
) {

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 12.dp
                )
    ) {

        Text(
            text =
                label,

            fontSize =
                12.sp,

            color =
                Muted
        )


        Text(
            text =
                value,

            fontSize =
                15.sp,

            fontWeight =
                FontWeight.Medium,

            color =
                Ink
        )
    }
}