package pe.edu.pe.presentation.reception.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.roundToLong
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.pe.presentation.theme.*
import pe.edu.pe.presentation.viewmodel.EncomiendaViewModel
import pe.edu.pe.presentation.viewmodel.PersonaViewModel
import pe.edu.pe.presentation.viewmodel.SedeState
import pe.edu.pe.presentation.viewmodel.SedeViewModel
import pe.edu.pe.presentation.viewmodel.TarifaState
import pe.edu.pe.presentation.viewmodel.TarifaViewModel


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun RegisterShipmentScreen(

    onBack: () -> Unit,

    // Sede asignada al usuario RECEPCION
    recepcionSedeId: Int?,

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
    ) -> Unit,

    personaViewModel:
    PersonaViewModel =
        koinViewModel(),

    sedeViewModel:
    SedeViewModel =
        koinViewModel(),

    tarifaViewModel:
    TarifaViewModel =
        koinViewModel(),

    encomiendaViewModel:
    EncomiendaViewModel =
        koinViewModel()
) {

    var state by remember {

        mutableStateOf(
            RegisterShipmentState()
        )
    }


    val sedeState by
    sedeViewModel.state
        .collectAsState()


    val tarifaState by
    tarifaViewModel.state
        .collectAsState()


    var destinationExpanded by remember {
        mutableStateOf(false)
    }


    // Carga sedes y tarifas
    LaunchedEffect(Unit) {

        sedeViewModel.getSedes()

        tarifaViewModel.getTarifas()
    }


    // Sedes activas
    val branches =
        when (
            val current =
                sedeState
        ) {

            is SedeState.Success -> {

                current.sedes
                    .filter {
                        it.active
                    }
            }

            else -> {

                emptyList()
            }
        }


    // Tarifas reales
    val tarifas =
        when (
            val current =
                tarifaState
        ) {

            is TarifaState.Success -> {

                current.tarifas
            }

            else -> {

                emptyList()
            }
        }


    // Asigna automáticamente la sede del recepcionista
    LaunchedEffect(
        recepcionSedeId,
        branches
    ) {

        val sedeRecepcion =
            branches.firstOrNull {

                it.id ==
                        recepcionSedeId
            }


        if (
            sedeRecepcion != null &&
            state.originBranchId !=
            sedeRecepcion.id
        ) {

            state =
                state.copy(

                    originBranchId =
                        sedeRecepcion.id,

                    originBranch =
                        sedeRecepcion.name,

                    calculatedCost =
                        null
                )
        }
    }


    // Calcula exactamente con la misma regla del backend
    fun calcularCosto() {

        val originId =
            state.originBranchId

        val destinationId =
            state.destinationBranchId

        val alto =
            state.height
                .toDoubleOrNull()

        val ancho =
            state.width
                .toDoubleOrNull()

        val largo =
            state.length
                .toDoubleOrNull()

        val peso =
            state.weight
                .toDoubleOrNull()


        if (
            originId == null
        ) {

            state =
                state.copy(
                    errorMessage =
                        "El usuario de recepción no tiene una sede asignada"
                )

            return
        }


        if (
            destinationId == null
        ) {

            state =
                state.copy(
                    errorMessage =
                        "Selecciona la sede de destino"
                )

            return
        }


        if (
            alto == null ||
            ancho == null ||
            largo == null ||
            peso == null
        ) {

            state =
                state.copy(
                    errorMessage =
                        "Completa correctamente las dimensiones y el peso"
                )

            return
        }


        if (
            alto <= 0 ||
            ancho <= 0 ||
            largo <= 0 ||
            peso <= 0
        ) {

            state =
                state.copy(
                    errorMessage =
                        "Las dimensiones y el peso deben ser mayores a 0"
                )

            return
        }


        val tarifa =
            tarifas.firstOrNull {

                it.sedeOrigenId ==
                        originId &&

                        it.sedeDestinoId ==
                        destinationId &&

                        it.active
            }


        if (
            tarifa == null
        ) {

            state =
                state.copy(

                    calculatedCost =
                        null,

                    tarifaBase =
                        null,

                    precioKgExcedente =
                        null,

                    errorMessage =
                        "No existe una tarifa activa para esta sede de origen y destino"
                )

            return
        }


        val pesoIncluido =
            20.0


        val costo =
            if (
                peso <=
                pesoIncluido
            ) {

                tarifa.precioBase

            } else {

                tarifa.precioBase +
                        (
                                peso -
                                        pesoIncluido
                                ) *
                        tarifa.precioKgExcedente
            }


        state =
            state.copy(

                calculatedCost =
                    costo,

                tarifaBase =
                    tarifa.precioBase,

                precioKgExcedente =
                    tarifa.precioKgExcedente,

                errorMessage =
                    null
            )
    }


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
            )
    ) {


        // CABECERA
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
                        Icons.Outlined.ArrowBack,

                    contentDescription =
                        "Regresar",

                    tint =
                        Ink
                )
            }


            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "Registrar envío",

                    fontSize =
                        24.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )


                Text(
                    text =
                        "Registrar una nueva encomienda",

                    fontSize =
                        13.sp,

                    color =
                        Muted
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // REMITENTE
        Text(
            text =
                "Datos del remitente",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        OutlinedTextField(
            value =
                state.senderDni,

            onValueChange = { value ->

                if (
                    value.length <= 8 &&
                    value.all {
                        it.isDigit()
                    }
                ) {

                    state =
                        state.copy(

                            senderDni =
                                value,

                            senderId =
                                null,

                            senderNames =
                                "",

                            senderLastNames =
                                "",

                            errorMessage =
                                null
                        )
                }
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {

                Text(
                    "DNI del remitente"
                )
            },

            placeholder = {

                Text(
                    "Ingrese DNI"
                )
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),

            singleLine =
                true
        )


        Spacer(
            modifier =
                Modifier.height(
                    8.dp
                )
        )


        Button(
            onClick = {

                state =
                    state.copy(
                        isSearchingSender =
                            true,

                        errorMessage =
                            null
                    )


                personaViewModel
                    .buscarPorDni(

                        dni =
                            state.senderDni,

                        onSuccess = {
                                persona ->

                            state =
                                state.copy(

                                    senderId =
                                        persona.id,

                                    senderNames =
                                        persona.nombres,

                                    senderLastNames =
                                        persona.apellidos,

                                    isSearchingSender =
                                        false
                                )
                        },

                        onError = {
                                error ->

                            state =
                                state.copy(

                                    senderId =
                                        null,

                                    senderNames =
                                        "",

                                    senderLastNames =
                                        "",

                                    isSearchingSender =
                                        false,

                                    errorMessage =
                                        error
                                )
                        }
                    )
            },

            enabled =
                state.senderDni.length ==
                        8 &&
                        !state.isSearchingSender,

            modifier =
                Modifier.fillMaxWidth(),

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Navy
                    )
        ) {

            if (
                state.isSearchingSender
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            20.dp
                        ),

                    strokeWidth =
                        2.dp,

                    color =
                        Surface
                )

            } else {

                Text(
                    "Validar DNI"
                )
            }
        }


        if (
            state.senderId != null
        ) {

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )


            PersonaCard(
                titulo =
                    "Remitente encontrado",

                dni =
                    state.senderDni,

                nombres =
                    state.senderNames,

                apellidos =
                    state.senderLastNames
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // DESTINATARIO
        Text(
            text =
                "Datos del destinatario",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        OutlinedTextField(
            value =
                state.recipientDni,

            onValueChange = { value ->

                if (
                    value.length <= 8 &&
                    value.all {
                        it.isDigit()
                    }
                ) {

                    state =
                        state.copy(

                            recipientDni =
                                value,

                            recipientId =
                                null,

                            recipientNames =
                                "",

                            recipientLastNames =
                                "",

                            errorMessage =
                                null
                        )
                }
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {

                Text(
                    "DNI del destinatario"
                )
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),

            singleLine =
                true
        )


        Spacer(
            modifier =
                Modifier.height(
                    8.dp
                )
        )


        Button(
            onClick = {

                state =
                    state.copy(
                        isSearchingRecipient =
                            true,

                        errorMessage =
                            null
                    )


                personaViewModel
                    .buscarPorDni(

                        dni =
                            state.recipientDni,

                        onSuccess = {
                                persona ->

                            state =
                                state.copy(

                                    recipientId =
                                        persona.id,

                                    recipientNames =
                                        persona.nombres,

                                    recipientLastNames =
                                        persona.apellidos,

                                    isSearchingRecipient =
                                        false
                                )
                        },

                        onError = {
                                error ->

                            state =
                                state.copy(

                                    recipientId =
                                        null,

                                    recipientNames =
                                        "",

                                    recipientLastNames =
                                        "",

                                    isSearchingRecipient =
                                        false,

                                    errorMessage =
                                        error
                                )
                        }
                    )
            },

            enabled =
                state.recipientDni.length ==
                        8 &&
                        !state.isSearchingRecipient,

            modifier =
                Modifier.fillMaxWidth(),

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Navy
                    )
        ) {

            if (
                state.isSearchingRecipient
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            20.dp
                        ),

                    strokeWidth =
                        2.dp,

                    color =
                        Surface
                )

            } else {

                Text(
                    "Validar DNI"
                )
            }
        }


        if (
            state.recipientId != null
        ) {

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )


            PersonaCard(
                titulo =
                    "Destinatario encontrado",

                dni =
                    state.recipientDni,

                nombres =
                    state.recipientNames,

                apellidos =
                    state.recipientLastNames
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // PAGO
        Text(
            text =
                "Modalidad de pago",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    10.dp
                )
        ) {

            PaymentOption(
                text =
                    "Pagar en origen",

                selected =
                    state.paymentMethod ==
                            "ORIGEN",

                modifier =
                    Modifier.weight(1f),

                onClick = {

                    state =
                        state.copy(
                            paymentMethod =
                                "ORIGEN"
                        )
                }
            )


            PaymentOption(
                text =
                    "Pagar en destino",

                selected =
                    state.paymentMethod ==
                            "DESTINO",

                modifier =
                    Modifier.weight(1f),

                onClick = {

                    state =
                        state.copy(
                            paymentMethod =
                                "DESTINO"
                        )
                }
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // DATOS DEL PAQUETE
        Text(
            text =
                "Datos de la encomienda",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    8.dp
                )
        ) {

            DecimalField(
                value =
                    state.height,

                label =
                    "Alto",

                modifier =
                    Modifier.weight(1f),

                onValueChange = {

                    state =
                        state.copy(
                            height = it,
                            calculatedCost = null
                        )
                }
            )


            DecimalField(
                value =
                    state.width,

                label =
                    "Ancho",

                modifier =
                    Modifier.weight(1f),

                onValueChange = {

                    state =
                        state.copy(
                            width = it,
                            calculatedCost = null
                        )
                }
            )


            DecimalField(
                value =
                    state.length,

                label =
                    "Largo",

                modifier =
                    Modifier.weight(1f),

                onValueChange = {

                    state =
                        state.copy(
                            length = it,
                            calculatedCost = null
                        )
                }
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        DecimalField(
            value =
                state.weight,

            label =
                "Peso (kg)",

            modifier =
                Modifier.fillMaxWidth(),

            onValueChange = {

                state =
                    state.copy(
                        weight = it,
                        calculatedCost = null
                    )
            }
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        OutlinedTextField(
            value =
                state.observation,

            onValueChange = {

                state =
                    state.copy(
                        observation =
                            it
                    )
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {

                Text(
                    "Observación"
                )
            },

            placeholder = {

                Text(
                    "Ejemplo: Producto frágil"
                )
            },

            maxLines =
                3
        )


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // SEDE ORIGEN FIJA
        Text(
            text =
                "Sede de origen",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        OutlinedTextField(
            value =
                if (
                    state.originBranch
                        .isBlank()
                ) {

                    "Cargando sede asignada..."

                } else {

                    state.originBranch
                },

            onValueChange = {},

            modifier =
                Modifier.fillMaxWidth(),

            label = {

                Text(
                    "Sede asignada al recepcionista"
                )
            },

            readOnly =
                true,

            enabled =
                false,

            singleLine =
                true
        )


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // SEDE DESTINO
        Text(
            text =
                "Sede de destino",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Ink
        )


        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )


        ExposedDropdownMenuBox(
            expanded =
                destinationExpanded,

            onExpandedChange = {

                destinationExpanded =
                    !destinationExpanded
            }
        ) {

            OutlinedTextField(
                value =
                    state.destinationBranch,

                onValueChange = {},

                readOnly =
                    true,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor(),

                label = {

                    Text(
                        "Sede de destino"
                    )
                },

                placeholder = {

                    Text(
                        "Selecciona una sede"
                    )
                },

                trailingIcon = {

                    ExposedDropdownMenuDefaults
                        .TrailingIcon(
                            expanded =
                                destinationExpanded
                        )
                }
            )


            ExposedDropdownMenu(
                expanded =
                    destinationExpanded,

                onDismissRequest = {

                    destinationExpanded =
                        false
                }
            ) {

                branches
                    .filter {

                        it.id !=
                                state.originBranchId
                    }
                    .forEach {
                            sede ->

                        DropdownMenuItem(
                            text = {

                                Column {

                                    Text(
                                        text =
                                            sede.name,

                                        fontWeight =
                                            FontWeight.Medium
                                    )


                                    Text(
                                        text =
                                            sede.city,

                                        fontSize =
                                            12.sp,

                                        color =
                                            Muted
                                    )
                                }
                            },

                            onClick = {

                                state =
                                    state.copy(

                                        destinationBranchId =
                                            sede.id,

                                        destinationBranch =
                                            sede.name,

                                        calculatedCost =
                                            null,

                                        tarifaBase =
                                            null,

                                        precioKgExcedente =
                                            null,

                                        errorMessage =
                                            null
                                    )


                                destinationExpanded =
                                    false
                            }
                        )
                    }
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        // CALCULAR ANTES DE REGISTRAR
        Button(
            onClick = {

                calcularCosto()
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        52.dp
                    ),

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Navy
                    ),

            shape =
                RoundedCornerShape(
                    10.dp
                )
        ) {

            Text(
                text =
                    "Calcular costo",

                fontWeight =
                    FontWeight.Bold
            )
        }


        // COTIZACION
        if (
            state.calculatedCost != null
        ) {

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        14.dp
                    ),

                border =
                    BorderStroke(
                        1.dp,
                        Success
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
                            16.dp
                        )
                ) {

                    Text(
                        text =
                            "Cotización calculada",

                        fontSize =
                            16.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Success
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                12.dp
                            )
                    )


                    Text(
                        text =
                            "${state.originBranch} → ${state.destinationBranch}",

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )


                    Text(
                        text =
                            "Peso: ${state.weight} kg",

                        color =
                            Ink
                    )


                    Text(
                        text =
                            "Peso incluido: 20 kg",

                        color =
                            Muted
                    )


                    Text(
                        text =
                            "Tarifa base: S/ ${
                                formatoPrecio(
                                    state.tarifaBase
                                        ?: 0.0
                                )
                            }",

                        color =
                            Muted
                    )


                    Text(
                        text =
                            "Kg excedente: S/ ${
                                formatoPrecio(
                                    state.precioKgExcedente
                                        ?: 0.0
                                )
                            }",

                        color =
                            Muted
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                12.dp
                            )
                    )


                    Text(
                        text =
                            "Total: S/ ${
                                formatoPrecio(
                                    state.calculatedCost
                                        ?: 0.0
                                )
                            }",

                        fontSize =
                            22.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Navy
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    16.dp
                )
        )


        if (
            !state.errorMessage
                .isNullOrBlank()
        ) {

            Text(
                text =
                    state.errorMessage!!,

                color =
                    MaterialTheme
                        .colorScheme
                        .error,

                fontSize =
                    13.sp
            )


            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )
        }


        // REGISTRAR
        Button(
            onClick = {

                val senderId =
                    state.senderId

                val recipientId =
                    state.recipientId

                val originId =
                    state.originBranchId

                val destinationId =
                    state.destinationBranchId


                val alto =
                    state.height
                        .toDoubleOrNull()

                val ancho =
                    state.width
                        .toDoubleOrNull()

                val largo =
                    state.length
                        .toDoubleOrNull()

                val peso =
                    state.weight
                        .toDoubleOrNull()


                when {

                    senderId == null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Valida el DNI del remitente"
                            )

                        return@Button
                    }


                    recipientId == null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Valida el DNI del destinatario"
                            )

                        return@Button
                    }


                    senderId ==
                            recipientId -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Remitente y destinatario deben ser diferentes"
                            )

                        return@Button
                    }


                    state.paymentMethod
                        .isBlank() -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Selecciona la modalidad de pago"
                            )

                        return@Button
                    }


                    originId == null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "El usuario de recepción no tiene sede asignada"
                            )

                        return@Button
                    }


                    destinationId == null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Selecciona la sede de destino"
                            )

                        return@Button
                    }


                    alto == null ||
                            ancho == null ||
                            largo == null ||
                            peso == null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Completa las dimensiones y peso"
                            )

                        return@Button
                    }


                    state.calculatedCost ==
                            null -> {

                        state =
                            state.copy(
                                errorMessage =
                                    "Primero calcula el costo del envío"
                            )

                        return@Button
                    }
                }


                state =
                    state.copy(

                        isLoading =
                            true,

                        errorMessage =
                            null
                    )


                encomiendaViewModel
                    .registrar(

                        remitenteId =
                            senderId,

                        destinatarioId =
                            recipientId,

                        sedeOrigenId =
                            originId,

                        sedeDestinoId =
                            destinationId,

                        altoCm =
                            alto!!,

                        anchoCm =
                            ancho!!,

                        largoCm =
                            largo!!,

                        pesoKg =
                            peso!!,

                        modalidadPago =
                            state.paymentMethod,

                        observacion =
                            state.observation,

                        onSuccess = {
                                result ->

                            state =
                                state.copy(
                                    isLoading =
                                        false
                                )


                            // El backend devuelve los valores definitivos
                            onShipmentGenerated(

                                result.codigoRastreo,

                                result.claveRastreo,

                                result.remitenteDni,

                                result.destinatarioDni,

                                result.origen,

                                result.destino,

                                state.weight,

                                state.height,

                                state.width,

                                state.length,

                                state.weight,

                                formatoPrecio(
                                    result.costoTotal
                                ),

                                if (
                                    state.paymentMethod ==
                                    "ORIGEN"
                                ) {

                                    "Pago en origen"

                                } else {

                                    "Pago en destino"
                                }
                            )
                        },

                        onError = {
                                error ->

                            state =
                                state.copy(

                                    isLoading =
                                        false,

                                    errorMessage =
                                        error
                                )
                        }
                    )
            },

            enabled =
                !state.isLoading &&
                        state.calculatedCost != null,

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
                            Orange,

                        contentColor =
                            Surface
                    )
        ) {

            if (
                state.isLoading
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            22.dp
                        ),

                    strokeWidth =
                        2.dp,

                    color =
                        Surface
                )

            } else {

                Text(
                    text =
                        "Registrar encomienda",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    20.dp
                )
        )
    }
}


@Composable
private fun PersonaCard(
    titulo: String,
    dni: String,
    nombres: String,
    apellidos: String
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                12.dp
            ),

        border =
            BorderStroke(
                1.dp,
                Success
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
                    14.dp
                )
        ) {

            Text(
                text =
                    "✓ $titulo",

                color =
                    Success,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )


            Text(
                text =
                    "DNI: $dni",

                color =
                    Ink
            )


            Text(
                text =
                    "Nombres: $nombres",

                color =
                    Ink
            )


            Text(
                text =
                    "Apellidos: $apellidos",

                color =
                    Ink
            )
        }
    }
}


@Composable
private fun PaymentOption(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    if (
        selected
    ) {

        Button(
            onClick =
                onClick,

            modifier =
                modifier,

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Orange
                    )
        ) {

            Text(
                "✓ $text"
            )
        }

    } else {

        OutlinedButton(
            onClick =
                onClick,

            modifier =
                modifier
        ) {

            Text(
                text
            )
        }
    }
}


@Composable
private fun DecimalField(
    value: String,
    label: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value =
            value,

        onValueChange = {

            if (
                it.count { char ->
                    char == '.'
                } <= 1 &&
                it.all { char ->
                    char.isDigit() ||
                            char == '.'
                }
            ) {

                onValueChange(
                    it
                )
            }
        },

        modifier =
            modifier,

        label = {

            Text(
                label
            )
        },

        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    KeyboardType.Decimal
            ),

        singleLine =
            true
    )
}


private fun formatoPrecio(
    valor: Double
): String {

    val centavos =
        (
                valor *
                        100
                )
            .roundToLong()


    val absoluto =
        abs(
            centavos
        )


    val entero =
        absoluto /
                100


    val decimal =
        (
                absoluto %
                        100
                )
            .toString()
            .padStart(
                2,
                '0'
            )


    return "$entero.$decimal"
}