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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionRolesAdminContent(

    onSalirClick: () -> Unit = {},

    // Aquí llegan las sedes creadas en Gestión de sedes
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

    // Texto utilizado para buscar usuarios
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // Controla el formulario
    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    // -1 significa que se está agregando un usuario
    var indiceEditar by remember {
        mutableStateOf(-1)
    }

    // Datos del usuario
    var nombreUsuario by remember {
        mutableStateOf("")
    }

    var rolSeleccionado by remember {
        mutableStateOf("")
    }

    var sedeSeleccionada by remember {
        mutableStateOf("")
    }

    var usuarioLogin by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    // Controla los desplegables
    var rolExpandido by remember {
        mutableStateOf(false)
    }

    var sedeExpandida by remember {
        mutableStateOf(false)
    }

    // Mensaje de validación
    var mensajeError by remember {
        mutableStateOf("")
    }


    // Roles permitidos en el sistema
    val rolesDisponibles = listOf(
        "Recepción",
        "Almacén",
        "Movilidad"
    )


    /*
     * Posiciones utilizadas:
     *
     * [0] Nombre
     * [1] Rol
     * [2] Sede
     * [3] Último acceso
     * [4] Usuario
     * [5] Contraseña
     */
    val usuarios = remember {

        mutableStateListOf(

            listOf(
                "Mariela Ríos",
                "Recepción",
                "Tarapoto",
                "Hoy 09:12",
                "mariela.rios",
                "123456"
            ),

            listOf(
                "Pedro Tuesta",
                "Almacén",
                "Tarapoto",
                "Hoy 08:40",
                "pedro.tuesta",
                "123456"
            ),

            listOf(
                "José Vela",
                "Movilidad",
                "Todas",
                "Hoy 07:55",
                "jose.vela",
                "123456"
            )
        )
    }


    // Filtra mientras el administrador escribe
    val usuariosFiltrados = usuarios.filter { usuario ->

        textoBusqueda.isBlank() ||

                usuario[0].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                usuario[1].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                usuario[2].contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||

                usuario[4].contains(
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
                    text = "Gestión de roles",
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


        // Buscador y botón para registrar usuario
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            // Busca por nombre, rol, sede o usuario
            OutlinedTextField(
                value = textoBusqueda,

                onValueChange = {

                    // Actualiza la tabla mientras se escribe
                    textoBusqueda = it
                },

                modifier =
                    Modifier.weight(1f),

                placeholder = {
                    Text(
                        "Buscar usuario",
                        color = Muted // Color gris para el placeholder
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Buscar usuario",
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


            // Abre el formulario de nuevo usuario
            Button(
                onClick = {

                    indiceEditar = -1

                    nombreUsuario = ""
                    rolSeleccionado = ""
                    sedeSeleccionada = ""
                    usuarioLogin = ""
                    contrasena = ""
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
                        "Nuevo usuario"
                )

                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )

                Text(
                    text = "Nuevo usuario",
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        // Separación entre buscador y tabla
        Spacer(
            modifier = Modifier.height(22.dp)
        )


        // Permite desplazar la tabla en pantallas pequeñas
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
                            text = "Usuario",
                            modifier =
                                Modifier.weight(2.3f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )

                        Text(
                            text = "Rol",
                            modifier =
                                Modifier.weight(1.4f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )

                        Text(
                            text = "Sede asignada",
                            modifier =
                                Modifier.weight(1.5f),
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Muted // Color gris para encabezados
                        )

                        Text(
                            text = "Último acceso",
                            modifier =
                                Modifier.weight(1.4f),
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


                    // Muestra los usuarios encontrados
                    usuariosFiltrados.forEach { usuario ->

                        val indiceReal =
                            usuarios.indexOf(usuario)


                        // Obtiene las iniciales del usuario
                        val partesNombre =
                            usuario[0].split(" ")

                        val iniciales =
                            if (partesNombre.size >= 2) {

                                "${partesNombre[0].first()}" +
                                        "${partesNombre[1].first()}"

                            } else {

                                usuario[0]
                                    .take(2)
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


                            // Muestra iniciales y nombre
                            Row(
                                modifier =
                                    Modifier.weight(2.3f),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Surface(
                                    shape = CircleShape,

                                    color = NavySoft, // Fondo azul marino suave para el círculo

                                    modifier =
                                        Modifier.size(36.dp)
                                ) {

                                    Box(
                                        contentAlignment =
                                            Alignment.Center
                                    ) {

                                        Text(
                                            text = iniciales,

                                            fontSize = 12.sp,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color = Navy // Color Azul Marino para las iniciales
                                        )
                                    }
                                }


                                Spacer(
                                    modifier =
                                        Modifier.width(10.dp)
                                )


                                Text(
                                    text = usuario[0],

                                    fontSize = 14.sp,

                                    fontWeight =
                                        FontWeight.Medium,

                                    color = Ink // Color oscuro para el nombre
                                )
                            }


                            // Rol del usuario
                            Text(
                                text = usuario[1],

                                modifier =
                                    Modifier.weight(1.4f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el rol
                            )


                            // Sede asignada
                            Text(
                                text = usuario[2],

                                modifier =
                                    Modifier.weight(1.5f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para la sede
                            )


                            // Último ingreso al sistema
                            Text(
                                text = usuario[3],

                                modifier =
                                    Modifier.weight(1.4f),

                                fontSize = 14.sp,

                                color = Ink // Color oscuro para el acceso
                            )


                            // Abre el usuario para editar
                            Box(
                                modifier =
                                    Modifier.weight(0.6f)
                            ) {

                                IconButton(
                                    onClick = {

                                        indiceEditar =
                                            indiceReal


                                        // Carga los datos actuales
                                        nombreUsuario =
                                            usuario[0]

                                        rolSeleccionado =
                                            usuario[1]

                                        sedeSeleccionada =
                                            usuario[2]

                                        usuarioLogin =
                                            usuario[4]

                                        contrasena =
                                            usuario[5]

                                        mensajeError = ""

                                        mostrarFormulario =
                                            true
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Edit,

                                        contentDescription =
                                            "Editar usuario",

                                        tint = Navy // Color Azul Marino para el icono
                                    )
                                }
                            }
                        }


                        // Separa un usuario del siguiente
                        HorizontalDivider(
                            color = Border // Color de borde sutil
                        )
                    }


                    // Mensaje si no existen coincidencias
                    if (usuariosFiltrados.isEmpty()) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(35.dp),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "No se encontraron usuarios",

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


    // Formulario para agregar o editar usuarios
    if (mostrarFormulario) {

        AlertDialog(

            onDismissRequest = {
                mostrarFormulario = false
            },


            title = {

                Text(
                    text =
                        if (indiceEditar == -1) {
                            "Nuevo usuario"
                        } else {
                            "Editar usuario"
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
                        Arrangement.spacedBy(12.dp)
                ) {


                    // Nombre completo del trabajador
                    OutlinedTextField(
                        value = nombreUsuario,

                        onValueChange = {
                            nombreUsuario = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {
                            Text("Nombre completo")
                        },

                        singleLine = true,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Border,
                            focusedTextColor = Ink,
                            unfocusedTextColor = Ink
                        )
                    )


                    // Selecciona el rol del usuario
                    ExposedDropdownMenuBox(
                        expanded =
                            rolExpandido,

                        onExpandedChange = {
                            rolExpandido =
                                !rolExpandido
                        }
                    ) {

                        OutlinedTextField(
                            value =
                                rolSeleccionado,

                            onValueChange = {},

                            readOnly = true,

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            label = {
                                Text("Rol")
                            },

                            trailingIcon = {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ArrowDropDown,

                                    contentDescription =
                                        "Rol",
                                    tint = Muted // Color gris para el icono
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
                            expanded =
                                rolExpandido,

                            onDismissRequest = {
                                rolExpandido =
                                    false
                            }
                        ) {

                            rolesDisponibles.forEach { rol ->

                                DropdownMenuItem(

                                    text = {
                                        Text(rol)
                                    },

                                    onClick = {

                                        rolSeleccionado =
                                            rol


                                        // Movilidad trabaja con todas las sedes
                                        if (
                                            rol == "Movilidad"
                                        ) {

                                            sedeSeleccionada =
                                                "Todas"

                                        } else if (
                                            sedeSeleccionada ==
                                            "Todas"
                                        ) {

                                            sedeSeleccionada =
                                                ""
                                        }


                                        rolExpandido =
                                            false
                                    }
                                )
                            }
                        }
                    }


                    // Para Recepción y Almacén se selecciona una sede
                    if (
                        rolSeleccionado != "Movilidad"
                    ) {

                        ExposedDropdownMenuBox(
                            expanded =
                                sedeExpandida,

                            onExpandedChange = {
                                sedeExpandida =
                                    !sedeExpandida
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

                                label = {
                                    Text("Sede asignada")
                                },

                                trailingIcon = {

                                    Icon(
                                        imageVector =
                                            Icons.Default
                                                .ArrowDropDown,

                                        contentDescription =
                                            "Sede",
                                        tint = Muted // Color gris para el icono
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
                                expanded =
                                    sedeExpandida,

                                onDismissRequest = {
                                    sedeExpandida =
                                        false
                                }
                            ) {

                                // Muestra las sedes creadas anteriormente
                                sedesDisponibles.forEach { sede ->

                                    DropdownMenuItem(

                                        text = {
                                            Text(sede)
                                        },

                                        onClick = {

                                            sedeSeleccionada =
                                                sede

                                            sedeExpandida =
                                                false
                                        }
                                    )
                                }
                            }
                        }

                    } else {

                        // Movilidad tiene acceso a todas las sedes
                        OutlinedTextField(
                            value = "Todas",

                            onValueChange = {},

                            readOnly = true,

                            modifier =
                                Modifier.fillMaxWidth(),

                            label = {
                                Text("Sede asignada")
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Navy,
                                unfocusedBorderColor = Border,
                                focusedTextColor = Ink,
                                unfocusedTextColor = Ink
                            )
                        )
                    }


                    HorizontalDivider(
                        color = Border // Color de borde sutil
                    )


                    Text(
                        text = "Credenciales de acceso",

                        fontSize = 14.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Navy // Color Azul Marino para el subtítulo
                    )


                    // Usuario utilizado para iniciar sesión
                    OutlinedTextField(
                        value = usuarioLogin,

                        onValueChange = {
                            usuarioLogin = it
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


                    // Contraseña del usuario
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


                    // Muestra los errores del formulario
                    if (mensajeError.isNotBlank()) {

                        Text(
                            text = mensajeError,

                            fontSize = 12.sp,

                            color = Color(0xFFE53935) // Rojo para errores
                        )
                    }
                }
            },


            confirmButton = {

                Button(
                    onClick = {

                        // Comprueba los campos obligatorios
                        if (
                            nombreUsuario.isBlank() ||
                            rolSeleccionado.isBlank() ||
                            sedeSeleccionada.isBlank() ||
                            usuarioLogin.isBlank() ||
                            contrasena.isBlank()
                        ) {

                            mensajeError =
                                "Completa todos los campos"

                        } else {


                            if (
                                indiceEditar == -1
                            ) {

                                // Agrega un nuevo usuario
                                usuarios.add(

                                    listOf(
                                        nombreUsuario.trim(),
                                        rolSeleccionado,
                                        sedeSeleccionada,

                                        // Un usuario nuevo todavía no ha ingresado
                                        "Sin acceso",

                                        usuarioLogin.trim(),
                                        contrasena
                                    )
                                )

                            } else {

                                // Conserva el último acceso existente
                                val ultimoAcceso =
                                    usuarios[indiceEditar][3]


                                // Actualiza el usuario seleccionado
                                usuarios[indiceEditar] =

                                    listOf(
                                        nombreUsuario.trim(),
                                        rolSeleccionado,
                                        sedeSeleccionada,
                                        ultimoAcceso,
                                        usuarioLogin.trim(),
                                        contrasena
                                    )
                            }


                            mostrarFormulario =
                                false
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
                                "Agregar usuario"
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