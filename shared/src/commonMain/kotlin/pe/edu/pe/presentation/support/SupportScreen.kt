package pe.edu.pe.presentation.support

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pe.edu.pe.presentation.theme.*

data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
fun SupportScreen(
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var subjectText by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    val faqs = remember {
        listOf(
            FaqItem(
                question = "¿Cómo puedo rastrear su paquete?",
                answer = "Ingresa a la opción 'Rastrear Pedido' en el menú principal y digita tu número de guía de envío para ver el estado en tiempo real."
            ),
            FaqItem(
                question = "¿Cuáles son los métodos de pago aceptados?",
                answer = "Aceptamos pagos con tarjeta de crédito/débito (Visa, Mastercard), Yape, Plin y pago en efectivo al entregar la encomienda."
            ),
            FaqItem(
                question = "¿Cuánto tiempo tarda en llegar un envío?",
                answer = "Los envíos locales suelen entregarse en 24 horas hábiles. Para envíos nacionales, el tiempo estimado es de 2 a 4 días hábiles."
            ),
            FaqItem(
                question = "¿Qué hago si mi encomienda no llega a tiempo?",
                answer = "Puedes comunicarte directamente por WhatsApp o llamada con nuestro equipo de soporte, o enviarnos un mensaje mediante el formulario de abajo."
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Navy,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                ScaledText(
                    text = "Soporte y Ayuda",
                    fontSize = 25f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Border)
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(NavySoft, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Support,
                                contentDescription = null,
                                tint = Navy,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            ScaledText(
                                text = "¿En qué podemos ayudarte?",
                                fontSize = 16f,
                                fontWeight = FontWeight.Bold,
                                color = Ink
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            ScaledText(
                                text = "Estamos listos para resolver tus dudas e inconvenientes.",
                                fontSize = 12f,
                                color = Muted
                            )
                        }
                    }
                }

                ScaledText(
                    text = "Canales de Atención Directa",
                    fontSize = 16f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ContactChannelCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.AutoMirrored.Filled.Chat,
                        title = "WhatsApp",
                        subtitle = "Chat en vivo",
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Abriendo chat de WhatsApp...")
                            }
                        }
                    )
                    ContactChannelCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Phone,
                        title = "Llamar",
                        subtitle = "Atención 24/7",
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Iniciando llamada de atención al cliente...")
                            }
                        }
                    )
                    ContactChannelCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Email,
                        title = "Correo",
                        subtitle = "Respuesta en 24h",
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Redirigiendo a correo de soporte...")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ScaledText(
                    text = "Preguntas Frecuentes",
                    fontSize = 16f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                faqs.forEach { faq ->
                    FaqCard(faqItem = faq)
                }

                Spacer(modifier = Modifier.height(8.dp))

                ScaledText(
                    text = "Envíanos un mensaje",
                    fontSize = 16f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = CardDefaults.cardColors(containerColor = Surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (isSubmitted) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF279B57),
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    ScaledText(
                                        text = "¡Mensaje Enviado!",
                                        fontWeight = FontWeight.Bold,
                                        color = Ink,
                                        fontSize = 15f
                                    )
                                    ScaledText(
                                        text = "Nos pondremos en contacto contigo a la brevedad.",
                                        fontSize = 12f,
                                        color = Muted
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    isSubmitted = false
                                    subjectText = ""
                                    messageText = ""
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Navy,
                                    contentColor = Color.White
                                )
                            ) {
                                ScaledText("Enviar otro mensaje", fontWeight = FontWeight.Bold, fontSize = 14f)
                            }
                        } else {
                            OutlinedTextField(
                                value = subjectText,
                                onValueChange = { subjectText = it },
                                label = { ScaledText("Asunto", fontSize = 14f) },
                                placeholder = { ScaledText("Ej: Retraso en mi pedido #1234", color = Muted, fontSize = 14f) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Navy,
                                    unfocusedBorderColor = Border,
                                    focusedTextColor = Ink,
                                    unfocusedTextColor = Ink
                                )
                            )

                            OutlinedTextField(
                                value = messageText,
                                onValueChange = { messageText = it },
                                label = { ScaledText("Detalle de tu consulta", fontSize = 14f) },
                                placeholder = { ScaledText("Escribe aquí tu duda o problema...", color = Muted, fontSize = 14f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Navy,
                                    unfocusedBorderColor = Border,
                                    focusedTextColor = Ink,
                                    unfocusedTextColor = Ink
                                )
                            )

                            Button(
                                onClick = {
                                    if (subjectText.isNotBlank() && messageText.isNotBlank()) {
                                        isSubmitted = true
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Por favor, completa todos los campos.")
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Navy,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                ScaledText("Enviar Consulta", fontWeight = FontWeight.Bold, fontSize = 14f)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun ContactChannelCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(NavySoft, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Navy,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ScaledText(
                text = title,
                fontSize = 13f,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
            Spacer(modifier = Modifier.height(2.dp))
            ScaledText(
                text = subtitle,
                fontSize = 10f,
                color = Muted,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun FaqCard(faqItem: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(NavySoft, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = null,
                            tint = Navy,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    ScaledText(
                        text = faqItem.question,
                        fontSize = 14f,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Contraer" else "Expandir",
                    tint = Muted
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    ScaledText(
                        text = faqItem.answer,
                        fontSize = 13f,
                        color = Muted,
                        lineHeight = 18f,
                        modifier = Modifier.padding(start = 42.dp)
                    )
                }
            }
        }
    }
}