package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.roundToLong
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.presentation.theme.*
import pe.edu.pe.presentation.viewmodel.SedeState
import pe.edu.pe.presentation.viewmodel.SedeViewModel
import pe.edu.pe.presentation.viewmodel.TarifaState
import pe.edu.pe.presentation.viewmodel.TarifaViewModel


// Formatea precios sin usar String.format
private fun formatoDecimal(
    valor: Double
): String {

    val centavos =
        (valor * 100).roundToLong()

    val valorAbsoluto =
        abs(centavos)

    val entero =
        valorAbsoluto / 100

    val decimal =
        (valorAbsoluto % 100)
            .toString()
            .padStart(
                2,
                '0'
            )

    val signo =
        if (centavos < 0) {
            "-"
        } else {
            ""
        }

    return "$signo$entero.$decimal"
}


@Composable
fun GestionTarifasAdminContent(
    onSalirClick: () -> Unit = {},
    sedeViewModel: SedeViewModel = koinViewModel(),
    tarifaViewModel: TarifaViewModel = koinViewModel()
) {

    // Estados recibidos desde los ViewModel
    val sedeState by
    sedeViewModel.state.collectAsState()

    val tarifaState by
    tarifaViewModel.state.collectAsState()


    // Carga sedes y tarifas al abrir la pantalla
    LaunchedEffect(Unit) {

        sedeViewModel.getSedes()

        tarifaViewModel.getTarifas()
    }


    var textoBusqueda by remember {
        mutableStateOf("")
    }


    var mostrarFormulario by remember {
        mutableStateOf(false)
    }


    var mostrarSelectorOrigen by remember {
        mutableStateOf(false)
    }


    var mostrarSelectorDestino by remember {
        mutableStateOf(false)
    }


    // Si tiene ID, estamos editando
    var tarifaEditandoId by remember {
        mutableStateOf<Int?>(null)
    }


    var sedeOrigenId by remember {
        mutableStateOf<Int?>(null)
    }


    var sedeDestinoId by remember {
        mutableStateOf<Int?>(null)
    }


    var sedeOrigen by remember {
        mutableStateOf("")
    }


    var sedeDestino by remember {
        mutableStateOf("")
    }


    var tarifaBase by remember {
        mutableStateOf("")
    }


    var precioKgExcedente by remember {
        mutableStateOf("")
    }


    var tarifaActiva by remember {
        mutableStateOf(true)
    }


    var mensajeError by remember {
        mutableStateOf("")
    }


    // Obtiene las sedes activas del backend
    val sedesDisponibles =
        when (
            val currentState =
                sedeState
        ) {

            is SedeState.Success -> {

                currentState.sedes
                    .filter {
                        it.active
                    }
            }

            else -> {

                emptyList()
            }
        }


    // Obtiene las tarifas reales del backend
    val tarifas =
        when (
            val currentState =
                tarifaState
        ) {

            is TarifaState.Success -> {

                currentState.tarifas
            }

            else -> {

                emptyList()
            }
        }


    // Busca por sede origen o destino
    val tarifasFiltradas =
        tarifas.filter { tarifa ->

            textoBusqueda.isBlank() ||

                    tarifa.sedeOrigen.contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||

                    tarifa.sedeDestino.contains(
                        textoBusqueda,
                        ignoreCase = true
                    )
        }


    // Limpia el formulario
    fun limpiarFormulario() {

        tarifaEditandoId = null

        sedeOrigenId = null

        sedeDestinoId = null

        sedeOrigen = ""

        sedeDestino = ""

        tarifaBase = ""

        precioKgExcedente = ""

        tarifaActiva = true

        mensajeError = ""
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                LightBackground
            )
            .statusBarsPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 18.dp
            )
    ) {

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // Encabezado
        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick =
                    onSalirClick,

                modifier =
                    Modifier.size(40.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored
                            .Filled
                            .ArrowBack,

                    contentDescription =
                        "Regresar",

                    tint =
                        Navy,

                    modifier =
                        Modifier.size(24.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )


            Column {

                Text(
                    text =
                        "Administrador",

                    fontSize =
                        12.sp,

                    color =
                        Muted
                )


                Text(
                    text =
                        "Gestión de tarifas",

                    fontSize =
                        25.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(15.dp)
        )


        HorizontalDivider(
            color =
                Border
        )


        Spacer(
            modifier =
                Modifier.height(26.dp)
        )


        // Buscador y botón nueva tarifa
        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value =
                    textoBusqueda,

                onValueChange = {
                    textoBusqueda = it
                },

                modifier =
                    Modifier.weight(1f),

                placeholder = {

                    Text(
                        text =
                            "Buscar tarifa",

                        color =
                            Muted
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar tarifa",

                        tint =
                            Muted
                    )
                },

                singleLine =
                    true,

                shape =
                    RoundedCornerShape(
                        14.dp
                    ),

                colors =
                    OutlinedTextFieldDefaults
                        .colors(

                            focusedBorderColor =
                                Navy,

                            unfocusedBorderColor =
                                Border,

                            focusedTextColor =
                                Ink,

                            unfocusedTextColor =
                                Ink
                        )
            )


            Button(
                onClick = {

                    limpiarFormulario()

                    mostrarFormulario =
                        true
                },

                modifier =
                    Modifier.height(56.dp),

                shape =
                    RoundedCornerShape(
                        14.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Navy,

                        contentColor =
                            Color.White
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,

                    contentDescription =
                        "Nueva tarifa"
                )


                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )


                Text(
                    text =
                        "Nueva tarifa",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(22.dp)
        )


        // Estado de carga o error
        when (
            val currentState =
                tarifaState
        ) {

            is TarifaState.Loading -> {

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 35.dp
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color =
                            Navy
                    )
                }
            }


            is TarifaState.Error -> {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(
                                    0xFFFFEBEE
                                )
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
                                currentState.message,

                            color =
                                Color(
                                    0xFFE53935
                                ),

                            fontSize =
                                14.sp
                        )


                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )


                        Button(
                            onClick = {

                                tarifaViewModel
                                    .getTarifas()
                            },

                            colors =
                                ButtonDefaults
                                    .buttonColors(
                                        containerColor =
                                            Navy
                                    )
                        ) {

                            Text(
                                text =
                                    "Reintentar"
                            )
                        }
                    }
                }
            }


            else -> Unit
        }


        // Tabla de tarifas
        if (
            tarifaState is
                    TarifaState.Success
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        )
            ) {

                Card(
                    modifier =
                        Modifier.width(
                            1100.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    border =
                        BorderStroke(
                            1.dp,
                            Border
                        ),

                    colors =
                        CardDefaults
                            .cardColors(
                                containerColor =
                                    Surface
                            )
                ) {

                    Column {


                        // Encabezados
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal =
                                            14.dp,

                                        vertical =
                                            14.dp
                                    ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text =
                                    "Origen",

                                modifier =
                                    Modifier.weight(
                                        1.6f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Destino",

                                modifier =
                                    Modifier.weight(
                                        1.6f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Tarifa base",

                                modifier =
                                    Modifier.weight(
                                        1.2f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Peso incluido",

                                modifier =
                                    Modifier.weight(
                                        1.2f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Kg excedente",

                                modifier =
                                    Modifier.weight(
                                        1.2f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Estado",

                                modifier =
                                    Modifier.weight(
                                        1f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )


                            Text(
                                text =
                                    "Editar",

                                modifier =
                                    Modifier.weight(
                                        0.7f
                                    ),

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Muted
                            )
                        }


                        HorizontalDivider(
                            color =
                                Border
                        )


                        // Tarifas recibidas desde MySQL
                        tarifasFiltradas
                            .forEach { tarifa ->


                                Row(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal =
                                                    14.dp,

                                                vertical =
                                                    14.dp
                                            ),

                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {


                                    Text(
                                        text =
                                            tarifa.sedeOrigen,

                                        modifier =
                                            Modifier.weight(
                                                1.6f
                                            ),

                                        fontSize =
                                            14.sp,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            Ink
                                    )


                                    Text(
                                        text =
                                            tarifa.sedeDestino,

                                        modifier =
                                            Modifier.weight(
                                                1.6f
                                            ),

                                        fontSize =
                                            14.sp,

                                        color =
                                            Ink
                                    )


                                    Text(
                                        text =
                                            "S/ ${
                                                formatoDecimal(
                                                    tarifa.precioBase
                                                )
                                            }",

                                        modifier =
                                            Modifier.weight(
                                                1.2f
                                            ),

                                        fontSize =
                                            14.sp,

                                        color =
                                            Ink
                                    )


                                    // Peso incluido fijo del negocio
                                    Text(
                                        text =
                                            "20 kg",

                                        modifier =
                                            Modifier.weight(
                                                1.2f
                                            ),

                                        fontSize =
                                            14.sp,

                                        color =
                                            Ink
                                    )


                                    Text(
                                        text =
                                            "S/ ${
                                                formatoDecimal(
                                                    tarifa
                                                        .precioKgExcedente
                                                )
                                            }",

                                        modifier =
                                            Modifier.weight(
                                                1.2f
                                            ),

                                        fontSize =
                                            14.sp,

                                        color =
                                            Ink
                                    )


                                    // Estado de la tarifa
                                    Box(
                                        modifier =
                                            Modifier.weight(
                                                1f
                                            )
                                    ) {

                                        val colorEstado =
                                            if (
                                                tarifa.active
                                            ) {

                                                Color(
                                                    0xFF279B57
                                                )

                                            } else {

                                                Color(
                                                    0xFFE53935
                                                )
                                            }


                                        Box(
                                            modifier =
                                                Modifier
                                                    .clip(
                                                        RoundedCornerShape(
                                                            20.dp
                                                        )
                                                    )
                                                    .background(
                                                        colorEstado
                                                            .copy(
                                                                alpha =
                                                                    0.12f
                                                            )
                                                    )
                                                    .padding(
                                                        horizontal =
                                                            12.dp,

                                                        vertical =
                                                            6.dp
                                                    )
                                        ) {

                                            Text(
                                                text =
                                                    if (
                                                        tarifa.active
                                                    ) {

                                                        "Activa"

                                                    } else {

                                                        "Inactiva"
                                                    },

                                                fontSize =
                                                    12.sp,

                                                fontWeight =
                                                    FontWeight
                                                        .SemiBold,

                                                color =
                                                    colorEstado
                                            )
                                        }
                                    }


                                    // Carga la tarifa para editar
                                    Box(
                                        modifier =
                                            Modifier.weight(
                                                0.7f
                                            )
                                    ) {

                                        IconButton(
                                            onClick = {

                                                tarifaEditandoId =
                                                    tarifa.id

                                                sedeOrigenId =
                                                    tarifa
                                                        .sedeOrigenId

                                                sedeDestinoId =
                                                    tarifa
                                                        .sedeDestinoId

                                                sedeOrigen =
                                                    tarifa
                                                        .sedeOrigen

                                                sedeDestino =
                                                    tarifa
                                                        .sedeDestino

                                                tarifaBase =
                                                    tarifa
                                                        .precioBase
                                                        .toString()

                                                precioKgExcedente =
                                                    tarifa
                                                        .precioKgExcedente
                                                        .toString()

                                                tarifaActiva =
                                                    tarifa.active

                                                mensajeError =
                                                    ""

                                                mostrarFormulario =
                                                    true
                                            }
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Edit,

                                                contentDescription =
                                                    "Editar tarifa",

                                                tint =
                                                    Navy
                                            )
                                        }
                                    }
                                }


                                HorizontalDivider(
                                    color =
                                        Border
                                )
                            }


                        if (
                            tarifasFiltradas
                                .isEmpty()
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            35.dp
                                        ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text =
                                        "No se encontraron tarifas",

                                    fontSize =
                                        14.sp,

                                    color =
                                        Muted
                                )
                            }
                        }
                    }
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(
                    32.dp
                )
        )
    }


    // Formulario para crear o editar
    if (
        mostrarFormulario
    ) {

        AlertDialog(

            onDismissRequest = {

                mostrarFormulario =
                    false

                limpiarFormulario()
            },


            title = {

                Text(
                    text =
                        if (
                            tarifaEditandoId == null
                        ) {

                            "Nueva tarifa"

                        } else {

                            "Editar tarifa"
                        },

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )
            },


            text = {

                Column(
                    modifier =
                        Modifier
                            .verticalScroll(
                                rememberScrollState()
                            ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {


                    // Sede origen
                    Text(
                        text =
                            "Sede origen",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )


                    OutlinedButton(
                        onClick = {

                            mostrarSelectorOrigen =
                                true
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                10.dp
                            ),

                        border =
                            BorderStroke(
                                1.dp,
                                Border
                            )
                    ) {

                        Text(
                            text =
                                if (
                                    sedeOrigen
                                        .isBlank()
                                ) {

                                    "Seleccionar sede"

                                } else {

                                    sedeOrigen
                                },

                            modifier =
                                Modifier.weight(1f),

                            color =
                                if (
                                    sedeOrigen
                                        .isBlank()
                                ) {

                                    Muted

                                } else {

                                    Ink
                                }
                        )


                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription =
                                "Buscar sede",

                            tint =
                                Navy
                        )
                    }


                    // Sede destino
                    Text(
                        text =
                            "Sede destino",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )


                    OutlinedButton(
                        onClick = {

                            mostrarSelectorDestino =
                                true
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                10.dp
                            ),

                        border =
                            BorderStroke(
                                1.dp,
                                Border
                            )
                    ) {

                        Text(
                            text =
                                if (
                                    sedeDestino
                                        .isBlank()
                                ) {

                                    "Seleccionar sede"

                                } else {

                                    sedeDestino
                                },

                            modifier =
                                Modifier.weight(1f),

                            color =
                                if (
                                    sedeDestino
                                        .isBlank()
                                ) {

                                    Muted

                                } else {

                                    Ink
                                }
                        )


                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription =
                                "Buscar sede",

                            tint =
                                Navy
                        )
                    }


                    // Precio base
                    OutlinedTextField(
                        value =
                            tarifaBase,

                        onValueChange = {

                            tarifaBase = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {

                            Text(
                                "Tarifa base (S/)"
                            )
                        },

                        singleLine =
                            true,

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Decimal
                            )
                    )


                    // El peso incluido es fijo en 20 kg
                    OutlinedTextField(
                        value =
                            "20 kg",

                        onValueChange = {},

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {

                            Text(
                                "Peso incluido"
                            )
                        },

                        enabled =
                            false,

                        singleLine =
                            true
                    )


                    // Precio por cada kilo excedente
                    OutlinedTextField(
                        value =
                            precioKgExcedente,

                        onValueChange = {

                            precioKgExcedente =
                                it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {

                            Text(
                                "Precio por kg excedente (S/)"
                            )
                        },

                        singleLine =
                            true,

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Decimal
                            )
                    )


                    // Activa o desactiva la tarifa
                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text =
                                "Tarifa activa",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                Ink
                        )


                        Switch(
                            checked =
                                tarifaActiva,

                            onCheckedChange = {

                                tarifaActiva =
                                    it
                            },

                            colors =
                                SwitchDefaults
                                    .colors(

                                        checkedThumbColor =
                                            Color.White,

                                        checkedTrackColor =
                                            Navy,

                                        uncheckedThumbColor =
                                            Color.White,

                                        uncheckedTrackColor =
                                            Muted
                                    )
                        )
                    }


                    // Error al cargar sedes
                    if (
                        sedeState is
                                SedeState.Error
                    ) {

                        Text(
                            text =
                                (
                                        sedeState as
                                                SedeState.Error
                                        ).message,

                            color =
                                Color(
                                    0xFFE53935
                                ),

                            fontSize =
                                12.sp
                        )
                    }


                    if (
                        mensajeError
                            .isNotBlank()
                    ) {

                        Text(
                            text =
                                mensajeError,

                            color =
                                Color(
                                    0xFFE53935
                                ),

                            fontSize =
                                12.sp
                        )
                    }
                }
            },


            confirmButton = {

                Button(
                    onClick = {

                        // Debe existir origen y destino
                        if (
                            sedeOrigenId == null ||
                            sedeDestinoId == null
                        ) {

                            mensajeError =
                                "Selecciona la sede de origen y destino"

                            return@Button
                        }


                        // No se permite la misma sede
                        if (
                            sedeOrigenId ==
                            sedeDestinoId
                        ) {

                            mensajeError =
                                "La sede origen y destino deben ser diferentes"

                            return@Button
                        }


                        if (
                            tarifaBase
                                .isBlank() ||
                            precioKgExcedente
                                .isBlank()
                        ) {

                            mensajeError =
                                "Completa los precios de la tarifa"

                            return@Button
                        }


                        val precioBaseNumero =
                            tarifaBase
                                .toDoubleOrNull()

                        val precioKgNumero =
                            precioKgExcedente
                                .toDoubleOrNull()


                        if (
                            precioBaseNumero == null ||
                            precioKgNumero == null
                        ) {

                            mensajeError =
                                "Los precios deben ser números"

                            return@Button
                        }


                        if (
                            precioBaseNumero <= 0 ||
                            precioKgNumero < 0
                        ) {

                            mensajeError =
                                "Ingresa precios válidos"

                            return@Button
                        }


                        // ID null = POST / ID existente = PUT
                        tarifaViewModel
                            .saveTarifa(

                                id =
                                    tarifaEditandoId,

                                sedeOrigenId =
                                    sedeOrigenId!!,

                                sedeDestinoId =
                                    sedeDestinoId!!,

                                precioBase =
                                    precioBaseNumero,

                                precioKgExcedente =
                                    precioKgNumero,

                                active =
                                    tarifaActiva,

                                onSuccess = {

                                    mensajeError =
                                        ""

                                    mostrarFormulario =
                                        false

                                    limpiarFormulario()
                                },

                                onError = {
                                        error ->

                                    mensajeError =
                                        error
                                }
                            )
                    },

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    Navy,

                                contentColor =
                                    Color.White
                            )
                ) {

                    Text(
                        text =
                            if (
                                tarifaEditandoId ==
                                null
                            ) {

                                "Agregar"

                            } else {

                                "Guardar"
                            }
                    )
                }
            },


            dismissButton = {

                TextButton(
                    onClick = {

                        mostrarFormulario =
                            false

                        limpiarFormulario()
                    }
                ) {

                    Text(
                        text =
                            "Cancelar",

                        color =
                            Navy
                    )
                }
            }
        )
    }


    // Selector de sede origen
    if (
        mostrarSelectorOrigen
    ) {

        SelectorSedeTarifaDialog(

            titulo =
                "Seleccionar sede de origen",

            sedes =
                sedesDisponibles
                    .filter {

                        it.id !=
                                sedeDestinoId
                    },

            onSeleccionar = {
                    sede ->

                sedeOrigenId =
                    sede.id

                sedeOrigen =
                    sede.name

                mostrarSelectorOrigen =
                    false
            },

            onCerrar = {

                mostrarSelectorOrigen =
                    false
            }
        )
    }


    // Selector de sede destino
    if (
        mostrarSelectorDestino
    ) {

        SelectorSedeTarifaDialog(

            titulo =
                "Seleccionar sede de destino",

            sedes =
                sedesDisponibles
                    .filter {

                        it.id !=
                                sedeOrigenId
                    },

            onSeleccionar = {
                    sede ->

                sedeDestinoId =
                    sede.id

                sedeDestino =
                    sede.name

                mostrarSelectorDestino =
                    false
            },

            onCerrar = {

                mostrarSelectorDestino =
                    false
            }
        )
    }
}


// Buscador reutilizable de sedes
@Composable
private fun SelectorSedeTarifaDialog(
    titulo: String,
    sedes: List<Sede>,
    onSeleccionar: (Sede) -> Unit,
    onCerrar: () -> Unit
) {

    var textoBusqueda by remember {
        mutableStateOf("")
    }


    val sedesFiltradas =
        sedes.filter { sede ->

            textoBusqueda.isBlank() ||

                    sede.name.contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||

                    sede.city.contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||

                    sede.address.contains(
                        textoBusqueda,
                        ignoreCase = true
                    )
        }


    AlertDialog(

        onDismissRequest =
            onCerrar,


        title = {

            Text(
                text =
                    titulo,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )
        },


        text = {

            Column {

                OutlinedTextField(
                    value =
                        textoBusqueda,

                    onValueChange = {

                        textoBusqueda =
                            it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    placeholder = {

                        Text(
                            text =
                                "Buscar sede"
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription =
                                "Buscar"
                        )
                    },

                    singleLine =
                        true
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )


                if (
                    sedesFiltradas
                        .isEmpty()
                ) {

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    100.dp
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                "No se encontraron sedes",

                            color =
                                Muted,

                            fontSize =
                                14.sp
                        )
                    }

                } else {

                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    max = 320.dp
                                ),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                8.dp
                            )
                    ) {

                        items(
                            items =
                                sedesFiltradas,

                            key = {
                                it.id
                            }
                        ) { sede ->


                            OutlinedButton(
                                onClick = {

                                    onSeleccionar(
                                        sede
                                    )
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        10.dp
                                    ),

                                border =
                                    BorderStroke(
                                        1.dp,
                                        Border
                                    )
                            ) {

                                Column(
                                    modifier =
                                        Modifier.fillMaxWidth()
                                ) {

                                    Text(
                                        text =
                                            sede.name,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            Ink
                                    )


                                    Text(
                                        text =
                                            sede.city,

                                        fontSize =
                                            12.sp,

                                        color =
                                            Muted
                                    )


                                    Text(
                                        text =
                                            sede.address,

                                        fontSize =
                                            11.sp,

                                        color =
                                            Muted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },


        confirmButton = {},


        dismissButton = {

            TextButton(
                onClick =
                    onCerrar
            ) {

                Text(
                    text =
                        "Cerrar",

                    color =
                        Navy
                )
            }
        }
    )
}