package pe.edu.pe.presentation.reception.delivery

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import org.koin.compose.viewmodel.koinViewModel

import pe.edu.pe.domain.model.EncomiendaPendienteEntrega

import pe.edu.pe.presentation.theme.InputBorder
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.SurfaceWhite
import pe.edu.pe.presentation.theme.TextPrimary
import pe.edu.pe.presentation.theme.TextSecondary

import pe.edu.pe.presentation.viewmodel.EntregaRecepcionViewModel


@Composable
fun PendientesEntregaScreen(
    onBack: () -> Unit,
    viewModel:
    EntregaRecepcionViewModel =
        koinViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsState()


    val encomiendasFiltradas =
        uiState.encomiendas
            .filter {

                val busqueda =
                    uiState.busqueda
                        .trim()
                        .lowercase()


                if (
                    busqueda.isBlank()
                ) {

                    true

                } else {

                    val nombreDestinatario =
                        (
                                it.destinatario.nombres +
                                        " " +
                                        it.destinatario.apellidoPaterno +
                                        " " +
                                        it.destinatario.apellidoMaterno
                                )
                            .lowercase()


                    it.codigoRastreo
                        .lowercase()
                        .contains(
                            busqueda
                        ) ||
                            it.destinatario.dni
                                .contains(
                                    busqueda
                                ) ||
                            nombreDestinatario
                                .contains(
                                    busqueda
                                )
                }
            }


    var encomiendaSeleccionada:
            EncomiendaPendienteEntrega =
        EncomiendaPendienteEntrega(

            id =
                0,

            codigoRastreo =
                "",

            estado =
                "",

            pesoKg =
                0.0,

            altoCm =
                0.0,

            anchoCm =
                0.0,

            largoCm =
                0.0,

            observacion =
                "",

            remitente =
                pe.edu.pe.domain.model.PersonaEntrega(
                    dni = "",
                    nombres = "",
                    apellidoPaterno = "",
                    apellidoMaterno = ""
                ),

            destinatario =
                pe.edu.pe.domain.model.PersonaEntrega(
                    dni = "",
                    nombres = "",
                    apellidoPaterno = "",
                    apellidoMaterno = ""
                ),

            sedeOrigen =
                pe.edu.pe.domain.model.SedeEntrega(
                    id = 0,
                    name = ""
                ),

            sedeDestino =
                pe.edu.pe.domain.model.SedeEntrega(
                    id = 0,
                    name = ""
                ),

            ultimaSede =
                pe.edu.pe.domain.model.SedeEntrega(
                    id = 0,
                    name = ""
                )
        )


    for (
    encomienda in uiState.encomiendas
    ) {

        if (
            encomienda.id ==
            uiState.encomiendaSeleccionadaId
        ) {

            encomiendaSeleccionada =
                encomienda

            break
        }
    }


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    LightBackground
                )
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal =
                            12.dp,

                        vertical =
                            12.dp
                    ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick =
                    onBack
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored
                            .Filled
                            .ArrowBack,

                    contentDescription =
                        "Regresar",

                    tint =
                        TextPrimary
                )
            }


            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {

                Text(
                    text =
                        "Recepción",

                    fontSize =
                        13.sp,

                    color =
                        TextSecondary
                )


                Text(
                    text =
                        "Pendientes de entrega",

                    fontSize =
                        22.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        TextPrimary
                )
            }


            IconButton(
                onClick = {

                    viewModel
                        .cargarPendientes()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Refresh,

                    contentDescription =
                        "Actualizar",

                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }
        }


        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal =
                            20.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )
        ) {

            item {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        CardDefaults
                            .cardColors(
                                containerColor =
                                    MaterialTheme
                                        .colorScheme
                                        .primary
                            )
                ) {

                    Row(
                        modifier =
                            Modifier.padding(
                                20.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default
                                    .LocalShipping,

                            contentDescription =
                                null,

                            tint =
                                Color.White,

                            modifier =
                                Modifier.size(
                                    42.dp
                                )
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    16.dp
                                )
                        )


                        Column {

                            Text(
                                text =
                                    uiState.total
                                        .toString(),

                                fontSize =
                                    30.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.White
                            )


                            Text(
                                text =
                                    "Encomiendas listas para entregar",

                                fontSize =
                                    13.sp,

                                color =
                                    Color.White
                                        .copy(
                                            alpha =
                                                0.85f
                                        )
                            )
                        }
                    }
                }
            }


            item {

                OutlinedTextField(
                    value =
                        uiState.busqueda,

                    onValueChange = {

                        viewModel
                            .cambiarBusqueda(
                                it
                            )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    placeholder = {

                        Text(
                            "Código, DNI o destinatario"
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription =
                                null
                        )
                    },

                    shape =
                        RoundedCornerShape(
                            14.dp
                        ),

                    colors =
                        OutlinedTextFieldDefaults
                            .colors(
                                unfocusedBorderColor =
                                    InputBorder
                            )
                )
            }


            if (
                uiState.isLoading
            ) {

                item {

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    40.dp
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        CircularProgressIndicator()
                    }
                }

            } else if (
                encomiendasFiltradas
                    .isEmpty()
            ) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            ),

                        colors =
                            CardDefaults
                                .cardColors(
                                    containerColor =
                                        SurfaceWhite
                                )
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        32.dp
                                    ),

                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Check,

                                contentDescription =
                                    null,

                                tint =
                                    Color(
                                        0xFF2E7D32
                                    ),

                                modifier =
                                    Modifier.size(
                                        48.dp
                                    )
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        12.dp
                                    )
                            )


                            Text(
                                text =
                                    "No hay encomiendas pendientes",

                                color =
                                    TextPrimary,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Text(
                                text =
                                    "Cuando Movilidad descargue paquetes en esta sede aparecerán aquí.",

                                color =
                                    TextSecondary,

                                fontSize =
                                    13.sp,

                                textAlign =
                                    TextAlign.Center
                            )
                        }
                    }
                }

            } else {

                items(
                    items =
                        encomiendasFiltradas,

                    key = {
                        it.id
                    }
                ) { encomienda ->

                    EncomiendaPendienteCard(
                        encomienda =
                            encomienda,

                        onEntregar = {

                            viewModel
                                .abrirEntrega(
                                    encomienda.id
                                )
                        }
                    )
                }
            }


            if (
                uiState.error
                    .isNotBlank()
            ) {

                item {

                    Surface(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),

                        color =
                            Color(
                                0xFFFFEBEE
                            )
                    ) {

                        Text(
                            text =
                                uiState.error,

                            modifier =
                                Modifier.padding(
                                    14.dp
                                ),

                            color =
                                Color(
                                    0xFFC62828
                                ),

                            fontSize =
                                13.sp
                        )
                    }
                }
            }


            if (
                uiState.mensaje
                    .isNotBlank()
            ) {

                item {

                    Surface(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),

                        color =
                            Color(
                                0xFFE8F5E9
                            )
                    ) {

                        Text(
                            text =
                                uiState.mensaje,

                            modifier =
                                Modifier.padding(
                                    14.dp
                                ),

                            color =
                                Color(
                                    0xFF2E7D32
                                ),

                            fontWeight =
                                FontWeight.Medium,

                            fontSize =
                                13.sp
                        )
                    }
                }
            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            24.dp
                        )
                )
            }
        }
    }


    if (
        uiState.mostrarDialogo &&
        encomiendaSeleccionada.id >
        0
    ) {

        AlertDialog(
            onDismissRequest = {

                viewModel
                    .cerrarEntrega()
            },

            title = {

                Text(
                    text =
                        "Confirmar entrega",

                    fontWeight =
                        FontWeight.Bold
                )
            },

            text = {

                Column {

                    Text(
                        text =
                            encomiendaSeleccionada
                                .codigoRastreo,

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            16.sp
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                4.dp
                            )
                    )


                    Text(
                        text =
                            nombreCompleto(
                                encomiendaSeleccionada
                                    .destinatario
                            ),

                        color =
                            TextPrimary,

                        fontSize =
                            14.sp
                    )


                    Text(
                        text =
                            "Verifique físicamente el DNI del destinatario.",

                        color =
                            TextSecondary,

                        fontSize =
                            12.sp
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                16.dp
                            )
                    )


                    OutlinedTextField(
                        value =
                            uiState.dni,

                        onValueChange = {

                            viewModel
                                .cambiarDni(
                                    it
                                )
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {

                            Text(
                                "DNI presentado"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,

                                contentDescription =
                                    null
                            )
                        },

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            ),

                        singleLine =
                            true
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                12.dp
                            )
                    )


                    OutlinedTextField(
                        value =
                            uiState.clave,

                        onValueChange = {

                            viewModel
                                .cambiarClave(
                                    it
                                )
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        label = {

                            Text(
                                "Clave de 4 dígitos"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Lock,

                                contentDescription =
                                    null
                            )
                        },

                        visualTransformation =
                            PasswordVisualTransformation(),

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.NumberPassword
                            ),

                        singleLine =
                            true
                    )


                    if (
                        uiState.error
                            .isNotBlank()
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(
                                    10.dp
                                )
                        )


                        Text(
                            text =
                                uiState.error,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error,

                            fontSize =
                                12.sp
                        )
                    }


                    if (
                        uiState.procesando
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )


                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            CircularProgressIndicator(
                                modifier =
                                    Modifier.size(
                                        20.dp
                                    ),

                                strokeWidth =
                                    2.dp
                            )


                            Spacer(
                                modifier =
                                    Modifier.width(
                                        10.dp
                                    )
                            )


                            Text(
                                text =
                                    "Validando entrega...",

                                color =
                                    TextSecondary,

                                fontSize =
                                    12.sp
                            )
                        }
                    }
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel
                            .confirmarEntrega()
                    },

                    enabled =
                        !uiState.procesando &&
                                uiState.dni.length ==
                                8 &&
                                uiState.clave.length ==
                                4,

                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Color(
                                        0xFF2E7D32
                                    )
                            )
                ) {

                    Text(
                        text =
                            "Entregar",

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        viewModel
                            .cerrarEntrega()
                    },

                    enabled =
                        !uiState.procesando
                ) {

                    Text(
                        "Cancelar"
                    )
                }
            }
        )
    }
}


@Composable
private fun EncomiendaPendienteCard(
    encomienda:
    EncomiendaPendienteEntrega,

    onEntregar:
        () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                18.dp
            ),

        colors =
            CardDefaults
                .cardColors(
                    containerColor =
                        SurfaceWhite
                ),

        border =
            BorderStroke(
                1.dp,
                InputBorder
            )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    18.dp
                )
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    Text(
                        text =
                            encomienda
                                .codigoRastreo,

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary,

                        fontSize =
                            16.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(
                        text =
                            "Llegó a ${encomienda.sedeDestino.name}",

                        color =
                            TextSecondary,

                        fontSize =
                            12.sp
                    )
                }


                Surface(
                    shape =
                        RoundedCornerShape(
                            20.dp
                        ),

                    color =
                        Color(
                            0xFFFFF3E0
                        )
                ) {

                    Text(
                        text =
                            "POR ENTREGAR",

                        modifier =
                            Modifier.padding(
                                horizontal =
                                    10.dp,

                                vertical =
                                    5.dp
                            ),

                        color =
                            Color(
                                0xFFE65100
                            ),

                        fontSize =
                            10.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            HorizontalDivider(
                modifier =
                    Modifier.padding(
                        vertical =
                            14.dp
                    ),

                color =
                    InputBorder
            )


            Text(
                text =
                    "Destinatario",

                color =
                    TextSecondary,

                fontSize =
                    11.sp
            )


            Text(
                text =
                    nombreCompleto(
                        encomienda
                            .destinatario
                    ),

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    15.sp
            )


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )


            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text =
                            "Origen",

                        color =
                            TextSecondary,

                        fontSize =
                            11.sp
                    )


                    Text(
                        text =
                            encomienda
                                .sedeOrigen
                                .name,

                        color =
                            TextPrimary,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }


                Column(
                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(
                        text =
                            "Peso",

                        color =
                            TextSecondary,

                        fontSize =
                            11.sp
                    )


                    Text(
                        text =
                            "${encomienda.pesoKg} kg",

                        color =
                            TextPrimary,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )


            Button(
                onClick =
                    onEntregar,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            50.dp
                        ),

                shape =
                    RoundedCornerShape(
                        12.dp
                    ),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .secondary
                        )
            ) {

                Text(
                    text =
                        "Validar y entregar",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}


private fun nombreCompleto(
    persona:
    pe.edu.pe.domain.model.PersonaEntrega
): String {

    return (
            persona.nombres +
                    " " +
                    persona.apellidoPaterno +
                    " " +
                    persona.apellidoMaterno
            )
        .trim()
}