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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados


@Composable
fun GestionSedesAdminContent(
    onSalirClick: () -> Unit = {}
) {

    // Texto utilizado para buscar una sede
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // Controla el formulario de agregar o editar
    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    var indiceEditar by remember {
        mutableStateOf(-1)
    }

    // Datos ingresados en el formulario
    var nombreSede by remember {
        mutableStateOf("")
    }

    var ciudad by remember {
        mutableStateOf("")
    }

    var responsable by remember {
        mutableStateOf("")
    }

    var usuario by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var sedeActiva by remember {
        mutableStateOf(true)
    }

    // Muestra errores si algún campo obligatorio está vacío
    var mensajeError by remember {
        mutableStateOf("")
    }


    /*
     * [0] Nombre de sede
     * [1] Ciudad
     * [2] Responsable
     * [3] Encomiendas activas
     * [4] Estado
     * [5] Usuario
     * [6] Contraseña
     */
    val sedes = remember {

        mutableStateListOf(

            listOf(
                "Sede Central",
                "Tarapoto",
                "Jeyner Fernandez",
                "7",
                "Activa",
                "rosa.tarapoto",
                "123456"
            ),

            listOf(
                "Sede Norte",
                "Moyobamba",
                "Jan Arevalo",
                "2",
                "Activa",
                "ivan.moyobamba",
                "123456"
            ),

            listOf(
                "Sede Rioja",
                "Rioja",
                "Fernando Chumacero",
                "3",
                "Activa",
                "carla.rioja",
                "123456"
            ),

            listOf(
                "Sede Nueva Cajamarca",
                "Nueva Cajamarca",
                "Antony ",
                "1",
                "Inactiva",
                "",
                ""
            ),
        )
    }


    // Filtra automáticamente mientras se escribe en el buscador
    val sedesFiltradas = sedes.filter { sede ->

        textoBusqueda.isBlank() ||

                sede[0].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                sede[1].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                sede[2].contains(
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
                    text = "Gestión de sedes",
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


        // Buscador y botón para registrar una nueva sede
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            // Busca por nombre de sede, ciudad o responsable
            OutlinedTextField(
                value = textoBusqueda,

                onValueChange = {

                    // La tabla se actualiza conforme el usuario escribe
                    textoBusqueda = it
                },

                modifier =
                    Modifier.weight(1f),

                placeholder = {
                    Text(
                        "Buscar sede",
                        color = Muted // Color gris para el placeholder
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar sede",
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


            // Abre el formulario para registrar una sede
            Button(
                onClick = {

                    indiceEditar = -1

                    nombreSede = ""
                    ciudad = ""
                    responsable = ""
                    usuario = ""
                    contrasena = ""
                    sedeActiva = true
                    mensajeError = ""

                    mostrarFormulario = true
                },

                modifier = Modifier.height(56.dp),

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Navy, // Fondo Azul Marino
                        contentColor = Color.White // Texto blanco
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,

                    contentDescription =
                        "Nueva sede"
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Nueva sede",
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // Separación entre buscador y tabla
        Spacer(
            modifier = Modifier.height(22.dp)
        )


        // Permite visualizar la tabla en pantallas pequeñas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {

            Card(
                modifier =
                    Modifier.width(1000.dp),

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
                            text = "Sede",
                            modifier =
                                Modifier.weight(2f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Ciudad",
                            modifier =
                                Modifier.weight(1.5f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Responsable",
                            modifier =
                                Modifier.weight(1.7f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Encomiendas activas",
                            modifier =
                                Modifier.weight(1.4f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Estado",
                            modifier =
                                Modifier.weight(1.1f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )


                        Text(
                            text = "Editar",
                            modifier =
                                Modifier.weight(0.7f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )
                    }


                    HorizontalDivider(
                        color = Border // Color de borde sutil
                    )


                    // Muestra todas las sedes que coinciden con la búsqueda
                    sedesFiltradas.forEach { sede ->

                        val indiceReal =
                            sedes.indexOf(sede)


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
                                text = sede[0],

                                modifier =
                                    Modifier.weight(2f),

                                fontSize = 14.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color = Ink // Color oscuro para el nombre
                            )


                            Text(
                                text = sede[1],

                                modifier =
                                    Modifier.weight(1.5f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para la ciudad
                            )


                            Text(
                                text = sede[2],

                                modifier =
                                    Modifier.weight(1.7f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el responsable
                            )


                            Text(
                                text = sede[3],

                                modifier =
                                    Modifier.weight(1.4f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el número
                            )


                            // Muestra si la sede está activa o inactiva
                            Box(
                                modifier =
                                    Modifier.weight(1.1f)
                            ) {

                                val colorEstado =
                                    if (
                                        sede[4] == "Activa"
                                    ) {
                                        Color(0xFF279B57) // Verde para activa
                                    } else {
                                        Color(0xFFE53935) // Rojo para inactiva
                                    }


                                Box(
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                20.dp
                                            )
                                        )
                                        .background(
                                            colorEstado.copy(
                                                alpha = 0.12f
                                            )
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {

                                    Text(
                                        text = sede[4],
                                        fontSize = 12.sp,
                                        fontWeight =
                                            FontWeight.SemiBold,
                                        color =
                                            colorEstado
                                    )
                                }
                            }


                            // Permite editar los datos de la sede
                            Box(
                                modifier =
                                    Modifier.weight(0.7f)
                            ) {

                                IconButton(
                                    onClick = {

                                        indiceEditar =
                                            indiceReal

                                        nombreSede =
                                            sede[0]

                                        ciudad =
                                            sede[1]

                                        responsable =
                                            if (
                                                sede[2] ==
                                                "— Sin asignar"
                                            ) {
                                                ""
                                            } else {
                                                sede[2]
                                            }

                                        usuario =
                                            sede[5]

                                        contrasena =
                                            sede[6]

                                        sedeActiva =
                                            sede[4] ==
                                                    "Activa"

                                        mensajeError = ""

                                        mostrarFormulario =
                                            true
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Edit,

                                        contentDescription =
                                            "Editar sede",

                                        tint = Navy // Color Azul Marino para el icono
                                    )
                                }
                            }
                        }


                        // Separa visualmente cada sede
                        HorizontalDivider(
                            color = Border // Color de borde sutil
                        )
                    }


                    // Mensaje cuando la búsqueda no encuentra resultados
                    if (
                        sedesFiltradas.isEmpty()
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
                                    "No se encontraron sedes",

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


    // Formulario utilizado para agregar o editar una sede
    if (mostrarFormulario) {

        AlertDialog(

            onDismissRequest = {
                mostrarFormulario = false
            },

            title = {

                Text(
                    text =
                        if (indiceEditar == -1)
                            "Nueva sede"
                        else
                            "Editar sede",

                    fontWeight =
                        FontWeight.Bold,
                    color = Ink // Color oscuro para el título
                )
            },

            text = {

                Column(
                    modifier = Modifier
                        .verticalScroll(
                            rememberScrollState()
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {


                    // Nombre que tendrá la sede
                    OutlinedTextField(
                        value = nombreSede,

                        onValueChange = {
                            nombreSede = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Nombre de sede")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Ciudad donde funcionará la sede
                    OutlinedTextField(
                        value = ciudad,

                        onValueChange = {
                            ciudad = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Ciudad")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Persona encargada de la sede
                    OutlinedTextField(
                        value = responsable,

                        onValueChange = {
                            responsable = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Responsable")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    HorizontalDivider(
                        color = Border // Color de borde sutil
                    )


                    Text(
                        text = "Credenciales de acceso",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el subtítulo
                    )


                    // Usuario utilizado para iniciar sesión
                    OutlinedTextField(
                        value = usuario,

                        onValueChange = {
                            usuario = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Usuario")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Contraseña asignada a la sede
                    OutlinedTextField(
                        value = contrasena,

                        onValueChange = {
                            contrasena = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Contraseña")
                        },

                        singleLine = true,

                        visualTransformation =
                            PasswordVisualTransformation(),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Permite activar o desactivar la sede
                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Sede activa",
                            fontSize = 14.sp,
                            fontWeight =
                                FontWeight.Medium,
                            color = Ink // Color oscuro para el texto
                        )

                        Switch(
                            checked =
                                sedeActiva,

                            onCheckedChange = {
                                sedeActiva = it
                            },

                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Navy,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Muted
                            )
                        )
                    }


                    // Muestra un mensaje si faltan datos
                    if (
                        mensajeError.isNotBlank()
                    ) {

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

                        // Valida los campos antes de guardar
                        if (
                            nombreSede.isBlank() ||
                            ciudad.isBlank() ||
                            responsable.isBlank() ||
                            usuario.isBlank() ||
                            contrasena.isBlank()
                        ) {

                            mensajeError =
                                "Completa todos los campos"

                        } else {

                            val estado =
                                if (sedeActiva)
                                    "Activa"
                                else
                                    "Inactiva"


                            if (indiceEditar == -1) {

                                // Al agregar, la nueva sede aparece inmediatamente en la tabla
                                sedes.add(

                                    listOf(
                                        nombreSede.trim(),
                                        ciudad.trim(),
                                        responsable.trim(),

                                        // Una sede nueva inicia sin encomiendas activas
                                        "0",

                                        estado,
                                        usuario.trim(),
                                        contrasena
                                    )
                                )

                            } else {

                                // Actualiza la sede seleccionada conservando sus encomiendas
                                val encomiendasActivas =
                                    sedes[indiceEditar][3]


                                sedes[indiceEditar] =

                                    listOf(
                                        nombreSede.trim(),
                                        ciudad.trim(),
                                        responsable.trim(),
                                        encomiendasActivas,
                                        estado,
                                        usuario.trim(),
                                        contrasena
                                    )
                            }


                            mostrarFormulario = false
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Navy, // Fondo Azul Marino
                        contentColor = Color.White // Texto blanco
                    )
                ) {

                    Text(
                        text =
                            if (indiceEditar == -1)
                                "Agregar"
                            else
                                "Guardar"
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