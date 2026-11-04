package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.pe.presentation.theme.*
import pe.edu.pe.presentation.viewmodel.SedeState
import pe.edu.pe.presentation.viewmodel.SedeViewModel


@Composable
fun GestionSedesAdminContent(
    onSalirClick: () -> Unit = {},
    viewModel: SedeViewModel = koinViewModel()
) {

    // Estado que viene del ViewModel
    val state by viewModel.state.collectAsState()

    // Consulta las sedes cuando se abre la pantalla
    LaunchedEffect(Unit) {
        viewModel.getSedes()
    }

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    // ID de la sede que se está editando
    var sedeEditandoId by remember {
        mutableStateOf<Int?>(null)
    }

    var nombreSede by remember {
        mutableStateOf("")
    }

    var ciudad by remember {
        mutableStateOf("")
    }

    var direccion by remember {
        mutableStateOf("")
    }

    var latitud by remember {
        mutableStateOf("")
    }

    var longitud by remember {
        mutableStateOf("")
    }

    var sedeActiva by remember {
        mutableStateOf(true)
    }

    var mensajeError by remember {
        mutableStateOf("")
    }


    // Obtiene las sedes reales desde el backend
    val sedes = when (
        val currentState = state
    ) {

        is SedeState.Success -> {
            currentState.sedes
        }

        else -> {
            emptyList()
        }
    }


    // Filtra sedes por nombre, ciudad o dirección
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


    // Limpia el formulario
    fun limpiarFormulario() {

        sedeEditandoId = null

        nombreSede = ""

        ciudad = ""

        direccion = ""

        latitud = ""

        longitud = ""

        sedeActiva = true

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
                        "Gestión de sedes",

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


        // Buscador y botón Nueva sede
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
                            "Buscar sede",

                        color =
                            Muted
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar sede",

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

                    // Prepara el formulario para registrar una nueva sede
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
                    ButtonDefaults
                        .buttonColors(

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
                        "Nueva sede"
                )


                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )


                Text(
                    text =
                        "Nueva sede",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(22.dp)
        )


        // Muestra carga o error del backend
        when (
            val currentState = state
        ) {

            is SedeState.Loading -> {

                Box(
                    modifier = Modifier
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


            is SedeState.Error -> {

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
                                Modifier.height(
                                    10.dp
                                )
                        )


                        Button(
                            onClick = {

                                // Vuelve a consultar las sedes
                                viewModel.getSedes()
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


        // Tabla de sedes
        if (
            state is SedeState.Success
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
                            1000.dp
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
                                    "Sede",

                                modifier =
                                    Modifier.weight(
                                        2f
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
                                    "Ciudad",

                                modifier =
                                    Modifier.weight(
                                        1.5f
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
                                    "Dirección",

                                modifier =
                                    Modifier.weight(
                                        2.5f
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
                                        1.1f
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


                        // Recorre las sedes recibidas desde MySQL
                        sedesFiltradas.forEach {
                                sede ->


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
                                        sede.name,

                                    modifier =
                                        Modifier.weight(
                                            2f
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
                                        sede.city,

                                    modifier =
                                        Modifier.weight(
                                            1.5f
                                        ),

                                    fontSize =
                                        14.sp,

                                    color =
                                        Ink
                                )


                                Text(
                                    text =
                                        sede.address,

                                    modifier =
                                        Modifier.weight(
                                            2.5f
                                        ),

                                    fontSize =
                                        14.sp,

                                    color =
                                        Ink
                                )


                                // Estado de la sede
                                Box(
                                    modifier =
                                        Modifier.weight(
                                            1.1f
                                        )
                                ) {

                                    val colorEstado =
                                        if (
                                            sede.active
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
                                                    sede.active
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


                                // Abre la sede para editarla
                                Box(
                                    modifier =
                                        Modifier.weight(
                                            0.7f
                                        )
                                ) {

                                    IconButton(
                                        onClick = {

                                            sedeEditandoId =
                                                sede.id

                                            nombreSede =
                                                sede.name

                                            ciudad =
                                                sede.city

                                            direccion =
                                                sede.address

                                            latitud =
                                                sede.latitude
                                                    .toString()

                                            longitud =
                                                sede.longitude
                                                    .toString()

                                            sedeActiva =
                                                sede.active

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
                                                "Editar sede",

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
                            sedesFiltradas
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
                                        "No se encontraron sedes",

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


    // Formulario para crear o editar una sede
    if (
        mostrarFormulario
    ) {

        AlertDialog(

            onDismissRequest = {

                mostrarFormulario =
                    false
            },


            title = {

                Text(
                    text =
                        if (
                            sedeEditandoId == null
                        ) {

                            "Nueva sede"

                        } else {

                            "Editar sede"
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


                    OutlinedTextField(
                        value =
                            nombreSede,

                        onValueChange = {
                            nombreSede = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Nombre de sede"
                            )
                        },

                        singleLine =
                            true
                    )


                    OutlinedTextField(
                        value =
                            ciudad,

                        onValueChange = {
                            ciudad = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Ciudad"
                            )
                        },

                        singleLine =
                            true
                    )


                    OutlinedTextField(
                        value =
                            direccion,

                        onValueChange = {
                            direccion = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Dirección"
                            )
                        },

                        singleLine =
                            true
                    )


                    OutlinedTextField(
                        value =
                            latitud,

                        onValueChange = {
                            latitud = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Latitud"
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


                    OutlinedTextField(
                        value =
                            longitud,

                        onValueChange = {
                            longitud = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Longitud"
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
                                "Sede activa",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                Ink
                        )


                        Switch(
                            checked =
                                sedeActiva,

                            onCheckedChange = {
                                sedeActiva = it
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

                        // Valida que todos los campos estén completos
                        if (
                            nombreSede.isBlank() ||
                            ciudad.isBlank() ||
                            direccion.isBlank() ||
                            latitud.isBlank() ||
                            longitud.isBlank()
                        ) {

                            mensajeError =
                                "Completa todos los campos"

                            return@Button
                        }


                        // Convierte las coordenadas a Double
                        val latitudNumero =
                            latitud.toDoubleOrNull()

                        val longitudNumero =
                            longitud.toDoubleOrNull()


                        if (
                            latitudNumero == null ||
                            longitudNumero == null
                        ) {

                            mensajeError =
                                "Latitud y longitud deben ser números"

                            return@Button
                        }


                        // Crea o actualiza la sede dependiendo del ID
                        viewModel.saveSede(

                            id =
                                sedeEditandoId,

                            name =
                                nombreSede.trim(),

                            city =
                                ciudad.trim(),

                            address =
                                direccion.trim(),

                            latitude =
                                latitudNumero,

                            longitude =
                                longitudNumero,

                            active =
                                sedeActiva,

                            onSuccess = {

                                mensajeError =
                                    ""

                                mostrarFormulario =
                                    false

                                limpiarFormulario()
                            },

                            onError = { error ->

                                mensajeError =
                                    error
                            }
                        )
                    },

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                Navy,

                            contentColor =
                                Color.White
                        )
                ) {

                    Text(
                        text =
                            if (
                                sedeEditandoId == null
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
}