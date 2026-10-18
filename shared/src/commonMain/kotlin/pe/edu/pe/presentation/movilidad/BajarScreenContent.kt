    package pe.edu.pe.presentation.movilidad

    import androidx.compose.foundation.BorderStroke
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import pe.edu.pe.presentation.theme.InputBorder
    import pe.edu.pe.presentation.theme.SurfaceWhite
    import pe.edu.pe.presentation.theme.TextPrimary
    import pe.edu.pe.presentation.theme.TextSecondary

    @Composable
    fun BajarScreenContent(paddingValues: PaddingValues) {

        var searchQuery by remember {
            mutableStateOf("")
        }

        var selectedSede by remember {
            mutableStateOf<String?>(null)
        }

        var encomiendas by remember {
            mutableStateOf(encomiendasSimuladas.toList())
        }

        var mensaje by remember {
            mutableStateOf<String?>(null)
        }

        val sedes = listOf(
            "Tarapoto",
            "Moyobamba",
            "Rioja",
            "Nueva Cajamarca",
            "Bagua Grande"
        )

        val sedesFiltradas = sedes.filter {
            it.contains(searchQuery, ignoreCase = true)
        }

        val encomiendasDeLaSede =
            if (selectedSede != null) {
                encomiendas.filter {
                    it.destino.equals(
                        selectedSede,
                        ignoreCase = true
                    )
                }
            } else {
                emptyList()
            }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "Descarga por sede",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "¿Qué baja en cada sede?",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            item {

                Text(
                    text = "Selecciona la sede",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    placeholder = {
                        Text("Buscar sede...")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = InputBorder,
                        focusedBorderColor =
                            MaterialTheme.colorScheme.primary
                    )
                )
            }

            item {

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {

                    items(sedesFiltradas) { sede ->

                        val isSelected =
                            sede == selectedSede

                        Surface(
                            shape =
                                RoundedCornerShape(16.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    InputBorder
                                }
                            ),
                            color =
                                if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                        .copy(alpha = 0.1f)
                                } else {
                                    SurfaceWhite
                                },
                            modifier = Modifier.clickable {

                                selectedSede =
                                    if (isSelected) {
                                        null
                                    } else {
                                        sede
                                    }

                                mensaje = null
                            }
                        ) {

                            Text(
                                text = sede,
                                color =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        TextPrimary
                                    },
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceWhite
                    ),
                    border = BorderStroke(
                        1.dp,
                        InputBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {

                    if (selectedSede == null) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "Selecciona una sede para ver las encomiendas que bajan ahí.",
                                color = TextSecondary,
                                fontSize = 14.sp,
                                textAlign =
                                    TextAlign.Center
                            )
                        }

                    } else if (
                        encomiendasDeLaSede.isEmpty()
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "No hay encomiendas para descargar en $selectedSede.",
                                color = TextSecondary,
                                fontSize = 14.sp,
                                textAlign =
                                    TextAlign.Center
                            )
                        }

                    } else {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text =
                                    "Encomiendas para $selectedSede",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            encomiendasDeLaSede.forEach {
                                    encomienda ->

                                EncomiendaDescargaItem(
                                    encomienda = encomienda
                                )
                            }
                        }
                    }
                }
            }

            mensaje?.let { texto ->

                item {

                    Text(
                        text = texto,
                        color =
                            MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight =
                            FontWeight.Medium,
                        textAlign =
                            TextAlign.Center,
                        modifier =
                            Modifier.fillMaxWidth()
                    )
                }
            }

            item {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {

                        val sedeActual =
                            selectedSede ?: return@Button

                        encomiendas =
                            encomiendas.map {
                                    encomienda ->

                                if (
                                    encomienda.destino
                                        .equals(
                                            sedeActual,
                                            ignoreCase = true
                                        )
                                ) {

                                    encomienda.copy(
                                        estado =
                                            "En destino",
                                        ubicacionActual =
                                            sedeActual
                                    )

                                } else if (
                                    encomienda.estado !=
                                    "En destino"
                                ) {

                                    encomienda.copy(
                                        estado =
                                            "En tránsito",
                                        ubicacionActual =
                                            sedeActual
                                    )

                                } else {

                                    encomienda
                                }
                            }

                        mensaje =
                            "Estado actualizado en $sedeActual"
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    enabled =
                        selectedSede != null,

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.secondary,
                            disabledContainerColor =
                                Color(0xFFF3B79C)
                        )
                ) {

                    Text(
                        text = "Actualizar estado",
                        color = SurfaceWhite,
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
    @Composable
    fun EncomiendaDescargaItem(
        encomienda: Encomienda
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = encomienda.codigo,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Destino: ${encomienda.destino}",
                    color = TextSecondary,
                    fontSize = 13.sp
                )

                Text(
                    text = "Peso: ${encomienda.peso}",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }

            Text(
                text = encomienda.estado,
                color =
                    if (
                        encomienda.estado ==
                        "En destino"
                    ) {
                        Color(0xFF2E7D32)
                    } else {
                        Color(0xFFE65100)
                    },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }