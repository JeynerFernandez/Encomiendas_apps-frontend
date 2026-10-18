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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionEncomiendasAdminContent(
    onSalirClick: () -> Unit = {}
) {

    // Texto que escribe el administrador en el buscador
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // Controla el filtro de estados
    var estadoSeleccionado by remember {
        mutableStateOf("Todos los estados")
    }

    var estadosExpandido by remember {
        mutableStateOf(false)
    }

    // Controla el filtro de sedes
    var sedeSeleccionada by remember {
        mutableStateOf("Todas las sedes")
    }

    var sedesExpandido by remember {
        mutableStateOf(false)
    }


    // Estados disponibles para filtrar
    val estados = listOf(
        "Todos los estados",
        "Registrado",
        "Camino a almacén",
        "En almacén",
        "En tránsito",
        "En sede destino",
        "Entregado"
    )


    // Sedes disponibles para filtrar
    val sedes = listOf(
        "Todas las sedes",
        "Tarapoto",
        "Moyobamba",
        "Rioja",
        "Nueva Cajamarca",
        "Pomacochas"
    )

    val encomiendas = listOf(

        listOf(
            "RN-48213",
            "Tarapoto → Moyobamba",
            "5.0 kg",
            "En tránsito",
            "Pago pendiente",
            "Carlos Pérez",
            "Luis Flores",
            "Tarapoto",
            "Moyobamba"
        ),

        listOf(
            "RN-48220",
            "Tarapoto → Moyobamba",
            "2.4 kg",
            "En tránsito",
            "Pagado",
            "Ana Torres",
            "Marco Ruiz",
            "Tarapoto",
            "Moyobamba"
        ),

        listOf(
            "RN-30119",
            "Rioja → Tarapoto",
            "3.2 kg",
            "En sede destino",
            "Pagado",
            "Pedro Salas",
            "Jorge Ríos",
            "Rioja",
            "Tarapoto"
        ),

        listOf(
            "RN-48250",
            "Tarapoto → Pomacochas",
            "1.6 kg",
            "Registrado",
            "Pago pendiente",
            "Miguel Soto",
            "Rosa Díaz",
            "Tarapoto",
            "Pomacochas"
        ),

        listOf(
            "RN-48260",
            "Tarapoto → Rioja",
            "4.0 kg",
            "Camino a almacén",
            "Pago pendiente",
            "María López",
            "Carlos García",
            "Tarapoto",
            "Rioja"
        ),

        listOf(
            "RN-48270",
            "Tarapoto → Rioja",
            "2.1 kg",
            "En almacén",
            "Pagado",
            "Javier Mendoza",
            "Lucía Ramos",
            "Tarapoto",
            "Rioja"
        ),

        listOf(
            "RN-29811",
            "Tarapoto → Nueva Cajamarca",
            "6.6 kg",
            "Entregado",
            "Pagado",
            "José Sánchez",
            "Andrea Flores",
            "Tarapoto",
            "Nueva Cajamarca"
        )
    )


    // Busca automáticamente mientras el administrador escribe
    val encomiendasFiltradas = encomiendas.filter { encomienda ->

        val coincideBusqueda =
            textoBusqueda.isBlank() ||

                    encomienda[0].contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||

                    encomienda[5].contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||

                    encomienda[6].contains(
                        textoBusqueda,
                        ignoreCase = true
                    )


        // Filtra las encomiendas según el estado seleccionado
        val coincideEstado =
            estadoSeleccionado == "Todos los estados" ||
                    encomienda[3] == estadoSeleccionado


        // Filtra si la sede pertenece al origen o destino
        val coincideSede =
            sedeSeleccionada == "Todas las sedes" ||
                    encomienda[7] == sedeSeleccionada ||
                    encomienda[8] == sedeSeleccionada


        coincideBusqueda &&
                coincideEstado &&
                coincideSede
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


        // Espacio superior de la pantalla
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

                    modifier = Modifier.size(24.dp)
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
                    text = "Gestión de encomiendas",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink // Color oscuro para el título principal
                )
            }
        }


        // Separación entre el título y el contenido
        Spacer(
            modifier = Modifier.height(15.dp)
        )


        HorizontalDivider(
            color = Border // Color de borde sutil
        )


        Spacer(
            modifier = Modifier.height(26.dp)
        )


        // Busca por código, remitente o destinatario
        OutlinedTextField(
            value = textoBusqueda,

            onValueChange = {

                // Actualiza los resultados conforme se escribe
                textoBusqueda = it
            },

            modifier =
                Modifier.fillMaxWidth(),

            placeholder = {

                Text(
                    text =
                        "Buscar por código, remitente o destinatario",
                    color = Muted // Color gris para el placeholder
                )
            },

            leadingIcon = {

                Icon(
                    imageVector =
                        Icons.Default.Search,

                    contentDescription =
                        "Buscar",
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


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // Permite filtrar las encomiendas por estado
        ExposedDropdownMenuBox(
            expanded = estadosExpandido,

            onExpandedChange = {
                estadosExpandido =
                    !estadosExpandido
            }
        ) {

            OutlinedTextField(
                value =
                    estadoSeleccionado,

                onValueChange = {},

                readOnly = true,

                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),

                trailingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowDropDown,

                        contentDescription =
                            "Estados",
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
                    estadosExpandido,

                onDismissRequest = {
                    estadosExpandido = false
                }
            ) {

                estados.forEach { estado ->

                    DropdownMenuItem(

                        text = {
                            Text(estado)
                        },

                        onClick = {

                            // Cambia el estado utilizado en el filtro
                            estadoSeleccionado =
                                estado

                            estadosExpandido =
                                false
                        }
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Permite filtrar las encomiendas según la sede
        ExposedDropdownMenuBox(
            expanded = sedesExpandido,

            onExpandedChange = {
                sedesExpandido =
                    !sedesExpandido
            }
        ) {

            OutlinedTextField(
                value =
                    sedeSeleccionada,

                onValueChange = {},

                readOnly = true,

                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),

                trailingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowDropDown,

                        contentDescription =
                            "Sedes",
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
                    sedesExpandido,

                onDismissRequest = {
                    sedesExpandido = false
                }
            ) {

                sedes.forEach { sede ->

                    DropdownMenuItem(

                        text = {
                            Text(sede)
                        },

                        onClick = {

                            // Cambia la sede utilizada en el filtro
                            sedeSeleccionada =
                                sede

                            sedesExpandido =
                                false
                        }
                    )
                }
            }
        }


        // Separación entre filtros y resultados
        Spacer(
            modifier = Modifier.height(22.dp)
        )


        // Permite mover la tabla horizontalmente en pantallas pequeñas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {

            Card(
                modifier =
                    Modifier.width(900.dp),

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
                            text = "Código",
                            modifier =
                                Modifier.weight(1.2f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Ruta",
                            modifier =
                                Modifier.weight(2.8f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Peso",
                            modifier =
                                Modifier.weight(0.8f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Estado",
                            modifier =
                                Modifier.weight(1.9f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Pago",
                            modifier =
                                Modifier.weight(1.6f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )
                    }


                    HorizontalDivider(
                        color = Border // Color de borde sutil
                    )


                    // Muestra solo las encomiendas que cumplen los filtros
                    encomiendasFiltradas.forEach { encomienda ->

                        val estado =
                            encomienda[3]

                        val pago =
                            encomienda[4]


                        // Define el color visual según el estado
                        val colorEstado =
                            when (estado) {

                                "En tránsito" ->
                                    Orange // Color naranja para tránsito

                                "En sede destino" ->
                                    Navy // Color azul marino para sede destino

                                "Camino a almacén" ->
                                    Navy // Color azul marino para camino a almacén

                                "En almacén" ->
                                    Navy // Color azul marino para almacén

                                "Entregado" ->
                                    Color(0xFF279B57) // Verde para entregado

                                else ->
                                    Muted // Color gris para otros estados
                            }


                        // Pago pendiente o pagado
                        val colorPago =
                            if (
                                pago == "Pagado"
                            ) {
                                Color(0xFF279B57) // Verde para pagado
                            } else {
                                Color(0xFFE53935) // Rojo para pago pendiente
                            }


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
                                text =
                                    encomienda[0],

                                modifier =
                                    Modifier.weight(1.2f),

                                fontSize = 14.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color = Ink // Color oscuro para el código
                            )


                            Text(
                                text =
                                    encomienda[1],

                                modifier =
                                    Modifier.weight(2.8f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para la ruta
                            )


                            Text(
                                text =
                                    encomienda[2],

                                modifier =
                                    Modifier.weight(0.8f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el peso
                            )


                            // Muestra el estado actual de la encomienda
                            Box(
                                modifier = Modifier
                                    .weight(1.9f)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                20.dp
                                            )
                                        )
                                        .background(
                                            colorEstado.copy(
                                                alpha = 0.13f
                                            )
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {

                                    Text(
                                        text = estado,
                                        fontSize = 12.sp,
                                        fontWeight =
                                            FontWeight.SemiBold,
                                        color =
                                            colorEstado
                                    )
                                }
                            }


                            // Muestra únicamente pago pendiente o pagado
                            Box(
                                modifier =
                                    Modifier.weight(1.6f)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                20.dp
                                            )
                                        )
                                        .background(
                                            colorPago.copy(
                                                alpha = 0.13f
                                            )
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {

                                    Text(
                                        text = pago,
                                        fontSize = 12.sp,
                                        fontWeight =
                                            FontWeight.SemiBold,
                                        color = colorPago
                                    )
                                }
                            }
                        }


                        HorizontalDivider(
                            color = Border // Color de borde sutil
                        )
                    }


                    // Mensaje cuando no existen coincidencias
                    if (
                        encomiendasFiltradas.isEmpty()
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(35.dp),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "No se encontraron encomiendas",

                                fontSize = 14.sp,

                                color = Muted // Color gris para el mensaje
                            )
                        }
                    }
                }
            }
        }


        // Espacio al final de la pantalla
        Spacer(
            modifier = Modifier.height(32.dp)
        )
    }
}