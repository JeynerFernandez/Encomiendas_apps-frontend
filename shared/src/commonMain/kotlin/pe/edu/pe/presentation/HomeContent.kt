package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pe.edu.pe.presentation.theme.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeContent(
    isLoggedIn: Boolean,
    onLogout: ()-> Unit,
    onEditProfile: ()-> Unit,
    onCartClick: ()-> Unit,
    onCalcularEncomienda: ()-> Unit,
    onSupportClick: ()-> Unit = {},
    onTrackingClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    cartSize: Int
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                        .background(Navy),
                    contentAlignment = Alignment.Center
                ){
                    if (isLoggedIn){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(contentAlignment = Alignment.BottomEnd){
                                Icon(imageVector = Icons.Default.Person, contentDescription = null,
                                    modifier = Modifier.size(90.dp),
                                    tint = Color.White)
                                Surface(
                                    shape = CircleShape,
                                    color = Orange,
                                    modifier = Modifier.size(28.dp).offset(x = (-4).dp, y = (-4).dp)
                                ) {
                                    IconButton(onClick = onEditProfile){
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier.size(13.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                            ScaledText("Jeyner Fernandez", fontSize = 24f, fontWeight = FontWeight.Bold, color = Color.White)
                            ScaledText("fernandezjahir202@gmail.com", fontSize = 14f, color = Color.White.copy(alpha = 0.7f))
                        }
                    }
                    else{
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.AccountCircle, null,
                                modifier = Modifier.size(80.dp), tint = Color.White.copy(alpha = 0.5f))
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = {scope.launch { drawerState.close() }},
                                colors = ButtonDefaults.buttonColors(containerColor = Orange)
                            ){
                                ScaledText("Iniciar sesión", fontSize = 14f, color = Color.White)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                if (isLoggedIn){
                    NavigationDrawerItem(
                        label = { ScaledText("Mi perfil", fontSize = 14f) },
                        selected = false,
                        onClick = {scope.launch { drawerState.close() }},
                        icon = {Icon(Icons.Default.Person, null)}
                    )
                    NavigationDrawerItem(
                        label = { ScaledText("CERRAR SESION", fontSize = 14f) },
                        selected = false,
                        onClick = {scope.launch { drawerState.close() }
                            onLogout()},
                        icon = {Icon(Icons.AutoMirrored.Filled.ExitToApp, null)}
                    )
                }
            }
        }
    ){
        Scaffold(
            containerColor = LightBackground,
            topBar = {
                TopAppBar(
                    title = {
                        ScaledText(
                            "Manda Ya.",
                            fontSize = 20f,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {scope.launch { drawerState.open() }}){
                            Icon(Icons.Default.Menu, null, tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = onCartClick){
                            BadgedBox(badge ={
                                if (cartSize>0){
                                    Badge(containerColor = Orange) { Text("$cartSize") }
                                }
                            }){
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Navy
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Surface,
                    contentColor = Navy
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {Icon(Icons.Default.Home, null)},
                        label = { ScaledText("INICIO", fontSize = 12f) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Navy,
                            selectedTextColor = Navy,
                            indicatorColor = NavySoft,
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {Icon(Icons.Default.CalendarMonth, null)},
                        label = { ScaledText("RESERVAS", fontSize = 12f) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Navy,
                            selectedTextColor = Navy,
                            indicatorColor = NavySoft,
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScaledText(
                    "Reservas App Manda ya",
                    fontSize = 20f,
                    fontWeight = FontWeight.Bold,
                    color = Ink,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Card(
                    onClick = onCalcularEncomienda,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(NavySoft, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(32.dp), tint = Navy)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ScaledText("CALCULAR ENCOMIENDA", fontSize = 16f, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(modifier = Modifier.height(4.dp))
                        ScaledText("Estima el costo y tiempo de tu envío.", fontSize = 12f, color = Muted, textAlign = TextAlign.Center)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable{ onTrackingClick() },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Orange.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(32.dp), tint = Orange)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ScaledText("RASTREAR PEDIDO", fontSize = 16f, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(modifier = Modifier.height(4.dp))
                        ScaledText("Sigue el estado de tus envíos actuales.", fontSize = 12f, color = Muted, textAlign = TextAlign.Center)
                    }
                }

                Card(
                    onClick = onSupportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(NavySoft, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Support, contentDescription = null, modifier = Modifier.size(32.dp), tint = Navy)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ScaledText("SOPORTE", fontSize = 16f, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(modifier = Modifier.height(4.dp))
                        ScaledText("¿Necesitas ayuda? Contáctanos", fontSize = 12f, color = Muted, textAlign = TextAlign.Center)
                    }
                }

                Card(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Orange.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(32.dp), tint = Orange)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ScaledText("AJUSTES", fontSize = 16f, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(modifier = Modifier.height(4.dp))
                        ScaledText("Cambia el tema y el tamaño de texto", fontSize = 12f, color = Muted, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}