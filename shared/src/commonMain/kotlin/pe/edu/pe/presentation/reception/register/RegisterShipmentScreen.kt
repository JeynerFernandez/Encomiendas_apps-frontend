package pe.edu.pe.presentation.reception.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.Background
import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.Orange
import pe.edu.pe.presentation.theme.Success
import pe.edu.pe.presentation.theme.Surface
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterShipmentScreen(
    onBack: () -> Unit,
    onShipmentGenerated: (
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String,
        String
    ) -> Unit
){
    var senderDni by remember { mutableStateOf("") }
    var recipientDni by remember { mutableStateOf("")}

    var senderValidated by remember { mutableStateOf(false) }
    var recipientValidated by remember { mutableStateOf(false)}

    var paymentMethod by remember { mutableStateOf("") }

    var height by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var originBranch by remember { mutableStateOf("") }
    var destinationBranch by remember { mutableStateOf("") }

    var originExpanded by remember { mutableStateOf(false) }
    var destinationExpanded by remember { mutableStateOf(false) }

    val branches = listOf(
        "Tarapoto",
        "Moyobamba",
        "Juanjuí",
        "Tocache"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                top = 16.dp,      // antes 32.dp; statusBarsPadding ya aporta el espacio superior
                end = 20.dp,
                bottom = 20.dp
            )
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = onBack
            ){
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Ink
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = "Registrar envío",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Text(
                    text = "Registrar una nueva encomienda",
                    fontSize = 13.sp,
                    color = Muted
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Datos del remitente",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        //Aqui agregamos el campo DNI del remitente

        OutlinedTextField(
            value = senderDni,
            onValueChange = {
                if (it.length <= 8 && it.all { char -> char.isDigit() }){
                    senderDni = it
                    senderValidated = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("DNI del remitente")
            },
            placeholder = {
                Text("Ingrese DNI")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedBorderColor = Border,
                unfocusedBorderColor = Border
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (senderValidated){
            Text(
                text = "✓ DNI válido",
                fontSize = 13.sp,
                color = Success
            )
        }

        Button(
            onClick = {
                senderValidated = senderDni.length == 8
            },
            enabled = senderDni.length == 8,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Validar DNI")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Datos del destinario",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
        Spacer(modifier = Modifier.height(12.dp)
        )

        //Agregamos el campo del DNi del destinatario

        OutlinedTextField(
            value = recipientDni,
            onValueChange = {
                if (it.length <= 8 && it.all { char -> char.isDigit() }){
                    recipientDni = it
                    recipientValidated = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("DNI del Destinatario")
            },
            placeholder = {
                Text("Ingrese DNI")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedBorderColor = Border,
                unfocusedBorderColor = Border
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (recipientValidated) {
            Text(
                text = "✓ DNI válido",
                fontSize = 13.sp,
                color = Success
            )
        }

        Button(
            onClick = {
                recipientValidated = recipientDni.length ==8
            },
            enabled = recipientDni.length == 8,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Validar DNI")
        }

        //Modalidad de Pago

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Modalidad de pago",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            if (paymentMethod == "Pagar ahora"){
                Button(
                    onClick = {
                        paymentMethod = "Pagar ahora"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = Surface
                    )
                ){
                    Text("✓ Pagar ahora")
                }
            }else{
                OutlinedButton(
                    onClick = {
                        paymentMethod = "Pagar ahora"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp,Border),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Surface,
                        contentColor = Ink
                    )
                ){
                    Text("Pagar ahora")
                }
            }

            if (paymentMethod == "Contra entrega"){
                Button(
                    onClick = {
                        paymentMethod = "Contra entrega"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = Surface
                    )
                ){
                    Text("✓ Contra entrega")
                }

            }else{
                OutlinedButton(
                    onClick = {
                        paymentMethod = "Contra entrega"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Surface,
                        contentColor = Ink
                    )
                ){
                    Text("Contra entrega")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Encomienda",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            OutlinedTextField(
                value = height,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }){
                        height = it
                    }
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Alto")
                },
                placeholder = {
                    Text("30")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )
            OutlinedTextField(
                value = width,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }){
                        width = it
                    }
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Ancho")
                },
                placeholder = {
                    Text("20")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )

            OutlinedTextField(
                value = length,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }){
                        length = it
                    }
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Largo")
                },
                placeholder = {
                    Text("40")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = {
                if (it.all { char -> char.isDigit() || char == '.' }){
                    weight = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Peso (kg)")
            },
            placeholder = {
                Text("5")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedBorderColor = Border,
                unfocusedBorderColor = Border
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sede de Origen",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = originExpanded,
            onExpandedChange = {
                originExpanded = !originExpanded
            }
        ){
            OutlinedTextField(
                value = originBranch,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                label = {
                    Text("Sede de origen")
                },
                placeholder = {
                    Text("Selecciona una sede")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = originExpanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )
            ExposedDropdownMenu(
                expanded = originExpanded,
                onDismissRequest = {
                    originExpanded = false
                }
            ){
                branches.forEach {branch -> DropdownMenuItem(
                    text = {
                        Text(text = branch)
                    },
                    onClick = {
                        originBranch = branch
                        originExpanded = false
                    }
                )

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = destinationExpanded,
            onExpandedChange = {
                destinationExpanded = !destinationExpanded
            }
        ){
            OutlinedTextField(
                value = destinationBranch,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                label = {
                    Text("Sede de destino")
                },
                placeholder = {
                    Text("Seleccione una sede")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = destinationExpanded
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface,
                    focusedBorderColor = Border,
                    unfocusedBorderColor = Border
                )
            )

            ExposedDropdownMenu(
                expanded = destinationExpanded,
                onDismissRequest = {
                    destinationExpanded = false
                }
            ){
                branches.forEach { branch -> DropdownMenuItem(
                    text = {
                        Text(text = branch)
                    },
                    onClick = {
                        destinationBranch = branch
                        destinationExpanded = false
                    }
                )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                //Se Registrara la encomienda
                val code = "RN-${Random.nextInt(10000, 100000)}"
                val password = (1..4)
                    .map { ('A'..'Z').random() }
                    .joinToString("")

                val realWeight = weight.toDoubleOrNull() ?: 0.0
                val packageHeight = height.toDoubleOrNull() ?: 0.0
                val packageWidth = width.toDoubleOrNull() ?: 0.0
                val packageLength = length.toDoubleOrNull() ?: 0.0

                val volumetricWeight =
                    (packageHeight * packageWidth * packageLength) / 5000.0

                val billable = maxOf(realWeight, volumetricWeight)

                val cost = 12.0 + (billable * 4.8)

                onShipmentGenerated(
                    code,
                    password,
                    senderDni,
                    recipientDni,
                    originBranch,
                    destinationBranch,
                    weight,
                    height,
                    width,
                    length,
                    billable.toString(),
                    cost.toString(),
                    paymentMethod
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Surface
            )
        ){
            Text(
                text = "Generar codigo y continuar",
                fontWeight = FontWeight.Bold
            )
        }


    }
}