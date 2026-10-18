package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionMovilidadAdminContent(

    onSalirClick: () -> Unit = {},

    // Transportistas creados anteriormente con rol Movilidad
    transportistasDisponibles: List<String> = listOf(
        "José Vela",
        "Daniel Ríos",
        "Luis Chávez"
    ),

    // Rutas creadas anteriormente en Gestión de rutas
    rutasDisponibles: List<String> = listOf(
        "Tarapoto → Moyobamba",
        "Tarapoto → Rioja → Nueva Cajamarca",
        "Tarapoto → Pomacochas"
    )
) {

    // Texto utilizado para buscar por placa o transportista
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // Controla el formulario
    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    // -1 significa que se está agregando un nuevo registro
    var indiceEditar by remember {
        mutableStateOf(-1)
    }

    // Datos del formulario
    var placa by remember {
        mutableStateOf("")
    }

    var transportistaSeleccionado by remember {
        mutableStateOf("")
    }

    var rutaSeleccionada by remember {
        mutableStateOf("Sin asignar")
    }

    // Controla los desplegables
    var transportistaExpandido by remember {
        mutableStateOf(false)
    }

    var rutaExpandida by remember {
        mutableStateOf(false)
    }

    // Mensaje utilizado para validaciones
    var mensajeError by remember {
        mutableStateOf("")
    }


    /*
     * [0] Placa
     * [1] Transportista
     * [2] Ruta activa
     * [3] Encomiendas
     * [4] Estado
     */
    val movilidades = remember {

        mutableStateListOf(

            listOf(
                "ABC-731",
                "José Vela",
                "Tarapoto → Moyobamba",
                "2",
                "En tránsito"
            ),

            listOf(
                "DEF-902",
                "Daniel Ríos",
                "Tarapoto → Rioja → Nueva Cajamarca",
                "1",
                "En tránsito"
            ),

            listOf(
                "GHI-450",
                "Luis Chávez",
                "Sin asignar",
                "0",
                "Disponible"
            )
        )
    }


    // Filtra mientras el administrador escribe
    val movilidadesFiltradas = movilidades.filter { movilidad ->

        textoBusqueda.isBlank() ||

                movilidad[0].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                movilidad[1].contains(
                    textoBusqueda,
                    ignoreCase = true
                )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 18.dp
            )
    ) {

        // Margen superior
        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // Flecha para regresar al panel administrador
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onSalirClick,
                modifier = Modifier.size(40.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,

                    contentDescription = "Regresar",

                    tint = Navy,

                    modifier =
                        Modifier.size(24.dp)
                )
            }


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            Column {

                Text(
                    text = "Administrador",
                    fontSize = 12.sp,
                    color = Muted
                )

                Text(
                    text = "Gestión de movilidad",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
            }
        }


        // Separación del encabezado
        Spacer(
            modifier = Modifier.height(15.dp)
        )

        HorizontalDivider(
            color = Border
        )

        Spacer(
            modifier = Modifier.height(26.dp)
        )


        // --- SECCIÓN MEJORADA: Buscador y botón ---
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            // Busca por placa o nombre del transportista
            OutlinedTextField(
                value = textoBusqueda,

                onValueChange = {
                    textoBusqueda = it
                },

                modifier = Modifier
                    .weight(1f)
                    .height(56.dp), // Altura fija compacta

                placeholder = {
                    Text(
                        "Buscar por placa o transportista",
                        color = Muted,
                        fontSize = 13.sp, // Texto más pequeño para que quepa
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Muted,
                        modifier = Modifier.size(20.dp)
                    )
                },

                singleLine = true, // Forzar una sola línea
                shape = RoundedCornerShape(14.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Navy,
                    unfocusedBorderColor = Border,
                    focusedTextColor = Ink,
                    unfocusedTextColor = Ink,
                    focusedContainerColor = Surface, // Fondo blanco al enfocar
                    unfocusedContainerColor = Surface // Fondo blanco normal
                )
            )


            // Abre el formulario para agregar movilidad
            Button(
                onClick = {
                    indiceEditar = -1
                    placa = ""
                    transportistaSeleccionado = ""
                    rutaSeleccionada = "Sin asignar"
                    mensajeError = ""
                    mostrarFormulario = true
                },

                modifier = Modifier.height(56.dp), // Misma altura que el buscador

                shape = RoundedCornerShape(14.dp),

                contentPadding = PaddingValues(horizontal = 16.dp), // Menos padding interno

                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nuevo transportista",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Nuevo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        // --- FIN SECCIÓN MEJORADA ---


        // Separación entre buscador y tabla
        Spacer(
            modifier = Modifier.height(22.dp)
        )


        // Permite mover la tabla en pantallas pequeñas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {

            Card(
                modifier =
                    Modifier.width(950.dp),

                shape =
                    RoundedCornerShape(16.dp),

                border =
                    BorderStroke(
                        1.dp,
                        Border
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor = Surface
                    )
            ) {

                Column {

                    // Encabezados de la tabla
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 14.dp,
                                vertical = 14.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Placa",
                            modifier = Modifier.weight(1f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )


                        Text(
                            text = "Transportista",
                            modifier = Modifier.weight(1.8f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )


                        Text(
                            text = "Ruta activa",
                            modifier = Modifier.weight(3f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )


                        Text(
                            text = "Encomiendas",
                            modifier = Modifier.weight(1.1f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )


                        Text(
                            text = "Estado",
                            modifier = Modifier.weight(1.2f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )


                        Text(
                            text = "Editar",
                            modifier = Modifier.weight(0.6f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Muted
                        )
                    }


                    HorizontalDivider(
                        color = Border
                    )


                    // Muestra las movilidades encontradas
                    movilidadesFiltradas.forEach { movilidad ->

                        val indiceReal =
                            movilidades.indexOf(movilidad)


                        // Obtiene las iniciales del transportista
                        val partesNombre =
                            movilidad[1].split(" ")

                        val iniciales =
                            if (partesNombre.size >= 2) {

                                "${partesNombre[0].first()}" +
                                        "${partesNombre[1].first()}"

                            } else {

                                movilidad[1].take(2)
                            }
                                .uppercase()


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 14.dp
                                ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {


                            // Placa del vehículo
                            Text(
                                text = movilidad[0],
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Ink
                            )


                            // Iniciales y nombre del transportista
                            Row(
                                modifier = Modifier.weight(1.8f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Surface(
                                    modifier = Modifier.size(36.dp),
                                    shape = CircleShape,
                                    color = NavySoft
                                ) {

                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text(
                                            text = iniciales,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Navy
                                        )
                                    }
                                }


                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )


                                Text(
                                    text = movilidad[1],
                                    fontSize = 14.sp,
                                    color = Ink
                                )
                            }


                            // Ruta actualmente asignada
                            Text(
                                text = movilidad[2],
                                modifier = Modifier.weight(3f),
                                fontSize = 14.sp,
                                color = Ink
                            )


                            // Cantidad de encomiendas asignadas
                            Text(
                                text = movilidad[3],
                                modifier = Modifier.weight(1.1f),
                                fontSize = 14.sp,
                                color = Ink
                            )


                            // Estado actual de la movilidad
                            Box(
                                modifier = Modifier.weight(1.2f)
                            ) {

                                val colorEstado =
                                    if (movilidad[4] == "En tránsito") {
                                        Orange
                                    } else {
                                        Muted
                                    }


                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(colorEstado.copy(alpha = 0.12f))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {

                                    Text(
                                        text = movilidad[4],
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colorEstado
                                    )
                                }
                            }


                            // Abre el registro para editar
                            Box(
                                modifier = Modifier.weight(0.6f)
                            ) {

                                IconButton(
                                    onClick = {

                                        indiceEditar = indiceReal
                                        placa = movilidad[0]
                                        transportistaSeleccionado = movilidad[1]
                                        rutaSeleccionada = movilidad[2]
                                        mensajeError = ""
                                        mostrarFormulario = true
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = Navy
                                    )
                                }
                            }
                        }


                        // Separa una movilidad de la siguiente
                        HorizontalDivider(
                            color = Border
                        )
                    }


                    // Mensaje cuando no existen resultados
                    if (movilidadesFiltradas.isEmpty()) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(35.dp),

                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "No se encontraron transportistas",
                                fontSize = 14.sp,
                                color = Muted
                            )
                        }
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(32.dp)
        )
    }


    // Formulario para agregar o editar movilidad
    if (mostrarFormulario) {

        AlertDialog(

            onDismissRequest = {
                mostrarFormulario = false
            },


            title = {

                Text(
                    text = if (indiceEditar == -1) "Nuevo transportista" else "Editar transportista",
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
            },


            text = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {


                    // Ingresa la placa del vehículo
                    OutlinedTextField(
                        value = placa,

                        onValueChange = {
                            placa = it.uppercase()
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Placa del vehículo")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Selecciona un usuario con rol Movilidad
                    ExposedDropdownMenuBox(
                        expanded = transportistaExpandido,

                        onExpandedChange = {
                            transportistaExpandido = !transportistaExpandido
                        }
                    ) {

                        OutlinedTextField(
                            value = transportistaSeleccionado,

                            onValueChange = {},

                            readOnly = true,

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            label = {
                                Text("Transportista")
                            },

                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Transportistas",
                                    tint = Muted
                                )
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Navy,
                                unfocusedBorderColor = Border,
                                focusedTextColor = Ink,
                                unfocusedTextColor = Ink
                            )
                        )


                        ExposedDropdownMenu(
                            expanded = transportistaExpandido,

                            onDismissRequest = {
                                transportistaExpandido = false
                            }
                        ) {

                            transportistasDisponibles.forEach { transportista ->

                                DropdownMenuItem(

                                    text = {
                                        Text(transportista)
                                    },

                                    onClick = {
                                        transportistaSeleccionado = transportista
                                        transportistaExpandido = false
                                    }
                                )
                            }
                        }
                    }


                    // Selecciona una ruta creada anteriormente
                    ExposedDropdownMenuBox(
                        expanded = rutaExpandida,

                        onExpandedChange = {
                            rutaExpandida = !rutaExpandida
                        }
                    ) {

                        OutlinedTextField(
                            value = rutaSeleccionada,

                            onValueChange = {},

                            readOnly = true,

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            label = {
                                Text("Ruta activa")
                            },

                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Rutas",
                                    tint = Muted
                                )
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Navy,
                                unfocusedBorderColor = Border,
                                focusedTextColor = Ink,
                                unfocusedTextColor = Ink
                            )
                        )


                        ExposedDropdownMenu(
                            expanded = rutaExpandida,

                            onDismissRequest = {
                                rutaExpandida = false
                            }
                        ) {

                            DropdownMenuItem(
                                text = {
                                    Text("Sin asignar")
                                },
                                onClick = {
                                    rutaSeleccionada = "Sin asignar"
                                    rutaExpandida = false
                                }
                            )


                            rutasDisponibles.forEach { ruta ->

                                DropdownMenuItem(
                                    text = {
                                        Text(ruta)
                                    },
                                    onClick = {
                                        rutaSeleccionada = ruta
                                        rutaExpandida = false
                                    }
                                )
                            }
                        }
                    }


                    // Explica cómo se obtiene el estado
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = NavySoft
                    ) {

                        Text(
                            text = if (rutaSeleccionada == "Sin asignar") {
                                "Sin ruta asignada. El transportista quedará disponible."
                            } else {
                                "Con una ruta asignada, el transportista aparecerá en tránsito."
                            },

                            modifier = Modifier.padding(14.dp),
                            fontSize = 13.sp,
                            color = Navy
                        )
                    }


                    // Muestra errores del formulario
                    if (mensajeError.isNotBlank()) {

                        Text(
                            text = mensajeError,
                            fontSize = 12.sp,
                            color = Color(0xFFE53935)
                        )
                    }
                }
            },


            confirmButton = {

                Button(
                    onClick = {

                        if (placa.isBlank() || transportistaSeleccionado.isBlank()) {

                            mensajeError = "Completa todos los campos"

                        } else {

                            val estado = if (rutaSeleccionada == "Sin asignar") {
                                "Disponible"
                            } else {
                                "En tránsito"
                            }


                            if (indiceEditar == -1) {

                                movilidades.add(
                                    listOf(
                                        placa.trim(),
                                        transportistaSeleccionado,
                                        rutaSeleccionada,
                                        "0",
                                        estado
                                    )
                                )

                            } else {

                                val encomiendasActivas = movilidades[indiceEditar][3]

                                movilidades[indiceEditar] = listOf(
                                    placa.trim(),
                                    transportistaSeleccionado,
                                    rutaSeleccionada,
                                    encomiendasActivas,
                                    estado
                                )
                            }


                            mostrarFormulario = false
                        }
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Navy,
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = if (indiceEditar == -1) "Agregar transportista" else "Guardar cambios"
                    )
                }
            },


            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarFormulario = false
                    }
                ) {

                    Text(
                        "Cancelar",
                        color = Navy
                    )
                }
            }
        )
    }
}