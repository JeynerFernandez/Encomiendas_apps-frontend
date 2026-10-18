package pe.edu.pe.presentation.movilidad

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.SurfaceWhite
import pe.edu.pe.presentation.theme.TextPrimary
import pe.edu.pe.presentation.theme.TextSecondary
import pe.edu.pe.presentation.theme.InputBorder
import pe.edu.pe.data.model.Shipment
import pe.edu.pe.data.repository.ShipmentRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovilidadScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = LightBackground,
        topBar = {
            TopAppBar(
                title = { Text("Movilidad", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightBackground
                )
            )
        },
        bottomBar = {
            MovilidadBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        if (selectedTab == 0) {
            MiRutaScreenContent(paddingValues)
        } else if (selectedTab == 1) {
            BajarScreenContent(paddingValues)
        } else {
            ReporteScreenContent(paddingValues)
        }
    }
}

@Composable
fun MiRutaScreenContent(
    paddingValues: PaddingValues
) {

    val placaMovilidad = "ABC-731"

    val encomiendasMovilidad =
        ShipmentRepository.obtenerPorMovilidad(
            placaMovilidad
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Unidad $placaMovilidad",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Ruta asignada",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            RutaCard()
        }

        item {

            Text(
                text =
                    "Encomiendas en esta unidad (${encomiendasMovilidad.size})",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (encomiendasMovilidad.isEmpty()) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceWhite
                    ),
                    border = BorderStroke(
                        1.dp,
                        InputBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text =
                                "No hay encomiendas asignadas a esta movilidad.",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {

                EncomiendasMovilidadCard(
                    encomiendas = encomiendasMovilidad
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

@Composable
fun RutaCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Tarapoto → Moyobamba → Rioja → Nueva Cajamarca",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            StepperRow()
        }
    }
}

@Composable
fun StepperRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StepItem(
            text = "Tarapoto",
            isCompleted = true,
            isCurrent = false,
            number = "1"
        )
        StepLine(isCompleted = true, modifier = Modifier.weight(1f))
        StepItem(
            text = "Moyobamba",
            isCompleted = false,
            isCurrent = true,
            number = "2"
        )
        StepLine(isCompleted = false, modifier = Modifier.weight(1f))
        StepItem(
            text = "Rioja",
            isCompleted = false,
            isCurrent = false,
            number = "3"
        )
        StepLine(isCompleted = false, modifier = Modifier.weight(1f))
        StepItem(
            text = "Nueva\nCajamarca",
            isCompleted = false,
            isCurrent = false,
            number = "4"
        )
    }
}

@Composable
fun StepItem(text: String, isCompleted: Boolean, isCurrent: Boolean, number: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> MaterialTheme.colorScheme.secondary
                        isCurrent -> MaterialTheme.colorScheme.primary
                        else -> InputBorder
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completado",
                    tint = SurfaceWhite,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = number,
                    color = if (isCurrent) SurfaceWhite else TextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = if (isCompleted || isCurrent) TextPrimary else TextSecondary,
            fontSize = 12.sp,
            fontWeight = if (isCompleted || isCurrent) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun StepLine(isCompleted: Boolean, modifier: Modifier) {
    Box(
        modifier = modifier
            .height(2.dp)
            .background(if (isCompleted) MaterialTheme.colorScheme.secondary else InputBorder)
            .padding(horizontal = 4.dp)
            .offset(y = (-16).dp)
    )
}

@Composable
fun EncomiendasCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            EncomiendaItem(
                codigo = "RN-48213",
                destino = "Moyobamba",
                peso = "5.0 kg"
            )
            HorizontalDivider(color = InputBorder, modifier = Modifier.padding(vertical = 16.dp))
            EncomiendaItem(
                codigo = "RN-48220",
                destino = "Moyobamba",
                peso = "2.4 kg"
            )
        }
    }
}

@Composable
fun EncomiendaItem(codigo: String, destino: String, peso: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = "Encomienda",
                tint = TextSecondary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = codigo,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "→ $destino • $peso",
                color = TextSecondary,
                fontSize = 14.sp
            )
        }
        
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFF3E0)) // Light orange
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "En tránsito",
                color = Color(0xFFE65100), // Dark orange
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MovilidadBottomNav(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceWhite,
        contentColor = TextSecondary,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Mi ruta"
                )
            },
            label = { Text("Mi ruta", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.secondary,
                selectedTextColor = MaterialTheme.colorScheme.secondary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Bajar"
                )
            },
            label = { Text("Bajar", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.secondary,
                selectedTextColor = MaterialTheme.colorScheme.secondary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Reporte"
                )
            },
            label = { Text("Reporte", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.secondary,
                selectedTextColor = MaterialTheme.colorScheme.secondary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
    }
}
@Composable
fun EncomiendasMovilidadCard(
    encomiendas: List<Shipment>
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        ),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            encomiendas.forEachIndexed {
                    index,
                    shipment ->

                ShipmentMovilidadItem(
                    shipment = shipment
                )

                if (index < encomiendas.lastIndex) {

                    HorizontalDivider(
                        color = InputBorder,
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                }
            }
        }
    }
}
@Composable
fun ShipmentMovilidadItem(
    shipment: Shipment
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(LightBackground),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector =
                    Icons.Default.LocalShipping,
                contentDescription = "Encomienda",
                tint = TextSecondary
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = shipment.trackingCode,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text =
                    "→ ${shipment.destinationBranch} • ${shipment.weight} kg",
                color = TextSecondary,
                fontSize = 14.sp
            )

            shipment.driver?.let {

                Text(
                    text = "Conductor: $it",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    Color(0xFFFFF3E0)
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
        ) {

            Text(
                text = "En tránsito",
                color = Color(0xFFE65100),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}