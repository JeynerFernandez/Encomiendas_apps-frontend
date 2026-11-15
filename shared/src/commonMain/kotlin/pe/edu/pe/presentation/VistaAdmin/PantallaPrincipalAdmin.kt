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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import pe.edu.pe.presentation.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardContent(
    onReportesClick: () -> Unit = {},
    onEncomiendasClick: () -> Unit = {},
    onMovilidadClick: () -> Unit = {},
    onSedesClick: () -> Unit = {},
    onRolesClick: () -> Unit = {},
    onRutasClick: () -> Unit = {},
    onTarifasClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    notificationCount: Int = 0
) {

    val drawerState =
        rememberDrawerState(
            initialValue =
                DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()


    ModalNavigationDrawer(

        drawerState =
            drawerState,

        drawerContent = {

            ModalDrawerSheet {

                // Encabezado del menú lateral
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Navy),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Surface(
                            shape =
                                CircleShape,

                            color =
                                Color.White.copy(
                                    alpha = 0.15f
                                ),

                            modifier =
                                Modifier.size(90.dp)
                        ) {

                            Box(
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.AdminPanelSettings,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(54.dp),

                                    tint =
                                        Color.White
                                )
                            }
                        }


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text =
                                "Manda Ya.",

                            color =
                                Color.White,

                            style =
                                MaterialTheme.typography
                                    .headlineSmall
                        )


                        Text(
                            text =
                                "Administrador",

                            color =
                                Color.White.copy(
                                    alpha = 0.7f
                                )
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )


                // Perfil
                NavigationDrawerItem(

                    label = {

                        Text(
                            "Mi perfil"
                        )
                    },

                    selected =
                        false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        onProfileClick()
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                null
                        )
                    },

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                // Cerrar sesión
                NavigationDrawerItem(

                    label = {

                        Text(
                            "Cerrar sesión"
                        )
                    },

                    selected =
                        false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        onLogout()
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.AutoMirrored
                                    .Filled
                                    .ExitToApp,

                            contentDescription =
                                null
                        )
                    },

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )
            }
        }
    ) {


        Scaffold(

            containerColor =
                LightBackground,

            topBar = {

                TopAppBar(

                    title = {

                        Column {

                            Text(
                                text =
                                    "Manda Ya.",

                                fontSize =
                                    22.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.White
                            )


                            Text(
                                text =
                                    "Administrador",

                                fontSize =
                                    12.sp,

                                color =
                                    Color.White.copy(
                                        alpha = 0.8f
                                    )
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
                                imageVector =
                                    Icons.Default.Menu,

                                contentDescription =
                                    "Menú",

                                tint =
                                    Color.White
                            )
                        }
                    },


                    actions = {

                        IconButton(
                            onClick = {

                                // Aquí irá la pantalla de notificaciones
                            }
                        ) {

                            BadgedBox(

                                badge = {

                                    if (
                                        notificationCount > 0
                                    ) {

                                        Badge(
                                            containerColor =
                                                Orange
                                        ) {

                                            Text(
                                                text =
                                                    notificationCount
                                                        .toString()
                                            )
                                        }
                                    }
                                }

                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Notifications,

                                    contentDescription =
                                        "Notificaciones",

                                    tint =
                                        Color.White
                                )
                            }
                        }
                    },


                    colors =
                        TopAppBarDefaults
                            .topAppBarColors(

                                containerColor =
                                    Navy
                            )
                )
            }

        ) { paddingValues ->


            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        16.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(
                        12.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {


                Text(
                    text =
                        "Panel de administración",

                    fontSize =
                        20.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Navy,

                    modifier =
                        Modifier.padding(
                            vertical = 8.dp
                        )
                )


                // Fila 1
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {


                    AdminOptionCard(
                        title =
                            "REPORTES",

                        description =
                            "Consulta estadísticas y reportes.",

                        icon =
                            Icons.Default.BarChart,

                        color =
                            Navy,

                        onClick =
                            onReportesClick,

                        modifier =
                            Modifier.weight(1f)
                    )


                    AdminOptionCard(
                        title =
                            "ENCOMIENDAS",

                        description =
                            "Gestiona los envíos registrados.",

                        icon =
                            Icons.Default.Inventory2,

                        color =
                            Orange,

                        onClick =
                            onEncomiendasClick,

                        modifier =
                            Modifier.weight(1f)
                    )
                }


                // Fila 2
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {


                    AdminOptionCard(
                        title =
                            "MOVILIDAD",

                        description =
                            "Administra vehículos y movilidad.",

                        icon =
                            Icons.Default.LocalShipping,

                        color =
                            Orange,

                        onClick =
                            onMovilidadClick,

                        modifier =
                            Modifier.weight(1f)
                    )


                    AdminOptionCard(
                        title =
                            "SEDES",

                        description =
                            "Revisa y edita las sedes.",

                        icon =
                            Icons.Default.Business,

                        color =
                            Navy,

                        onClick =
                            onSedesClick,

                        modifier =
                            Modifier.weight(1f)
                    )
                }


                // Fila 3
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {


                    AdminOptionCard(
                        title =
                            "ROLES",

                        description =
                            "Configura roles y permisos.",

                        icon =
                            Icons.Default.Groups,

                        color =
                            Navy,

                        onClick =
                            onRolesClick,

                        modifier =
                            Modifier.weight(1f)
                    )


                    AdminOptionCard(
                        title =
                            "RUTAS",

                        description =
                            "Ajusta las rutas de reparto.",

                        icon =
                            Icons.Default.Route,

                        color =
                            Orange,

                        onClick =
                            onRutasClick,

                        modifier =
                            Modifier.weight(1f)
                    )
                }


                // Fila 4: Tarifas
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {


                    AdminOptionCard(
                        title =
                            "TARIFAS",

                        description =
                            "Configura tarifas entre sedes.",

                        icon =
                            Icons.Default.AttachMoney,

                        color =
                            Navy,

                        onClick =
                            onTarifasClick,

                        modifier =
                            Modifier.weight(1f)
                    )


                    // Mantiene el mismo ancho que las demás tarjetas
                    Spacer(
                        modifier =
                            Modifier.weight(1f)
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )
            }
        }
    }
}


// Tarjeta reutilizable del panel administrativo
@Composable
private fun AdminOptionCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        onClick =
            onClick,

        modifier =
            modifier.height(
                138.dp
            ),

        shape =
            RoundedCornerShape(
                12.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Surface
            ),

        border =
            BorderStroke(
                1.dp,
                Border
            ),

        elevation =
            CardDefaults.cardElevation(
                0.dp
            )
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    14.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {


            Icon(
                imageVector =
                    icon,

                contentDescription =
                    null,

                modifier =
                    Modifier.size(
                        40.dp
                    ),

                tint =
                    color
            )


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )


            Text(
                text =
                    title,

                fontSize =
                    15.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    color,

                textAlign =
                    TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )


            Text(
                text =
                    description,

                fontSize =
                    11.sp,

                color =
                    Muted,

                textAlign =
                    TextAlign.Center,

                lineHeight =
                    14.sp
            )
        }
    }
}