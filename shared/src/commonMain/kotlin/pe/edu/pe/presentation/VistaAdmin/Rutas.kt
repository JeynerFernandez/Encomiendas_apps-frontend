package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionRutasAdminContent(

    onSalirClick: () -> Unit = {},

    // Aquí llegan las sedes registradas en Gestión de sedes
    sedesDisponibles: List<String> = listOf(
        "Tarapoto",
        "Moyobamba",
        "Rioja",
        "Nueva Cajamarca",
        "Pomacochas",
        "Pedro Ruiz",
        "Bagua Grande"
    )
) {

    // Texto utilizado para buscar rutas
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // Controla el formulario de agregar o editar
    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    // -1 significa que se está agregando una ruta nueva
    var indiceEditar by remember {
        mutableStateOf(-1)
    }


    // Datos del formulario
    var origenSeleccionado by remember {
        mutableStateOf("")
    }

    var destinoSeleccionado by remember {
        mutableStateOf("")
    }

    // Guarda las paradas intermedias
    val paradasIntermedias = remember {
        mutableStateListOf<String>()
    }

    // Controla los desplegables
    var origenExpandido by remember {
        mutableStateOf(false)
    }

    var destinoExpandido by remember {
        mutableStateOf(false)
    }

    var paradaExpandida by remember {
        mutableStateOf(-1)
    }

    // Muestra errores de validación
    var mensajeError by remember {
        mutableStateOf("")
    }


    /*
     * [0] Origen
     * [1] Destino
     * [2] Paradas intermedias
     * [3] Encomiendas activas
     */
    val rutas = remember {

        mutableStateListOf(

            listOf(
                "Tarapoto",
                "Nueva Cajamarca",
                "Moyobamba|Rioja",
                "1"
            ),

            listOf(
                "Tarapoto",
                "Bagua Grande",
                "Pedro Ruiz",
                "0"
            ),

            listOf(
                "Tarapoto",
                "Pomacochas",
                "",
                "1"
            )
        )
    }


    // Busca rutas mientras el administrador escribe
    val rutasFiltradas = rutas.filter { ruta ->

        val paradas = ruta[2]
            .replace("|", " ")

        textoBusqueda.isBlank() ||

                ruta[0].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                ruta[1].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                paradas.contains(
                    textoBusqueda,
                    ignoreCase = true
                )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground) // Color de fondo claro (gris muy suave)
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

                    tint = Navy, // Color Azul Marino para el icono

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
                    color = Muted // Color gris para el subtítulo
                )

                Text(
                    text = "Gestión de rutas",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink // Color oscuro para el título principal
                )
            }
        }


        // Separación del encabezado
        Spacer(
            modifier = Modifier.height(15.dp)
        )

        HorizontalDivider(
            color = Border // Color de borde sutil
        )

        Spacer(
            modifier = Modifier.height(26.dp)
        )


        // Buscador y botón para crear una ruta
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            // Busca por origen, destino o paradas
            OutlinedTextField(
                value = textoBusqueda,

                onValueChange = {

                    // La lista cambia mientras se escribe
                    textoBusqueda = it
                },

                modifier =
                    Modifier.weight(1f),

                placeholder = {
                    Text(
                        "Buscar ruta",
                        color = Muted // Color gris para el placeholder
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar ruta",
                        tint = Muted // Color gris para el icono
                    )
                },

                singleLine = true,

                shape =
                    RoundedCornerShape(14.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Navy,
                    unfocusedBorderColor = Border,
                    focusedTextColor = Ink,
                    unfocusedTextColor = Ink
                )
            )


            // Abre el formulario para una nueva ruta
            Button(
                onClick = {

                    indiceEditar = -1

                    origenSeleccionado = ""
                    destinoSeleccionado = ""

                    paradasIntermedias.clear()

                    mensajeError = ""

                    mostrarFormulario = true
                },

                modifier =
                    Modifier.height(56.dp),

                shape =
                    RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy, // Fondo Azul Marino
                    contentColor = Color.White // Texto blanco
                )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,

                    contentDescription =
                        "Nueva ruta"
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Nueva ruta",
                    fontWeight = FontWeight.Bold
                )
            }
        }


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
                        Border // Color de borde sutil
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor = Surface // Fondo blanco
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
                            text = "Ruta",

                            modifier =
                                Modifier.weight(3f),

                            fontSize = 13.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Paradas",

                            modifier =
                                Modifier.weight(1.8f),

                            fontSize = 13.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Encomiendas activas",

                            modifier =
                                Modifier.weight(1.3f),

                            fontSize = 13.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Editar",

                            modifier =
                                Modifier.weight(0.6f),

                            fontSize = 13.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color = Muted // Color gris para encabezados
                        )
                    }


                    HorizontalDivider(
                        color = Border // Color de borde sutil
                    )


                    // Muestra las rutas encontradas
                    rutasFiltradas.forEach { ruta ->

                        val indiceReal =
                            rutas.indexOf(ruta)


                        // Convierte las paradas guardadas en una lista
                        val paradas =
                            if (ruta[2].isBlank()) {
                                emptyList()
                            } else {
                                ruta[2].split("|")
                            }


                        // Construye la ruta completa
                        val rutaCompleta =
                            buildList {

                                add(ruta[0])

                                addAll(paradas)

                                add(ruta[1])
                            }
                                //De lista, converte a colecion de un solo texto
                                .joinToString(" → ")


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


                            // Muestra origen, paradas y destino
                            Text(
                                text = rutaCompleta,

                                modifier =
                                    Modifier.weight(3f),

                                fontSize = 14.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color = Ink // Color oscuro para la ruta
                            )


                            // Muestra únicamente las paradas intermedias
                            Text(
                                text =
                                    if (paradas.isEmpty()) {
                                        "—"
                                    } else {
                                        paradas.joinToString(", ")
                                    },

                                modifier =
                                    Modifier.weight(1.8f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para las paradas
                            )


                            Text(
                                text = ruta[3],

                                modifier =
                                    Modifier.weight(1.3f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el número
                            )


                            // Permite editar una ruta existente
                            Box(
                                modifier =
                                    Modifier.weight(0.6f)
                            ) {

                                IconButton(
                                    onClick = {

                                        indiceEditar =
                                            indiceReal


                                        // Carga el origen actual
                                        origenSeleccionado =
                                            ruta[0]


                                        // Carga el destino actual
                                        destinoSeleccionado =
                                            ruta[1]


                                        // Carga las paradas existentes
                                        paradasIntermedias.clear()

                                        if (
                                            ruta[2].isNotBlank()
                                        ) {

                                            paradasIntermedias.addAll(
                                                ruta[2].split("|")
                                            )
                                        }


                                        mensajeError = ""

                                        mostrarFormulario =
                                            true
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Edit,

                                        contentDescription =
                                            "Editar ruta",

                                        tint = Navy // Color Azul Marino para el icono
                                    )
                                }
                            }
                        }


                        // Separa una ruta de la siguiente
                        HorizontalDivider(
                            color = Border // Color de borde sutil
                        )
                    }


                    // Mensaje cuando no existen coincidencias
                    if (rutasFiltradas.isEmpty()) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(35.dp),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "No se encontraron rutas",

                                fontSize = 14.sp,

                                color = Muted // Color gris para el mensaje
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


    // Formulario para agregar o editar rutas
    if (mostrarFormulario) {

        AlertDialog(

            onDismissRequest = {
                mostrarFormulario = false
            },


            title = {

                Text(
                    text =
                        if (indiceEditar == -1) {
                            "Nueva ruta"
                        } else {
                            "Editar ruta"
                        },

                    fontWeight =
                        FontWeight.Bold,
                    color = Ink // Color oscuro para el título
                )
            },


            text = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(
                            rememberScrollState()
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp)
                ) {


                    // Selecciona la sede de origen
                    Text(
                        text = "Ruta origen",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el subtítulo
                    )


                    ExposedDropdownMenuBox(
                        expanded =
                            origenExpandido,

                        onExpandedChange = {
                            origenExpandido =
                                !origenExpandido
                        }
                    ) {

                        OutlinedTextField(
                            value =
                                origenSeleccionado,

                            onValueChange = {},

                            readOnly = true,

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            placeholder = {
                                Text(
                                    "Selecciona sede",
                                    color = Muted // Color gris para el placeholder
                                )
                            },

                            trailingIcon = {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ArrowDropDown,

                                    contentDescription =
                                        "Origen",
                                    tint = Muted // Color gris para el icono
                                )
                            },

                            shape =
                                RoundedCornerShape(12.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Navy,
                                unfocusedBorderColor = Border,
                                focusedTextColor = Ink,
                                unfocusedTextColor = Ink
                            )
                        )


                        ExposedDropdownMenu(
                            expanded =
                                origenExpandido,

                            onDismissRequest = {
                                origenExpandido = false
                            }
                        ) {

                            // Usa las sedes registradas en Gestión de sedes
                            sedesDisponibles.forEach { sede ->

                                DropdownMenuItem(

                                    text = {
                                        Text(sede)
                                    },

                                    onClick = {

                                        origenSeleccionado =
                                            sede

                                        origenExpandido =
                                            false
                                    }
                                )
                            }
                        }
                    }


                    // Muestra las paradas intermedias
                    Text(
                        text = "Paradas intermedias",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el subtítulo
                    )


                    if (paradasIntermedias.isEmpty()) {

                        // Se muestra cuando la ruta no tiene paradas
                        Surface(
                            modifier =
                                Modifier.fillMaxWidth(),

                            shape =
                                RoundedCornerShape(10.dp),

                            color = NavySoft // Fondo azul marino suave para el aviso
                        ) {

                            Text(
                                text =
                                    "Sin paradas intermedias. La ruta será directa origen → destino.",

                                modifier =
                                    Modifier.padding(14.dp),

                                fontSize = 13.sp,

                                color = Navy // Color Azul Marino para el texto
                            )
                        }
                    }


                    // Muestra cada parada agregada
                    paradasIntermedias.forEachIndexed { index, parada ->

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {


                            // Número de orden de la parada
                            Surface(
                                shape =
                                    RoundedCornerShape(20.dp),

                                color = NavySoft // Fondo azul marino suave
                            ) {

                                Text(
                                    text =
                                        "${index + 1}",

                                    modifier =
                                        Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 7.dp
                                        ),

                                    fontWeight =
                                        FontWeight.Bold,

                                    color = Navy // Color Azul Marino
                                )
                            }


                            // Selector de la parada
                            ExposedDropdownMenuBox(
                                expanded =
                                    paradaExpandida == index,

                                onExpandedChange = {

                                    paradaExpandida =
                                        if (
                                            paradaExpandida ==
                                            index
                                        ) {
                                            -1
                                        } else {
                                            index
                                        }
                                },

                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                OutlinedTextField(
                                    value =
                                        parada,

                                    onValueChange = {},

                                    readOnly = true,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),

                                    placeholder = {
                                        Text(
                                            "Selecciona sede",
                                            color = Muted // Color gris para el placeholder
                                        )
                                    },

                                    trailingIcon = {

                                        Icon(
                                            imageVector =
                                                Icons.Default
                                                    .ArrowDropDown,

                                            contentDescription =
                                                "Parada",
                                            tint = Muted // Color gris para el icono
                                        )
                                    },

                                    singleLine = true,

                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Navy,
                                        unfocusedBorderColor = Border,
                                        focusedTextColor = Ink,
                                        unfocusedTextColor = Ink
                                    )
                                )


                                ExposedDropdownMenu(
                                    expanded =
                                        paradaExpandida ==
                                                index,

                                    onDismissRequest = {
                                        paradaExpandida =
                                            -1
                                    }
                                ) {

                                    // Muestra las sedes disponibles
                                    sedesDisponibles.forEach { sede ->

                                        DropdownMenuItem(

                                            text = {
                                                Text(sede)
                                            },

                                            onClick = {

                                                // Cambia la sede seleccionada para esta parada
                                                paradasIntermedias[index] =
                                                    sede

                                                paradaExpandida =
                                                    -1
                                            }
                                        )
                                    }
                                }
                            }


                            // Elimina una parada intermedia
                            IconButton(
                                onClick = {

                                    paradasIntermedias.removeAt(
                                        index
                                    )
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Delete,

                                    contentDescription =
                                        "Eliminar parada",

                                    tint = Color(0xFFE53935) // Rojo para el icono de eliminar
                                )
                            }
                        }
                    }


                    // Agrega una nueva parada intermedia
                    OutlinedButton(
                        onClick = {

                            paradasIntermedias.add("")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(12.dp),

                        border = BorderStroke(1.dp, Navy), // Borde Azul Marino

                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Navy // Texto Azul Marino
                        )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Add,

                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(6.dp)
                        )

                        Text(
                            text =
                                "Agregar parada intermedia",

                            fontWeight =
                                FontWeight.Bold
                        )
                    }


                    // Selecciona la sede final de la ruta
                    Text(
                        text = "Ruta destino",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el subtítulo
                    )


                    ExposedDropdownMenuBox(
                        expanded =
                            destinoExpandido,

                        onExpandedChange = {
                            destinoExpandido =
                                !destinoExpandido
                        }
                    ) {

                        OutlinedTextField(
                            value =
                                destinoSeleccionado,

                            onValueChange = {},

                            readOnly = true,

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            placeholder = {
                                Text(
                                    "Selecciona sede",
                                    color = Muted // Color gris para el placeholder
                                )
                            },

                            trailingIcon = {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ArrowDropDown,

                                    contentDescription =
                                        "Destino",
                                    tint = Muted // Color gris para el icono
                                )
                            },

                            shape =
                                RoundedCornerShape(12.dp),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Navy,
                                unfocusedBorderColor = Border,
                                focusedTextColor = Ink,
                                unfocusedTextColor = Ink
                            )
                        )


                        ExposedDropdownMenu(
                            expanded =
                                destinoExpandido,

                            onDismissRequest = {
                                destinoExpandido = false
                            }
                        ) {

                            // Usa las mismas sedes registradas
                            sedesDisponibles.forEach { sede ->

                                DropdownMenuItem(

                                    text = {
                                        Text(sede)
                                    },

                                    onClick = {

                                        destinoSeleccionado =
                                            sede

                                        destinoExpandido =
                                            false
                                    }
                                )
                            }
                        }
                    }


                    // Explica cómo se recorrerán las paradas
                    Surface(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(10.dp),

                        color = NavySoft // Fondo azul marino suave para el aviso
                    ) {

                        Text(
                            text =
                                "Las paradas se visitan en el orden mostrado. Puedes agregar tantas como necesites.",

                            modifier =
                                Modifier.padding(14.dp),

                            fontSize = 13.sp,

                            color = Navy // Color Azul Marino para el texto
                        )
                    }


                    // Muestra errores del formulario
                    if (mensajeError.isNotBlank()) {

                        Text(
                            text = mensajeError,

                            color = Color(0xFFE53935), // Rojo para errores

                            fontSize = 12.sp
                        )
                    }
                }
            },


            confirmButton = {

                Button(
                    onClick = {

                        // Verifica que origen y destino estén seleccionados
                        if (
                            origenSeleccionado.isBlank() ||
                            destinoSeleccionado.isBlank()
                        ) {

                            mensajeError =
                                "Selecciona origen y destino"

                        }

                        // Evita crear una ruta con el mismo origen y destino
                        else if (
                            origenSeleccionado ==
                            destinoSeleccionado
                        ) {

                            mensajeError =
                                "El origen y destino no pueden ser iguales"
                        }

                        // Verifica que las paradas agregadas tengan una sede
                        else if (
                            paradasIntermedias.any {
                                it.isBlank()
                            }
                        ) {

                            mensajeError =
                                "Selecciona todas las paradas intermedias"
                        }

                        else {

                            val todasLasSedes =
                                listOf(
                                    origenSeleccionado
                                ) +
                                        paradasIntermedias +
                                        destinoSeleccionado


                            // Evita repetir una sede dentro de la misma ruta
                            if (
                                todasLasSedes.distinct()
                                    .size !=
                                todasLasSedes.size
                            ) {

                                mensajeError =
                                    "Una sede no puede repetirse en la ruta"

                            } else {

                                // Guarda las paradas para mostrarlas después
                                val paradasGuardadas =
                                    paradasIntermedias
                                        .joinToString("|")


                                if (
                                    indiceEditar == -1
                                ) {

                                    // Agrega una nueva ruta
                                    rutas.add(

                                        listOf(
                                            origenSeleccionado,
                                            destinoSeleccionado,
                                            paradasGuardadas,

                                            // Una ruta nueva inicia sin encomiendas activas
                                            "0"
                                        )
                                    )

                                } else {

                                    // Conserva las encomiendas activas de la ruta
                                    val encomiendasActivas =
                                        rutas[indiceEditar][3]


                                    // Actualiza la ruta seleccionada
                                    rutas[indiceEditar] =

                                        listOf(
                                            origenSeleccionado,
                                            destinoSeleccionado,
                                            paradasGuardadas,
                                            encomiendasActivas
                                        )
                                }


                                mostrarFormulario =
                                    false
                            }
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Navy, // Fondo Azul Marino
                        contentColor = Color.White // Texto blanco
                    )
                ) {

                    Text(
                        text =
                            if (
                                indiceEditar == -1
                            ) {
                                "Guardar ruta"
                            } else {
                                "Guardar cambios"
                            }
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
                        color = Navy // Color Azul Marino para el botón cancelar
                    )
                }
            }
        )
    }
}