package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.pe.di.appModule
import pe.edu.pe.presentation.theme.*
import pe.edu.pe.presentation.components.InlineMessage
import pe.edu.pe.presentation.components.InlineMessageModel
import pe.edu.pe.presentation.components.InlineMessageType
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.pe.presentation.viewmodel.SedeState
import pe.edu.pe.presentation.viewmodel.SedeViewModel
import pe.edu.pe.presentation.viewmodel.TarifaState
import pe.edu.pe.presentation.viewmodel.TarifaViewModel

@Preview
@Composable
fun CotizacionPreview() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        MandaYaTheme {
            CalculadoraCostoScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraCostoScreen(
    onSalirClick: () -> Unit = {},
    sedeViewModel: SedeViewModel = koinViewModel(),
    tarifaViewModel: TarifaViewModel = koinViewModel()
) {

    var largo by remember { mutableStateOf("") }
    var ancho by remember { mutableStateOf("") }
    var alto by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var origenSeleccionado by remember { mutableStateOf("") }
    var destinoSeleccionado by remember { mutableStateOf("") }
    var origenIdSeleccionado by remember { mutableStateOf<Int?>(null) }
    var destinoIdSeleccionado by remember { mutableStateOf<Int?>(null) }
    var tipoEnvio by remember { mutableStateOf("Paquete") }

    var origenExpandido by remember { mutableStateOf(false) }
    var destinoExpandido by remember { mutableStateOf(false) }

    var mostrarResultado by remember { mutableStateOf(false) }
    var costoCalculado by remember { mutableStateOf(0.0) }
    var mensajeError by remember { mutableStateOf("") }
    var isErrorMessageVisible by remember { mutableStateOf(true) }

    val sedeState by sedeViewModel.state.collectAsState()
    val tarifaState by tarifaViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        sedeViewModel.getSedes()
        tarifaViewModel.getTarifas()
    }

    LaunchedEffect(mensajeError) {
        isErrorMessageVisible = true
    }


    val sedesDisponibles = when (val state = sedeState) {
        is SedeState.Success -> state.sedes.filter { it.active }
        else -> emptyList()
    }


    fun calcularCosto(): Double? {
        val pesoNum = peso.toDoubleOrNull() ?: 0.0
        val tarifa = when (val state = tarifaState) {
            is TarifaState.Success -> state.tarifas.firstOrNull {
                it.active &&
                    it.sedeOrigenId == origenIdSeleccionado &&
                    it.sedeDestinoId == destinoIdSeleccionado
            }
            else -> null
        }

        if (tarifa == null) return null

        val pesoIncluido = 20.0
        return tarifa.precioBase +
            if (pesoNum > pesoIncluido) {
                (pesoNum - pesoIncluido) * tarifa.precioKgExcedente
            } else {
                0.0
            }
    }


    fun Double.formatearPrecio(): String {
        val entero = this.toInt()
        val decimales = ((this - entero) * 100).roundToInt()
        return "$entero.${if (decimales < 10) "0$decimales" else "$decimales"}"
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onSalirClick,
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

            Column {
                ScaledText(
                    text = "Administrador",
                    fontSize = 12f,
                    color = Muted
                )
                ScaledText(
                    text = "Calcular costo",
                    fontSize = 25f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        HorizontalDivider(color = Border)
        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Border),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Straighten, contentDescription = null, tint = Orange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    ScaledText("MEDIDAS", fontSize = 14f, fontWeight = FontWeight.Bold, color = Navy)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = largo,
                        onValueChange = { largo = it },
                        modifier = Modifier.weight(1f),
                        label = { ScaledText("Largo (cm)", fontSize = 11f) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )
                    OutlinedTextField(
                        value = ancho,
                        onValueChange = { ancho = it },
                        modifier = Modifier.weight(1f),
                        label = { ScaledText("Ancho (cm)", fontSize = 11f) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )
                    OutlinedTextField(
                        value = alto,
                        onValueChange = { alto = it },
                        modifier = Modifier.weight(1f),
                        label = { ScaledText("Alto (cm)", fontSize = 11f) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Border),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Scale, contentDescription = null, tint = Orange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    ScaledText("PESO", fontSize = 14f, fontWeight = FontWeight.Bold, color = Navy)
                }
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { ScaledText("Peso (kg)", fontSize = 14f) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Navy,
                        unfocusedBorderColor = Border,
                        focusedTextColor = Ink,
                        unfocusedTextColor = Ink
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Border),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Orange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    ScaledText("UBICACIÓN", fontSize = 14f, fontWeight = FontWeight.Bold, color = Navy)
                }
                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = origenExpandido,
                    onExpandedChange = { origenExpandido = !origenExpandido }
                ) {
                    OutlinedTextField(
                        value = origenSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        label = { ScaledText("Origen", fontSize = 14f) },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = Muted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = origenExpandido,
                        onDismissRequest = { origenExpandido = false }
                    ) {
                        sedesDisponibles.forEach { sede ->
                            DropdownMenuItem(
                                text = { ScaledText(sede.name, fontSize = 14f) },
                                onClick = {
                                    origenSeleccionado = sede.name
                                    origenIdSeleccionado = sede.id
                                    origenExpandido = false
                                    mensajeError = ""
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = destinoExpandido,
                    onExpandedChange = { destinoExpandido = !destinoExpandido }
                ) {
                    OutlinedTextField(
                        value = destinoSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        label = { ScaledText("Destino", fontSize = 14f) },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = Muted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = destinoExpandido,
                        onDismissRequest = { destinoExpandido = false }
                    ) {
                        sedesDisponibles.forEach { sede ->
                            DropdownMenuItem(
                                text = { ScaledText(sede.name, fontSize = 14f) },
                                onClick = {
                                    destinoSeleccionado = sede.name
                                    destinoIdSeleccionado = sede.id
                                    destinoExpandido = false
                                    mensajeError = ""
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Border),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Calculate, contentDescription = null, tint = Orange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    ScaledText("TIPO DE ENVÍO", fontSize = 14f, fontWeight = FontWeight.Bold, color = Navy)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { tipoEnvio = "Paquete" },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoEnvio == "Paquete") Navy else NavySoft,
                            contentColor = if (tipoEnvio == "Paquete") Color.White else Navy
                        )
                    ) {
                        ScaledText("📦 Paquete", fontSize = 13f)
                    }

                    Button(
                        onClick = { tipoEnvio = "Sobre" },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tipoEnvio == "Sobre") Navy else NavySoft,
                            contentColor = if (tipoEnvio == "Sobre") Color.White else Navy
                        )
                    ) {
                        ScaledText("✉️ Sobre", fontSize = 13f)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {
                if (peso.isBlank() || origenSeleccionado.isBlank() || destinoSeleccionado.isBlank()) {
                    mensajeError = "Completa peso, origen y destino"
                } else if (origenSeleccionado == destinoSeleccionado) {
                    mensajeError = "El origen y destino no pueden ser iguales"
                } else {
                    mensajeError = ""
                    val costo = calcularCosto()
                    if (costo == null) {
                        mensajeError = "No existe una tarifa activa para esta ruta"
                    } else {
                        costoCalculado = costo
                        mostrarResultado = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Navy,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Default.Calculate, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            ScaledText("CALCULAR COSTO", fontSize = 16f, fontWeight = FontWeight.Bold)
        }

        if (mensajeError.isNotBlank() && isErrorMessageVisible) {
            Spacer(modifier = Modifier.height(8.dp))
            InlineMessage(
                message = InlineMessageModel(
                    type = InlineMessageType.ERROR,
                    description = mensajeError,
                    dismissible = true,
                    onDismiss = {
                        isErrorMessageVisible = false
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }


    if (mostrarResultado) {
        AlertDialog(
            onDismissRequest = { mostrarResultado = false },
            title = {
                ScaledText(
                    "Costo de Envío",
                    fontWeight = FontWeight.Bold,
                    color = Ink,
                    fontSize = 18f
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScaledText(
                        text = "S/ ${costoCalculado.formatearPrecio()}",
                        fontSize = 32f,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ScaledText(
                        "Peso: $peso kg | Tipo: $tipoEnvio",
                        fontSize = 14f,
                        color = Muted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ScaledText(
                        "Ruta: $origenSeleccionado → $destinoSeleccionado",
                        fontSize = 14f,
                        color = Muted
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { mostrarResultado = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Navy)
                ) {
                    ScaledText("Aceptar", fontSize = 14f)
                }
            }
        )
    }
}
