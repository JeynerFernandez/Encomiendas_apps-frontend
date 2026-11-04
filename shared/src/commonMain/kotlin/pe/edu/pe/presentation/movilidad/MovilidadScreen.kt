package pe.edu.pe.presentation.movilidad

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.pe.domain.model.DetalleEncomiendaMovilidad
import pe.edu.pe.domain.model.TrasladoMovilidad
import pe.edu.pe.presentation.theme.InputBorder
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.SurfaceWhite
import pe.edu.pe.presentation.theme.TextPrimary
import pe.edu.pe.presentation.theme.TextSecondary
import pe.edu.pe.presentation.viewmodel.MovilidadUiState
import pe.edu.pe.presentation.viewmodel.MovilidadViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovilidadScreen(
    onBackClick: () -> Unit = {},
    viewModel: MovilidadViewModel = koinViewModel()
) {

    val uiState by
    viewModel.uiState.collectAsState()

    Scaffold(
        containerColor =
            LightBackground,

        topBar = {

            TopAppBar(
                title = {

                    Column {

                        Text(
                            text =
                                "Movilidad",

                            color =
                                TextPrimary,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize =
                                18.sp
                        )

                        if (
                            uiState.conductorNombre
                                .isNotBlank()
                        ) {

                            Text(
                                text =
                                    uiState.conductorNombre,

                                color =
                                    TextSecondary,

                                fontSize =
                                    12.sp
                            )
                        }
                    }
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onBackClick
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
                },

                actions = {

                    IconButton(
                        onClick = {

                            viewModel
                                .cargarTraslados()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Refresh,

                            contentDescription =
                                "Actualizar",

                            tint =
                                TextPrimary
                        )
                    }
                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(
                            containerColor =
                                LightBackground
                        )
            )
        },

        bottomBar = {

            MovilidadBottomNav(
                selectedTab =
                    uiState.selectedTab,

                onTabSelected = {

                    viewModel
                        .cambiarTab(
                            it
                        )
                }
            )
        }
    ) { paddingValues ->

        if (
            uiState.selectedTab ==
            0
        ) {

            MiRutaScreenContent(
                paddingValues =
                    paddingValues,

                uiState =
                    uiState,

                viewModel =
                    viewModel
            )

        } else if (
            uiState.selectedTab ==
            1
        ) {

            BajarScreenContent(
                paddingValues =
                    paddingValues,

                uiState =
                    uiState,

                viewModel =
                    viewModel
            )

        } else {

            ReporteScreenContent(
                paddingValues =
                    paddingValues,

                uiState =
                    uiState,

                viewModel =
                    viewModel
            )
        }
    }
}


@Composable
fun MiRutaScreenContent(
    paddingValues: PaddingValues,
    uiState: MovilidadUiState,
    viewModel: MovilidadViewModel
) {

    val traslado =
        uiState.traslados.find {

            it.id ==
                    uiState
                        .trasladoSeleccionadoId
        }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
                .padding(
                    horizontal =
                        20.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                16.dp
            )
    ) {

        item {

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Text(
                text =
                    "Mis traslados",

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Text(
                text =
                    "Ruta asignada",

                color =
                    MaterialTheme
                        .colorScheme
                        .secondary,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
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
            uiState.traslados
                .isEmpty()
        ) {

            item {

                EstadoVacio(
                    texto =
                        "No tienes traslados asignados"
                )
            }

        } else {

            item {

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    items(
                        items =
                            uiState.traslados,

                        key = {
                            it.id
                        }
                    ) { item ->

                        TrasladoSelector(
                            traslado =
                                item,

                            seleccionado =
                                item.id ==
                                        uiState
                                            .trasladoSeleccionadoId,

                            onClick = {

                                viewModel
                                    .seleccionarTraslado(
                                        item.id
                                    )
                            }
                        )
                    }
                }
            }

            if (
                traslado != null
            ) {

                item {

                    ResumenTrasladoCard(
                        traslado =
                            traslado
                    )
                }

                item {

                    RutaCard(
                        traslado =
                            traslado
                    )
                }

                item {

                    Text(
                        text =
                            "Encomiendas (${traslado.encomiendas.size})",

                        color =
                            TextPrimary,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            17.sp
                    )
                }

                if (
                    traslado.encomiendas
                        .isEmpty()
                ) {

                    item {

                        EstadoVacio(
                            texto =
                                "Este traslado no tiene encomiendas"
                        )
                    }

                } else {

                    items(
                        items =
                            traslado.encomiendas,

                        key = {
                            it.detalleId
                        }
                    ) { detalle ->

                        EncomiendaMovilidadCard(
                            detalle =
                                detalle
                        )
                    }
                }

                item {

                    AccionesTraslado(
                        traslado =
                            traslado,

                        uiState =
                            uiState,

                        onIniciar = {

                            viewModel
                                .iniciarTraslado()
                        },

                        onFinalizar = {

                            viewModel
                                .finalizarTraslado()
                        },

                        onRetornar = {

                            viewModel
                                .retornarASedeCentral()
                        }
                    )
                }
            }
        }

        item {

            MensajesMovilidad(
                uiState =
                    uiState
            )

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )
        }
    }
}


@Composable
fun AccionesTraslado(
    traslado: TrasladoMovilidad,
    uiState: MovilidadUiState,
    onIniciar: () -> Unit,
    onFinalizar: () -> Unit,
    onRetornar: () -> Unit
) {

    val estado =
        traslado.estado
            .trim()
            .uppercase()

    if (
        estado ==
        "PROGRAMADO"
    ) {

        Button(
            onClick =
                onIniciar,

            enabled =
                !uiState.procesando &&
                        traslado.encomiendas
                            .isNotEmpty(),

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        56.dp
                    ),

            shape =
                RoundedCornerShape(
                    14.dp
                )
        ) {

            if (
                uiState.procesando
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            22.dp
                        ),

                    strokeWidth =
                        2.dp,

                    color =
                        Color.White
                )

            } else {

                Icon(
                    imageVector =
                        Icons.Default.LocalShipping,

                    contentDescription =
                        "Iniciar"
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            8.dp
                        )
                )

                Text(
                    text =
                        "Iniciar traslado",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

    } else if (
        estado ==
        "EN_CURSO"
    ) {

        Button(
            onClick =
                onFinalizar,

            enabled =
                !uiState.procesando &&
                        traslado.resumen
                            .pendientesDescarga ==
                        0,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        56.dp
                    ),

            shape =
                RoundedCornerShape(
                    14.dp
                ),

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Color(
                                0xFF2E7D32
                            )
                    )
        ) {

            if (
                uiState.procesando
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            22.dp
                        ),

                    strokeWidth =
                        2.dp,

                    color =
                        Color.White
                )

            } else {

                Icon(
                    imageVector =
                        Icons.Default.Check,

                    contentDescription =
                        "Finalizar",

                    tint =
                        Color.White
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            8.dp
                        )
                )

                Text(
                    text =
                        if (
                            traslado.resumen
                                .pendientesDescarga ==
                            0
                        ) {

                            "Finalizar traslado"

                        } else {

                            "Faltan ${traslado.resumen.pendientesDescarga} encomiendas"
                        },

                    color =
                        Color.White,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

    } else if (
        estado ==
        "FINALIZADO"
    ) {

        val existeTrasladoActivo =
            existeNuevoTrasladoActivo(
                trasladoActual =
                    traslado,

                traslados =
                    uiState.traslados
            )

        if (
            !existeTrasladoActivo
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                border =
                    BorderStroke(
                        1.dp,
                        Color(
                            0xFF1565C0
                        )
                    ),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Color(
                                    0xFFE3F2FD
                                )
                        )
            ) {

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                18.dp
                            ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Home,

                            contentDescription =
                                "Sede central",

                            tint =
                                Color(
                                    0xFF1565C0
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    10.dp
                                )
                        )

                        Column {

                            Text(
                                text =
                                    "Ruta finalizada",

                                color =
                                    TextPrimary,

                                fontSize =
                                    16.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    "Confirma tu retorno a la sede central para volver a estar disponible.",

                                color =
                                    TextSecondary,

                                fontSize =
                                    13.sp
                            )
                        }
                    }

                    Button(
                        onClick =
                            onRetornar,

                        enabled =
                            !uiState.procesando,

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    56.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                14.dp
                            ),

                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Color(
                                            0xFF0F2A46
                                        )
                                )
                    ) {

                        if (
                            uiState.procesando
                        ) {

                            CircularProgressIndicator(
                                modifier =
                                    Modifier.size(
                                        22.dp
                                    ),

                                strokeWidth =
                                    2.dp,

                                color =
                                    Color.White
                            )

                        } else {

                            Icon(
                                imageVector =
                                    Icons.Default.Home,

                                contentDescription =
                                    "Retornar",

                                tint =
                                    Color.White
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(
                                        8.dp
                                    )
                            )

                            Text(
                                text =
                                    "Retornar a sede central",

                                color =
                                    Color.White,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }

        } else {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Color(
                                    0xFFE8F5E9
                                )
                        )
            ) {

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                16.dp
                            ),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Check,

                        contentDescription =
                            "Disponible",

                        tint =
                            Color(
                                0xFF2E7D32
                            )
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                10.dp
                            )
                    )

                    Column {

                        Text(
                            text =
                                "Retorno registrado",

                            color =
                                Color(
                                    0xFF2E7D32
                                ),

                            fontWeight =
                                FontWeight.Bold
                        )

                        Text(
                            text =
                                "La movilidad está disponible para un nuevo traslado.",

                            color =
                                TextSecondary,

                            fontSize =
                                12.sp
                        )
                    }
                }
            }
        }

    } else if (
        estado ==
        "CANCELADO"
    ) {

        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    16.dp
                ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Color(
                                0xFFFFEBEE
                            )
                    )
        ) {

            Text(
                text =
                    "Este traslado fue cancelado.",

                modifier =
                    Modifier.padding(
                        16.dp
                    ),

                color =
                    Color(
                        0xFFC62828
                    ),

                fontWeight =
                    FontWeight.Medium
            )
        }
    }
}


fun existeNuevoTrasladoActivo(
    trasladoActual: TrasladoMovilidad,
    traslados: List<TrasladoMovilidad>
): Boolean {

    for (
    traslado in traslados
    ) {

        if (
            traslado.id !=
            trasladoActual.id &&
            traslado.vehiculo.id ==
            trasladoActual.vehiculo.id &&
            traslado.conductor.id ==
            trasladoActual.conductor.id
        ) {

            val estado =
                traslado.estado
                    .trim()
                    .uppercase()

            if (
                estado ==
                "PROGRAMADO" ||
                estado ==
                "EN_CURSO"
            ) {

                return true
            }
        }
    }

    return false
}


@Composable
fun BajarScreenContent(
    paddingValues: PaddingValues,
    uiState: MovilidadUiState,
    viewModel: MovilidadViewModel
) {

    val traslado =
        uiState.traslados.find {

            it.id ==
                    uiState
                        .trasladoSeleccionadoId
        }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
                .padding(
                    horizontal =
                        20.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                16.dp
            )
    ) {

        item {

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Text(
                text =
                    "Descarga por sede",

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Text(
                text =
                    "Encomiendas por destino",

                color =
                    MaterialTheme
                        .colorScheme
                        .secondary,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        if (
            traslado == null
        ) {

            item {

                EstadoVacio(
                    texto =
                        "No existe un traslado seleccionado"
                )
            }

        } else {

            item {

                Card(
                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    colors =
                        CardDefaults
                            .cardColors(
                                containerColor =
                                    SurfaceWhite
                            ),

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier =
                            Modifier.padding(
                                18.dp
                            )
                    ) {

                        Text(
                            text =
                                "Traslado #${traslado.id}",

                            color =
                                TextPrimary,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize =
                                17.sp
                        )

                        Text(
                            text =
                                "${traslado.vehiculo.placa} • ${traslado.ruta.name}",

                            color =
                                TextSecondary,

                            fontSize =
                                13.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        EstadoChip(
                            estado =
                                traslado.estado
                        )
                    }
                }
            }

            item {

                OutlinedTextField(
                    value =
                        uiState.busquedaSede,

                    onValueChange = {

                        viewModel
                            .cambiarBusquedaSede(
                                it
                            )
                    },

                    placeholder = {

                        Text(
                            text =
                                "Buscar sede"
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

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

            item {

                val sedesFiltradas =
                    traslado.ruta
                        .recorrido
                        .filter {

                            it.sede.name
                                .contains(
                                    uiState
                                        .busquedaSede,

                                    ignoreCase =
                                        true
                                )
                        }

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        )
                ) {

                    items(
                        items =
                            sedesFiltradas,

                        key = {
                            it.sede.id
                        }
                    ) { item ->

                        val seleccionado =
                            item.sede.id ==
                                    uiState
                                        .sedeSeleccionadaId

                        Surface(
                            shape =
                                RoundedCornerShape(
                                    18.dp
                                ),

                            border =
                                BorderStroke(
                                    1.dp,

                                    if (
                                        seleccionado
                                    ) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        InputBorder
                                    }
                                ),

                            color =
                                if (
                                    seleccionado
                                ) {

                                    MaterialTheme
                                        .colorScheme
                                        .primary
                                        .copy(
                                            alpha =
                                                0.1f
                                        )

                                } else {

                                    SurfaceWhite
                                },

                            modifier =
                                Modifier.clickable {

                                    viewModel
                                        .seleccionarSede(
                                            item.sede.id
                                        )
                                }
                        ) {

                            Text(
                                text =
                                    item.sede.name,

                                modifier =
                                    Modifier.padding(
                                        horizontal =
                                            16.dp,

                                        vertical =
                                            9.dp
                                    ),

                                color =
                                    if (
                                        seleccionado
                                    ) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        TextPrimary
                                    },

                                fontWeight =
                                    FontWeight.Medium
                            )
                        }
                    }
                }
            }

            val pendientes =
                traslado.encomiendas
                    .filter {

                        it.descargadoAt
                            .isBlank() &&
                                it.encomienda
                                    .sedeDestino
                                    .id ==
                                uiState
                                    .sedeSeleccionadaId
                    }

            item {

                Text(
                    text =
                        "Encomiendas para descargar (${pendientes.size})",

                    color =
                        TextPrimary,

                    fontSize =
                        17.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }

            if (
                pendientes.isEmpty()
            ) {

                item {

                    EstadoVacio(
                        texto =
                            "No hay encomiendas pendientes para esta sede"
                    )
                }

            } else {

                items(
                    items =
                        pendientes,

                    key = {
                        it.detalleId
                    }
                ) { detalle ->

                    EncomiendaMovilidadCard(
                        detalle =
                            detalle
                    )
                }
            }

            item {

                Button(
                    onClick = {

                        viewModel
                            .descargarEnSede()
                    },

                    enabled =
                        traslado.estado
                            .trim()
                            .uppercase() ==
                                "EN_CURSO" &&
                                pendientes
                                    .isNotEmpty() &&
                                !uiState.procesando,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(
                                56.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
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

                    if (
                        uiState.procesando
                    ) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(
                                    22.dp
                                ),

                            color =
                                Color.White,

                            strokeWidth =
                                2.dp
                        )

                    } else {

                        Text(
                            text =
                                "Confirmar descarga",

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {

            MensajesMovilidad(
                uiState =
                    uiState
            )

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )
        }
    }
}


@Composable
fun ReporteScreenContent(
    paddingValues: PaddingValues,
    uiState: MovilidadUiState,
    viewModel: MovilidadViewModel
) {

    var menuAbierto by
    remember {

        mutableStateOf(
            false
        )
    }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
                .padding(
                    horizontal =
                        20.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                16.dp
            )
    ) {

        item {

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Text(
                text =
                    "Reportes de ruta",

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Text(
                text =
                    "Registrar incidencia",

                color =
                    MaterialTheme
                        .colorScheme
                        .secondary,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        item {

            Box {

                OutlinedButton(
                    onClick = {

                        menuAbierto =
                            true
                    },

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(
                                56.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        )
                ) {

                    Text(
                        text =
                            nombreTipoReporte(
                                uiState.tipoReporte
                            ),

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        textAlign =
                            TextAlign.Start
                    )
                }

                DropdownMenu(
                    expanded =
                        menuAbierto,

                    onDismissRequest = {

                        menuAbierto =
                            false
                    }
                ) {

                    DropdownMenuItem(
                        text = {

                            Text(
                                text =
                                    "Retraso"
                            )
                        },

                        onClick = {

                            viewModel
                                .cambiarTipoReporte(
                                    "RETRASO"
                                )

                            menuAbierto =
                                false
                        }
                    )

                    DropdownMenuItem(
                        text = {

                            Text(
                                text =
                                    "Daño"
                            )
                        },

                        onClick = {

                            viewModel
                                .cambiarTipoReporte(
                                    "DANIO"
                                )

                            menuAbierto =
                                false
                        }
                    )

                    DropdownMenuItem(
                        text = {

                            Text(
                                text =
                                    "Extravío"
                            )
                        },

                        onClick = {

                            viewModel
                                .cambiarTipoReporte(
                                    "EXTRAVIO"
                                )

                            menuAbierto =
                                false
                        }
                    )
                }
            }
        }

        item {

            OutlinedTextField(
                value =
                    uiState
                        .descripcionReporte,

                onValueChange = {

                    viewModel
                        .cambiarDescripcionReporte(
                            it
                        )
                },

                label = {

                    Text(
                        text =
                            "Descripción"
                    )
                },

                placeholder = {

                    Text(
                        text =
                            "Describe lo ocurrido durante el traslado"
                    )
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            150.dp
                        ),

                maxLines =
                    6,

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

        item {

            Card(
                shape =
                    RoundedCornerShape(
                        16.dp
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
                    ),

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            18.dp
                        )
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Warning,

                            contentDescription =
                                "Incidencia",

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .secondary
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    12.dp
                                )
                        )

                        Column {

                            Text(
                                text =
                                    "Traslado relacionado",

                                color =
                                    TextSecondary,

                                fontSize =
                                    12.sp
                            )

                            Text(
                                text =
                                    if (
                                        uiState
                                            .trasladoSeleccionadoId >
                                        0
                                    ) {

                                        "#${uiState.trasladoSeleccionadoId}"

                                    } else {

                                        "Sin traslado"
                                    },

                                color =
                                    TextPrimary,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        item {

            Button(
                onClick = {

                    viewModel
                        .validarReporte()
                },

                enabled =
                    uiState
                        .descripcionReporte
                        .isNotBlank() &&
                            uiState
                                .trasladoSeleccionadoId >
                            0,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            56.dp
                        ),

                shape =
                    RoundedCornerShape(
                        14.dp
                    ),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Color(
                                    0xFFE65100
                                )
                        )
            ) {

                Text(
                    text =
                        "Registrar reporte",

                    color =
                        SurfaceWhite,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

        item {

            MensajesMovilidad(
                uiState =
                    uiState
            )

            Spacer(
                modifier =
                    Modifier.height(
                        20.dp
                    )
            )
        }
    }
}


@Composable
fun TrasladoSelector(
    traslado: TrasladoMovilidad,
    seleccionado: Boolean,
    onClick: () -> Unit
) {

    Surface(
        modifier =
            Modifier.clickable {

                onClick()
            },

        shape =
            RoundedCornerShape(
                16.dp
            ),

        color =
            if (
                seleccionado
            ) {

                MaterialTheme
                    .colorScheme
                    .primary
                    .copy(
                        alpha =
                            0.1f
                    )

            } else {

                SurfaceWhite
            },

        border =
            BorderStroke(
                1.dp,

                if (
                    seleccionado
                ) {

                    MaterialTheme
                        .colorScheme
                        .primary

                } else {

                    InputBorder
                }
            )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    14.dp
                )
        ) {

            Text(
                text =
                    "#${traslado.id}",

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold
            )

            Text(
                text =
                    traslado.vehiculo
                        .placa,

                color =
                    TextSecondary,

                fontSize =
                    12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(
                        6.dp
                    )
            )

            EstadoChip(
                estado =
                    traslado.estado
            )
        }
    }
}


@Composable
fun ResumenTrasladoCard(
    traslado: TrasladoMovilidad
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
                )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    20.dp
                )
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(
                                52.dp
                            )
                            .clip(
                                RoundedCornerShape(
                                    14.dp
                                )
                            )
                            .background(
                                LightBackground
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.LocalShipping,

                        contentDescription =
                            "Vehículo",

                        tint =
                            MaterialTheme
                                .colorScheme
                                .secondary
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(
                            14.dp
                        )
                )

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    Text(
                        text =
                            traslado.vehiculo
                                .placa,

                        color =
                            TextPrimary,

                        fontSize =
                            19.sp,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            traslado.vehiculo
                                .descripcion,

                        color =
                            TextSecondary,

                        fontSize =
                            13.sp
                    )
                }

                EstadoChip(
                    estado =
                        traslado.estado
                )
            }

            HorizontalDivider(
                modifier =
                    Modifier.padding(
                        vertical =
                            16.dp
                    ),

                color =
                    InputBorder
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                ResumenDato(
                    titulo =
                        "Total",

                    valor =
                        traslado.resumen
                            .totalEncomiendas
                            .toString()
                )

                ResumenDato(
                    titulo =
                        "Pendientes",

                    valor =
                        traslado.resumen
                            .pendientesDescarga
                            .toString()
                )

                ResumenDato(
                    titulo =
                        "Descargadas",

                    valor =
                        traslado.resumen
                            .descargadas
                            .toString()
                )
            }
        }
    }
}


@Composable
fun ResumenDato(
    titulo: String,
    valor: String
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text =
                valor,

            color =
                TextPrimary,

            fontSize =
                20.sp,

            fontWeight =
                FontWeight.Bold
        )

        Text(
            text =
                titulo,

            color =
                TextSecondary,

            fontSize =
                11.sp
        )
    }
}


@Composable
fun RutaCard(
    traslado: TrasladoMovilidad
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
                )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    20.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Route,

                    contentDescription =
                        "Ruta",

                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            10.dp
                        )
                )

                Column {

                    Text(
                        text =
                            traslado.ruta
                                .name,

                        color =
                            TextPrimary,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            17.sp
                    )

                    Text(
                        text =
                            "${traslado.ruta.recorrido.size} sedes en el recorrido",

                        color =
                            TextSecondary,

                        fontSize =
                            12.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        18.dp
                    )
            )

            traslado.ruta
                .recorrido
                .forEachIndexed {
                        index,
                        item ->

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Box(
                            modifier =
                                Modifier
                                    .size(
                                        30.dp
                                    )
                                    .clip(
                                        CircleShape
                                    )
                                    .background(
                                        MaterialTheme
                                            .colorScheme
                                            .primary
                                            .copy(
                                                alpha =
                                                    0.12f
                                            )
                                    ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    item.orden
                                        .toString(),

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .primary,

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize =
                                    12.sp
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.width(
                                    12.dp
                                )
                        )

                        Text(
                            text =
                                item.sede.name,

                            color =
                                TextPrimary,

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Medium
                        )
                    }

                    if (
                        index <
                        traslado.ruta
                            .recorrido
                            .lastIndex
                    ) {

                        Box(
                            modifier =
                                Modifier
                                    .padding(
                                        start =
                                            14.dp
                                    )
                                    .width(
                                        2.dp
                                    )
                                    .height(
                                        20.dp
                                    )
                                    .background(
                                        InputBorder
                                    )
                        )
                    }
                }
        }
    }
}


@Composable
fun EncomiendaMovilidadCard(
    detalle: DetalleEncomiendaMovilidad
) {

    val descargada =
        detalle.descargadoAt
            .isNotBlank()

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                16.dp
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

        Row(
            modifier =
                Modifier.padding(
                    16.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            48.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                12.dp
                            )
                        )
                        .background(
                            LightBackground
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        if (
                            descargada
                        ) {

                            Icons.Default.Check

                        } else {

                            Icons.Default.LocalShipping
                        },

                    contentDescription =
                        "Encomienda",

                    tint =
                        if (
                            descargada
                        ) {

                            Color(
                                0xFF2E7D32
                            )

                        } else {

                            Color(
                                0xFFE65100
                            )
                        }
                )
            }

            Spacer(
                modifier =
                    Modifier.width(
                        14.dp
                    )
            )

            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {

                Text(
                    text =
                        detalle
                            .encomienda
                            .codigoRastreo,

                    color =
                        TextPrimary,

                    fontSize =
                        15.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text =
                        "Destino: ${detalle.encomienda.sedeDestino.name}",

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp
                )

                Text(
                    text =
                        "Peso: ${detalle.encomienda.pesoKg} kg",

                    color =
                        TextSecondary,

                    fontSize =
                        12.sp
                )
            }

            Text(
                text =
                    if (
                        descargada
                    ) {

                        "Descargada"

                    } else {

                        detalle.encomienda
                            .estado
                            .replace(
                                "_",
                                " "
                            )
                    },

                color =
                    if (
                        descargada
                    ) {

                        Color(
                            0xFF2E7D32
                        )

                    } else {

                        Color(
                            0xFFE65100
                        )
                    },

                fontSize =
                    11.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}


@Composable
fun EstadoChip(
    estado: String
) {

    val estadoNormalizado =
        estado
            .trim()
            .uppercase()

    val color =
        if (
            estadoNormalizado ==
            "EN_CURSO"
        ) {

            Color(
                0xFFE65100
            )

        } else if (
            estadoNormalizado ==
            "FINALIZADO"
        ) {

            Color(
                0xFF2E7D32
            )

        } else if (
            estadoNormalizado ==
            "CANCELADO"
        ) {

            Color(
                0xFFC62828
            )

        } else {

            MaterialTheme
                .colorScheme
                .primary
        }

    Surface(
        shape =
            RoundedCornerShape(
                20.dp
            ),

        color =
            color.copy(
                alpha =
                    0.12f
            )
    ) {

        Text(
            text =
                estadoNormalizado
                    .replace(
                        "_",
                        " "
                    ),

            modifier =
                Modifier.padding(
                    horizontal =
                        10.dp,

                    vertical =
                        5.dp
                ),

            color =
                color,

            fontSize =
                11.sp,

            fontWeight =
                FontWeight.Bold
        )
    }
}


@Composable
fun EstadoVacio(
    texto: String
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                16.dp
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
                        28.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Default.LocalShipping,

                contentDescription =
                    "Vacío",

                tint =
                    TextSecondary,

                modifier =
                    Modifier.size(
                        42.dp
                    )
            )

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            Text(
                text =
                    texto,

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                textAlign =
                    TextAlign.Center
            )
        }
    }
}


@Composable
fun MensajesMovilidad(
    uiState: MovilidadUiState
) {

    if (
        uiState.procesando
    ) {

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.Center,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            CircularProgressIndicator(
                modifier =
                    Modifier.size(
                        22.dp
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
                    "Procesando",

                color =
                    TextSecondary
            )
        }
    }

    if (
        uiState.error
            .isNotBlank()
    ) {

        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top =
                            10.dp
                    ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Color(
                                0xFFFFEBEE
                            )
                    )
        ) {

            Row(
                modifier =
                    Modifier.padding(
                        14.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ErrorOutline,

                    contentDescription =
                        "Error",

                    tint =
                        Color(
                            0xFFC62828
                        )
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            10.dp
                        )
                )

                Text(
                    text =
                        uiState.error,

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

        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top =
                            10.dp
                    ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Color(
                                0xFFE8F5E9
                            )
                    )
        ) {

            Row(
                modifier =
                    Modifier.padding(
                        14.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Check,

                    contentDescription =
                        "Correcto",

                    tint =
                        Color(
                            0xFF2E7D32
                        )
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            10.dp
                        )
                )

                Text(
                    text =
                        uiState.mensaje,

                    color =
                        Color(
                            0xFF2E7D32
                        ),

                    fontSize =
                        13.sp
                )
            }
        }
    }
}


@Composable
fun MovilidadBottomNav(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {

    NavigationBar(
        containerColor =
            SurfaceWhite,

        tonalElevation =
            8.dp
    ) {

        NavigationBarItem(
            selected =
                selectedTab ==
                        0,

            onClick = {

                onTabSelected(
                    0
                )
            },

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.Map,

                    contentDescription =
                        "Mi ruta"
                )
            },

            label = {

                Text(
                    text =
                        "Mi ruta"
                )
            },

            colors =
                coloresNavegacion()
        )

        NavigationBarItem(
            selected =
                selectedTab ==
                        1,

            onClick = {

                onTabSelected(
                    1
                )
            },

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.Check,

                    contentDescription =
                        "Bajar"
                )
            },

            label = {

                Text(
                    text =
                        "Bajar"
                )
            },

            colors =
                coloresNavegacion()
        )

        NavigationBarItem(
            selected =
                selectedTab ==
                        2,

            onClick = {

                onTabSelected(
                    2
                )
            },

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.Warning,

                    contentDescription =
                        "Reporte"
                )
            },

            label = {

                Text(
                    text =
                        "Reporte"
                )
            },

            colors =
                coloresNavegacion()
        )
    }
}


@Composable
fun coloresNavegacion():
        NavigationBarItemColors {

    return NavigationBarItemDefaults
        .colors(
            selectedIconColor =
                MaterialTheme
                    .colorScheme
                    .secondary,

            selectedTextColor =
                MaterialTheme
                    .colorScheme
                    .secondary,

            indicatorColor =
                Color.Transparent,

            unselectedIconColor =
                TextSecondary,

            unselectedTextColor =
                TextSecondary
        )
}


fun nombreTipoReporte(
    tipo: String
): String {

    if (
        tipo ==
        "DANIO"
    ) {

        return "Daño"
    }

    if (
        tipo ==
        "EXTRAVIO"
    ) {

        return "Extravío"
    }

    return "Retraso"
}