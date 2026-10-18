package pe.edu.pe.presentation.reception

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.data.model.Shipment
import pe.edu.pe.presentation.theme.*

@Composable
fun ReceptionScreen(
    onRegisterShipment: () -> Unit,
    onDeliverWarehouse: () -> Unit,
    onHistory: () -> Unit,
    onProfile: () -> Unit,
    onLogout: () -> Unit,
    shipments: List<Shipment>
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
            .padding(
                start = 20.dp,
                top = 16.dp,
                end = 20.dp,
                bottom = 20.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Recepción",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Gestiona las encomiendas de la sede",
                    fontSize = 14.sp,
                    color = Muted
                )
            }

            // El Box ancla el menú desplegable al botón
            Box {
                IconButton(
                    onClick = { menuExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Menú",
                        tint = Ink
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Perfil") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = Ink
                            )
                        },
                        onClick = {
                            menuExpanded = false
                            onProfile()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = null,
                                tint = Ink
                            )
                        },
                        onClick = {
                            menuExpanded = false
                            onLogout()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Encomiendas registradas: ${shipments.size}",
            fontSize = 13.sp,
            color = Muted
        )

        Spacer(modifier = Modifier.height(24.dp))

        ReceptionActionCard(
            title = "Registrar envío",
            description = "Registrar una nueva encomienda",
            icon = Icons.Outlined.Inventory2,
            iconBackground = OrangeSoft,
            iconColor = Orange,
            onClick = onRegisterShipment
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReceptionActionCard(
            title = "Entregar a almacén",
            description = "Entrega una encomienda registrada",
            icon = Icons.Outlined.LocalShipping,
            iconBackground = NavySoft,
            iconColor = Navy,
            onClick = onDeliverWarehouse
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReceptionActionCard(
            title = "Historial",
            description = "Consulta los envíos registrados",
            icon = Icons.Outlined.History,
            iconBackground = NavySoft,
            iconColor = Navy,
            onClick = onHistory
        )
    }
}

@Composable
private fun ReceptionActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconBackground: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        border = BorderStroke(
            width = 1.dp,
            color = Border
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        color = iconBackground,
                        shape = RoundedCornerShape(11.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.size(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = description,
                    fontSize = 12.5.sp,
                    color = Muted
                )
            }

            Text(
                text = "›",
                fontSize = 28.sp,
                color = Muted
            )
        }
    }
}