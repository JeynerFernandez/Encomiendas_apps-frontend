package pe.edu.pe.presentation.reception.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.data.model.Shipment
import pe.edu.pe.data.model.ShipmentStatus
import pe.edu.pe.presentation.theme.*
import kotlin.random.Random

@Composable
fun DeliverWarehouseScreen(
    shipments: List<Shipment>,
    onShipmentDelivered: (Shipment) -> Unit,
    onWarehouse: () -> Unit,
    onBack: () -> Unit
) {

    var dni by remember {
        mutableStateOf("")
    }

    var searchMessage by remember {
        mutableStateOf("")
    }

    var foundShipment by remember {
        mutableStateOf<Shipment?>(null)
    }

    var password by remember {
        mutableStateOf("")
    }

    var warehouseToken by remember {
        mutableStateOf("")
    }

    var passwordMessage by remember {
        mutableStateOf("")
    }

    var deliveryMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                top = 16.dp,
                end = 20.dp,
                bottom = 20.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Ink
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Entregar a almacén",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Text(
                    text = "Busca una encomienda registrada",
                    fontSize = 14.sp,
                    color = Muted
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "DNI",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = dni,

            onValueChange = { value ->

                if (
                    value.length <= 8 &&
                    value.all { it.isDigit() }
                ) {
                    dni = value
                    searchMessage = ""
                    foundShipment = null
                    password = ""
                    passwordMessage = ""
                    warehouseToken = ""
                    deliveryMessage = ""
                }
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Ingrese DNI")
            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedBorderColor = Border,
                unfocusedBorderColor = Border
            )
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {

                /*
                 * Solo buscamos encomiendas
                 * que todavía estén REGISTRADAS.
                 */
                val shipment = shipments.find {

                    (
                            it.senderDni == dni ||
                                    it.recipientDni == dni
                            ) &&
                            it.status == ShipmentStatus.REGISTRADO
                }

                if (shipment != null) {

                    foundShipment = shipment

                    searchMessage = ""

                    password = ""

                    passwordMessage = ""

                    warehouseToken = ""

                    deliveryMessage = ""

                } else {

                    foundShipment = null

                    searchMessage =
                        "No se encontró una encomienda registrada asociada a este DNI."
                }
            },

            enabled = dni.length == 8,

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Surface,
                disabledContainerColor = Border,
                disabledContentColor = Muted
            )
        ) {

            Text(
                text = "Buscar encomienda",
                fontWeight = FontWeight.Bold
            )
        }

        if (searchMessage.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = searchMessage,
                fontSize = 14.sp,
                color = Danger
            )
        }

        foundShipment?.let { shipment ->

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "✓ Encomienda encontrada",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Success
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Código de seguimiento",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = shipment.trackingCode,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
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
                text =
                    "${shipment.originBranch} → ${shipment.destinationBranch}",
                fontSize = 15.sp,
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
                text = "${shipment.weight} kg",
                fontSize = 15.sp,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Estado actual",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = shipment.status.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Contraseña",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = password,

                onValueChange = {

                    if (it.length <= 4) {

                        password =
                            it.uppercase()

                        passwordMessage = ""

                        warehouseToken = ""

                        deliveryMessage = ""
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("Ingrese contraseña")
                },

                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {

                    if (
                        password.equals(
                            shipment.password,
                            ignoreCase = true
                        )
                    ) {

                        passwordMessage =
                            "✓ Contraseña correcta"

                    } else {

                        passwordMessage =
                            "Contraseña incorrecta"
                    }
                },

                enabled = password.length == 4,

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Surface,
                    disabledContainerColor = Border,
                    disabledContentColor = Muted
                )
            ) {

                Text(
                    text = "Validar contraseña",
                    fontWeight = FontWeight.Bold
                )
            }

            if (passwordMessage.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = passwordMessage,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,

                    color =
                        if (
                            passwordMessage.startsWith("✓")
                        ) {
                            Success
                        } else {
                            Danger
                        }
                )
            }

            if (
                passwordMessage ==
                "✓ Contraseña correcta"
            ) {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                 * PASO 1
                 *
                 * Generamos un token.
                 */
                if (warehouseToken.isEmpty()) {

                    Button(
                        onClick = {

                            warehouseToken =
                                "ALM-${
                                    Random.nextInt(
                                        10000,
                                        100000
                                    )
                                }"
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Orange,
                                contentColor =
                                    Surface
                            )
                    ) {

                        Text(
                            text =
                                "Generar token para almacén",
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                /*
                 * PASO 2
                 *
                 * Mostramos el token y
                 * confirmamos la entrega.
                 */
                if (
                    warehouseToken.isNotEmpty()
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Text(
                        text = "Token de almacén",
                        fontSize = 13.sp,
                        color = Muted
                    )

                    Text(
                        text = warehouseToken,
                        fontSize = 22.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color = Navy
                    )

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Button(
                        onClick = {

                            /*
                             * AQUÍ ESTÁ EL CAMBIO
                             * IMPORTANTE.
                             *
                             * La encomienda ahora
                             * pasa directamente a:
                             *
                             * ALMACEN
                             */
                            val updatedShipment =
                                shipment.copy(

                                    status =
                                        ShipmentStatus.ALMACEN,

                                    warehouseToken =
                                        warehouseToken,

                                    /*
                                     * Todavía no tiene
                                     * vehículo/conductor.
                                     */
                                    vehiclePlate = null,

                                    driver = null
                                )

                            foundShipment =
                                updatedShipment

                            /*
                             * App.kt actualizará el
                             * ShipmentRepository.
                             */
                            onShipmentDelivered(
                                updatedShipment
                            )

                            deliveryMessage =
                                "✓ Encomienda entregada correctamente al almacén."
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Success,
                                contentColor =
                                    Surface
                            )
                    ) {

                        Text(
                            text =
                                "Confirmar entrega a almacén",
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                if (
                    deliveryMessage.isNotEmpty()
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Text(
                        text =
                            deliveryMessage,
                        fontSize = 14.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color = Success
                    )

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Button(
                        onClick =
                            onWarehouse,

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Navy,
                                contentColor =
                                    Surface
                            )
                    ) {

                        Text(
                            text =
                                "Ir a almacén",
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onBack,

            modifier = Modifier.fillMaxWidth(),

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