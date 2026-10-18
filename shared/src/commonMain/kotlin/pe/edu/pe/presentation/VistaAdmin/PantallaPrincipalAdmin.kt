package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardContent(
    onReportesClick: () -> Unit = {},
    onEncomiendasClick: () -> Unit = {},
    onMovilidadClick: () -> Unit = {},
    onSedesClick: () -> Unit = {},
    onRolesClick: () -> Unit = {},
    onRutasClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    notificationCount: Int = 0
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            // Menú lateral (drawer), con el mismo header con
            // fondo de color y avatar que se usa en HomeContent.
            ModalDrawerSheet {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Navy), // Color de fondo del drawer (Azul Marino)
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.size(90.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    modifier = Modifier.size(54.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Manda Ya.",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "Administrador",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    label = {
                        Text("Mi perfil")
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onProfileClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = {
                        Text("Cerrar sesión")
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onLogout()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {

        Scaffold(

            containerColor = LightBackground, // Color de fondo general (Gris muy claro)

            topBar = {

                TopAppBar(

                    title = {

                        Column {

                            Text(
                                text = "Manda Ya.",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = "Administrador",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                    },

                    actions = {

                        IconButton(
                            onClick = {
                                // Aquí puedes abrir la pantalla
                                // de notificaciones
                            }
                        ) {

                            BadgedBox(

                                badge = {

                                    if (notificationCount > 0) {

                                        Badge(
                                            containerColor = Orange // Color naranja para la notificación
                                        ) {

                                            Text(
                                                text = notificationCount.toString()
                                            )
                                        }
                                    }
                                }

                            ) {

                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notificaciones",
                                    tint = Color.White
                                )
                            }
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Navy // Color de la barra superior (Azul Marino)
                    )
                )
            }

        ) { paddingValues ->

            // Contenido del body, igual que en HomeContent:
            // un Column que ocupa toda la pantalla y las tarjetas
            // se reparten el espacio con weight() en vez de una
            // altura fija, para que se adapten a cualquier tamaño.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Panel de administración",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy, // Color del título (Azul Marino)
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Fila 1: REPORTES y ENCOMIENDAS
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Opción 1: REPORTES
                    Card(
                        onClick = onReportesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.BarChart,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Navy // Icono Azul Marino
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "REPORTES",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy, // Texto Azul Marino
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Consulta estadísticas y reportes.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Opción 2: ENCOMIENDAS
                    Card(
                        onClick = onEncomiendasClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Inventory2,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Orange // Icono Naranja
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "ENCOMIENDAS",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange, // Texto Naranja
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Gestiona los envíos registrados.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Fila 2: MOVILIDAD y SEDES
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Opción 3: MOVILIDAD
                    Card(
                        onClick = onMovilidadClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.LocalShipping,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Orange // Icono Naranja
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "MOVILIDAD",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange, // Texto Naranja
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Administra vehículos y movilidad.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Opción 4: SEDES
                    Card(
                        onClick = onSedesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Business,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Navy // Icono Azul Marino
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "SEDES",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy, // Texto Azul Marino
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Revisa y edita las sedes.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Fila 3: ROLES y RUTAS
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Opción 5: ROLES
                    Card(
                        onClick = onRolesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Groups,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Navy // Icono Azul Marino
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "ROLES",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy, // Texto Azul Marino
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Configura roles y permisos.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Opción 6: RUTAS
                    Card(
                        onClick = onRutasClick,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface // Fondo blanco
                        ),
                        border = BorderStroke(1.dp, Border), // Borde sutil
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Route,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Orange // Icono Naranja
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "RUTAS",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Orange, // Texto Naranja
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Ajusta las rutas de reparto.",
                                fontSize = 11.sp,
                                color = Muted, // Texto gris
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}