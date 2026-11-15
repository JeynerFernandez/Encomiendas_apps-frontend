package pe.edu.pe.presentation.warehouse

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface as MaterialSurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.domain.model.EncomiendaAlmacen
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.model.TrasladoAsignadoAlmacen
import pe.edu.pe.domain.model.TrasladoDisponibleAlmacen
import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.NavySoft
import pe.edu.pe.presentation.theme.Surface
import pe.edu.pe.presentation.viewmodel.AlmacenViewModel

@Composable
fun WarehouseScreen(
    viewModel: AlmacenViewModel,
    onBackClick: () -> Unit
) {

    val uiState by
    viewModel.uiState.collectAsState()

    if (
        uiState.mostrarSelectorMovilidad
    ) {

        SelectorMovilidadDialog(
            encomienda =
                uiState.encomiendaSeleccionada,
            traslados =
                uiState.trasladosDisponibles,
            cargando =
                uiState.cargandoMovilidades,
            asignandoTrasladoId =
                uiState.asignandoTrasladoId,
            onAsignar = { trasladoId ->

                viewModel.asignarMovilidad(
                    trasladoId
                )
            },
            onCerrar = {

                viewModel.cerrarSelectorMovilidad()
            }
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    LightBackground
                )
                .padding(
                    horizontal = 20.dp
                )
    ) {

        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick =
                    onBackClick
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Regresar",
                    tint =
                        Navy
                )
            }

            Spacer(
                modifier =
                    Modifier.width(
                        8.dp
                    )
            )

            Column {

                Text(
                    text =
                        "Almacén",
                    fontSize =
                        26.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Ink
                )

                Text(
                    text =
                        "Gestiona las encomiendas de tu sede",
                    fontSize =
                        13.sp,
                    color =
                        Muted
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    28.dp
                )
        )

        if (
            uiState.sedeActualNombre
                .isNotBlank()
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
                        Border
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Surface
                    )
            ) {

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                14.dp
                            ),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(
                                    42.dp
                                )
                                .background(
                                    NavySoft,
                                    RoundedCornerShape(
                                        12.dp
                                    )
                                ),
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Outlined.Inventory2,
                            contentDescription =
                                null,
                            tint =
                                Navy
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.width(
                                12.dp
                            )
                    )

                    Column {

                        Text(
                            text =
                                "Sede actual",
                            fontSize =
                                11.sp,
                            color =
                                Muted
                        )

                        Text(
                            text =
                                uiState.sedeActualNombre,
                            fontSize =
                                15.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Ink
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        18.dp
                    )
            )
        }

        Text(
            text =
                "Destino",
            fontSize =
                15.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                Ink
        )

        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )

        LazyRow(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(
                    10.dp
                ),
            contentPadding =
                PaddingValues(
                    end = 12.dp
                )
        ) {

            items(
                items =
                    uiState.sedesDestino,
                key = { sede ->
                    sede.id
                }
            ) { sede ->

                var seleccionada =
                    false

                val destinoSeleccionado =
                    uiState
                        .sedeDestinoSeleccionada

                if (
                    destinoSeleccionado != null
                ) {

                    if (
                        destinoSeleccionado.id ==
                        sede.id
                    ) {

                        seleccionada =
                            true
                    }
                }

                SedeItem(
                    sede =
                        sede,
                    seleccionada =
                        seleccionada,
                    onClick = {

                        viewModel.seleccionarDestino(
                            sede
                        )
                    }
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    22.dp
                )
        )

        Text(
            text =
                "Mostrar",
            fontSize =
                15.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                Ink
        )

        Spacer(
            modifier =
                Modifier.height(
                    10.dp
                )
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(
                    8.dp
                )
        ) {

            FiltroItem(
                texto =
                    "Pendientes",
                seleccionado =
                    uiState.filtro ==
                            "pendientes",
                modifier =
                    Modifier.weight(
                        1f
                    ),
                onClick = {

                    viewModel.cambiarFiltro(
                        "pendientes"
                    )
                }
            )

            FiltroItem(
                texto =
                    "Asignadas",
                seleccionado =
                    uiState.filtro ==
                            "asignadas",
                modifier =
                    Modifier.weight(
                        1f
                    ),
                onClick = {

                    viewModel.cambiarFiltro(
                        "asignadas"
                    )
                }
            )

            FiltroItem(
                texto =
                    "Todas",
                seleccionado =
                    uiState.filtro ==
                            "todas",
                modifier =
                    Modifier.weight(
                        1f
                    ),
                onClick = {

                    viewModel.cambiarFiltro(
                        "todas"
                    )
                }
            )
        }

        Spacer(
            modifier =
                Modifier.height(
                    20.dp
                )
        )

        val mensaje =
            uiState.mensaje

        if (
            mensaje != null
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(
                        14.dp
                    ),
                border =
                    BorderStroke(
                        1.dp,
                        Navy
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            NavySoft
                    )
            ) {

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                14.dp
                            ),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            mensaje,
                        modifier =
                            Modifier.weight(
                                1f
                            ),
                        fontSize =
                            13.sp,
                        fontWeight =
                            FontWeight.Medium,
                        color =
                            Navy
                    )

                    TextButton(
                        onClick = {

                            viewModel.limpiarMensaje()
                        }
                    ) {

                        Text(
                            text =
                                "Cerrar",
                            color =
                                Navy
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )
        }

        val error =
            uiState.error

        if (
            error != null
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(
                        14.dp
                    ),
                border =
                    BorderStroke(
                        1.dp,
                        Border
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Surface
                    )
            ) {

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                14.dp
                            ),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            error,
                        modifier =
                            Modifier.weight(
                                1f
                            ),
                        fontSize =
                            13.sp,
                        color =
                            Muted
                    )

                    TextButton(
                        onClick = {

                            viewModel.limpiarMensaje()
                        }
                    ) {

                        Text(
                            text =
                                "Cerrar",
                            color =
                                Navy
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )
        }

        val destino =
            uiState
                .sedeDestinoSeleccionada

        if (
            destino == null
        ) {

            EstadoVacio(
                mensaje =
                    "Selecciona una sede para ver las encomiendas."
            )

        } else if (
            uiState.isLoading
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                CircularProgressIndicator(
                    color =
                        Navy
                )
            }

        } else {

            Text(
                text =
                    obtenerTituloLista(
                        filtro =
                            uiState.filtro,
                        total =
                            uiState.total
                    ),
                fontSize =
                    15.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Ink
            )

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            if (
                uiState.encomiendas
                    .isEmpty()
            ) {

                if (
                    uiState.filtro ==
                    "pendientes"
                ) {

                    EstadoVacio(
                        mensaje =
                            "No hay encomiendas pendientes para esta sede."
                    )

                } else if (
                    uiState.filtro ==
                    "asignadas"
                ) {

                    EstadoVacio(
                        mensaje =
                            "No hay encomiendas esperando salida en una movilidad."
                    )

                } else {

                    EstadoVacio(
                        mensaje =
                            "No hay encomiendas para esta sede."
                    )
                }

            } else {

                LazyColumn(
                    modifier =
                        Modifier.weight(
                            1f
                        ),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        ),
                    contentPadding =
                        PaddingValues(
                            bottom = 30.dp
                        )
                ) {

                    items(
                        items =
                            uiState.encomiendas,
                        key = { encomienda ->
                            encomienda.id
                        }
                    ) { encomienda ->

                        EncomiendaAlmacenItem(
                            encomienda =
                                encomienda,
                            procesando =
                                uiState.procesandoCodigo ==
                                        encomienda.codigoRastreo,
                            onIngresarAlmacen = {

                                viewModel.ingresarAlmacen(
                                    encomienda
                                )
                            },
                            onAsignarMovilidad = {

                                viewModel.abrirSelectorMovilidad(
                                    encomienda
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SedeItem(
    sede: Sede,
    seleccionada: Boolean,
    onClick: () -> Unit
) {

    val colorBorde =
        if (
            seleccionada
        ) {
            Navy
        } else {
            Border
        }

    val colorFondo =
        if (
            seleccionada
        ) {
            Navy
        } else {
            Surface
        }

    val colorTexto =
        if (
            seleccionada
        ) {
            Surface
        } else {
            Ink
        }

    Card(
        onClick =
            onClick,
        shape =
            RoundedCornerShape(
                14.dp
            ),
        border =
            BorderStroke(
                1.dp,
                colorBorde
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    colorFondo
            )
    ) {

        Text(
            text =
                sede.name,
            modifier =
                Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 11.dp
                ),
            fontSize =
                13.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                colorTexto
        )
    }
}

@Composable
private fun FiltroItem(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    val colorBorde =
        if (
            seleccionado
        ) {
            Navy
        } else {
            Border
        }

    val colorFondo =
        if (
            seleccionado
        ) {
            NavySoft
        } else {
            Surface
        }

    val colorTexto =
        if (
            seleccionado
        ) {
            Navy
        } else {
            Muted
        }

    Card(
        onClick =
            onClick,
        modifier =
            modifier,
        shape =
            RoundedCornerShape(
                12.dp
            ),
        border =
            BorderStroke(
                1.dp,
                colorBorde
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    colorFondo
            )
    ) {

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 4.dp,
                        vertical = 12.dp
                    ),
            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text =
                    texto,
                fontSize =
                    12.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    colorTexto,
                maxLines =
                    1
            )
        }
    }
}

@Composable
private fun EncomiendaAlmacenItem(
    encomienda: EncomiendaAlmacen,
    procesando: Boolean,
    onIngresarAlmacen: () -> Unit,
    onAsignarMovilidad: () -> Unit
) {

    val estado =
        encomienda.estado
            .trim()
            .uppercase()

    val trasladoAsignado =
        encomienda.trasladoAsignado

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                20.dp
            ),
        border =
            BorderStroke(
                1.dp,
                if (
                    trasladoAsignado != null
                ) {
                    Navy
                } else {
                    Border
                }
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Surface
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    if (
                        trasladoAsignado != null
                    ) {
                        2.dp
                    } else {
                        0.dp
                    }
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        18.dp
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
                                48.dp
                            )
                            .background(
                                if (
                                    trasladoAsignado != null
                                ) {
                                    Navy
                                } else {
                                    NavySoft
                                },
                                RoundedCornerShape(
                                    14.dp
                                )
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.Inventory2,
                        contentDescription =
                            null,
                        tint =
                            if (
                                trasladoAsignado != null
                            ) {
                                Surface
                            } else {
                                Navy
                            }
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(
                            12.dp
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
                            encomienda.codigoRastreo,
                        fontSize =
                            16.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Ink
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                4.dp
                            )
                    )

                    Text(
                        text =
                            "${encomienda.sedeOrigen} → ${encomienda.sedeDestino}",
                        fontSize =
                            12.sp,
                        color =
                            Muted
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            EstadoEncomienda(
                estado =
                    encomienda.estado
            )

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        10.dp
                    )
            ) {

                DatoResumen(
                    titulo =
                        "Peso",
                    valor =
                        "${formatearKg(encomienda.pesoKg)} kg",
                    modifier =
                        Modifier.weight(
                            1f
                        )
                )

                DatoResumen(
                    titulo =
                        "Costo",
                    valor =
                        "S/ ${formatearMonto(encomienda.costoTotal)}",
                    modifier =
                        Modifier.weight(
                            1f
                        )
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            PagoResumen(
                modalidad =
                    encomienda.modalidadPago,
                estadoPago =
                    encomienda.estadoPago
            )

            val observacion =
                encomienda.observacion

            if (
                observacion != null
            ) {

                val observacionLimpia =
                    observacion.trim()

                if (
                    observacionLimpia.isNotBlank()
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    MaterialSurface(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),
                        color =
                            LightBackground
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    12.dp
                                )
                        ) {

                            Text(
                                text =
                                    "Observación",
                                fontSize =
                                    11.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Muted
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        4.dp
                                    )
                            )

                            Text(
                                text =
                                    observacionLimpia,
                                fontSize =
                                    13.sp,
                                color =
                                    Ink
                            )
                        }
                    }
                }
            }

            if (
                estado == "REGISTRADO" ||
                estado == "CAMINO_ALMACEN"
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            14.dp
                        )
                )

                BotonAccion(
                    texto =
                        "Ingresar a almacén",
                    cargando =
                        procesando,
                    bloqueado =
                        false,
                    onClick =
                        onIngresarAlmacen
                )

            } else if (
                estado == "EN_ALMACEN"
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            14.dp
                        )
                )

                if (
                    trasladoAsignado == null
                ) {

                    MaterialSurface(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),
                        color =
                            NavySoft
                    ) {

                        Text(
                            text =
                                "Lista para asignar a una movilidad.",
                            modifier =
                                Modifier.padding(
                                    12.dp
                                ),
                            fontSize =
                                12.sp,
                            fontWeight =
                                FontWeight.Medium,
                            color =
                                Navy
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    BotonAccion(
                        texto =
                            "Asignar movilidad",
                        cargando =
                            false,
                        bloqueado =
                            false,
                        onClick =
                            onAsignarMovilidad
                    )

                } else {

                    MovilidadAsignadaCard(
                        traslado =
                            trasladoAsignado
                    )
                }
            }
        }
    }
}

@Composable
private fun DatoResumen(
    titulo: String,
    valor: String,
    modifier: Modifier
) {

    MaterialSurface(
        modifier =
            modifier,
        shape =
            RoundedCornerShape(
                12.dp
            ),
        color =
            LightBackground
    ) {

        Column(
            modifier =
                Modifier.padding(
                    12.dp
                )
        ) {

            Text(
                text =
                    titulo,
                fontSize =
                    11.sp,
                color =
                    Muted
            )

            Spacer(
                modifier =
                    Modifier.height(
                        3.dp
                    )
            )

            Text(
                text =
                    valor,
                fontSize =
                    14.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Ink
            )
        }
    }
}

@Composable
private fun PagoResumen(
    modalidad: String?,
    estadoPago: String?
) {

    var modalidadTexto =
        "No especificado"

    if (
        modalidad != null
    ) {

        val modalidadNormalizada =
            modalidad
                .trim()
                .uppercase()

        if (
            modalidadNormalizada ==
            "ORIGEN"
        ) {

            modalidadTexto =
                "En origen"

        } else if (
            modalidadNormalizada ==
            "DESTINO"
        ) {

            modalidadTexto =
                "Contra entrega"

        } else if (
            modalidad.trim()
                .isNotBlank()
        ) {

            modalidadTexto =
                modalidad.trim()
        }
    }

    var estadoTexto =
        "No especificado"

    if (
        estadoPago != null
    ) {

        estadoTexto =
            formatearEstadoPago(
                estadoPago
            )
    }

    MaterialSurface(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                12.dp
            ),
        color =
            NavySoft
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        12.dp
                    ),
            horizontalArrangement =
                Arrangement.spacedBy(
                    12.dp
                )
        ) {

            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {

                Text(
                    text =
                        "Modalidad de pago",
                    fontSize =
                        11.sp,
                    color =
                        Muted
                )

                Text(
                    text =
                        modalidadTexto,
                    fontSize =
                        13.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Navy
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
                        "Estado de pago",
                    fontSize =
                        11.sp,
                    color =
                        Muted
                )

                Text(
                    text =
                        estadoTexto,
                    fontSize =
                        13.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Navy
                )
            }
        }
    }
}

@Composable
private fun MovilidadAsignadaCard(
    traslado: TrasladoAsignadoAlmacen
) {

    var recorridoTexto =
        traslado.rutaNombre

    if (
        traslado.recorrido
            .isNotEmpty()
    ) {

        recorridoTexto =
            traslado.recorrido
                .joinToString(
                    " → "
                )
    }

    MaterialSurface(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                16.dp
            ),
        color =
            NavySoft,
        border =
            BorderStroke(
                1.dp,
                Navy
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        15.dp
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
                                40.dp
                            )
                            .background(
                                Navy,
                                RoundedCornerShape(
                                    12.dp
                                )
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.Inventory2,
                        contentDescription =
                            null,
                        tint =
                            Surface
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(
                            10.dp
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
                            "Movilidad asignada",
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Navy
                    )

                    Text(
                        text =
                            "Esperando salida",
                        fontSize =
                            11.sp,
                        color =
                            Muted
                    )
                }

                EstadoTraslado(
                    estado =
                        traslado.estado
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            DatoMovilidad(
                titulo =
                    "Placa",
                valor =
                    traslado.placa
            )

            val descripcion =
                traslado
                    .vehiculoDescripcion
                    .trim()

            if (
                descripcion.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            8.dp
                        )
                )

                DatoMovilidad(
                    titulo =
                        "Vehículo",
                    valor =
                        descripcion
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            DatoMovilidad(
                titulo =
                    "Conductor",
                valor =
                    traslado.conductorNombre
            )

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            Text(
                text =
                    "Ruta",
                fontSize =
                    11.sp,
                color =
                    Muted
            )

            Text(
                text =
                    recorridoTexto,
                fontSize =
                    13.sp,
                lineHeight =
                    18.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Ink
            )
        }
    }
}

@Composable
private fun DatoMovilidad(
    titulo: String,
    valor: String
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                titulo,
            fontSize =
                12.sp,
            color =
                Muted
        )

        Spacer(
            modifier =
                Modifier.width(
                    12.dp
                )
        )

        Text(
            text =
                valor,
            modifier =
                Modifier.weight(
                    1f
                ),
            fontSize =
                13.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                Ink,
            textAlign =
                TextAlign.End
        )
    }
}

@Composable
private fun EstadoTraslado(
    estado: String
) {

    val normalizado =
        estado
            .trim()
            .uppercase()

    var texto =
        estado

    if (
        normalizado ==
        "PROGRAMADO"
    ) {
        texto =
            "Programado"
    } else if (
        normalizado ==
        "EN_CURSO"
    ) {
        texto =
            "En curso"
    } else if (
        normalizado ==
        "FINALIZADO"
    ) {
        texto =
            "Finalizado"
    } else if (
        normalizado ==
        "CANCELADO"
    ) {
        texto =
            "Cancelado"
    }

    MaterialSurface(
        shape =
            RoundedCornerShape(
                20.dp
            ),
        color =
            Surface
    ) {

        Text(
            text =
                texto,
            modifier =
                Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                ),
            fontSize =
                10.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                Navy
        )
    }
}

@Composable
private fun EstadoEncomienda(
    estado: String
) {

    val estadoNormalizado =
        estado
            .trim()
            .uppercase()

    var textoEstado =
        estado

    if (
        estadoNormalizado ==
        "REGISTRADO"
    ) {
        textoEstado =
            "Registrado"
    } else if (
        estadoNormalizado ==
        "CAMINO_ALMACEN"
    ) {
        textoEstado =
            "Camino a almacén"
    } else if (
        estadoNormalizado ==
        "EN_ALMACEN"
    ) {
        textoEstado =
            "En almacén"
    } else if (
        estadoNormalizado ==
        "EN_TRANSITO"
    ) {
        textoEstado =
            "En tránsito"
    } else if (
        estadoNormalizado ==
        "EN_SEDE_DESTINO"
    ) {
        textoEstado =
            "En sede destino"
    } else if (
        estadoNormalizado ==
        "ENTREGADO"
    ) {
        textoEstado =
            "Entregado"
    } else if (
        estadoNormalizado ==
        "CANCELADO"
    ) {
        textoEstado =
            "Cancelado"
    }

    Box(
        modifier =
            Modifier
                .background(
                    NavySoft,
                    RoundedCornerShape(
                        8.dp
                    )
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                )
    ) {

        Text(
            text =
                textoEstado,
            fontSize =
                12.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                Navy
        )
    }
}

@Composable
private fun BotonAccion(
    texto: String,
    cargando: Boolean,
    bloqueado: Boolean,
    onClick: () -> Unit
) {

    Card(
        onClick = {

            if (
                !cargando &&
                !bloqueado
            ) {

                onClick()
            }
        },
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                12.dp
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Navy
            )
    ) {

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp
                    ),
            contentAlignment =
                Alignment.Center
        ) {

            if (
                cargando
            ) {

                CircularProgressIndicator(
                    modifier =
                        Modifier.size(
                            20.dp
                        ),
                    strokeWidth =
                        2.dp,
                    color =
                        Surface
                )

            } else {

                Text(
                    text =
                        texto,
                    color =
                        Surface,
                    fontSize =
                        13.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SelectorMovilidadDialog(
    encomienda: EncomiendaAlmacen?,
    traslados: List<TrasladoDisponibleAlmacen>,
    cargando: Boolean,
    asignandoTrasladoId: Int?,
    onAsignar: (Int) -> Unit,
    onCerrar: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {

            if (
                asignandoTrasladoId == null
            ) {

                onCerrar()
            }
        },
        title = {

            Column {

                Text(
                    text =
                        "Asignar movilidad",
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Ink
                )

                if (
                    encomienda != null
                ) {

                    Text(
                        text =
                            encomienda.codigoRastreo,
                        fontSize =
                            13.sp,
                        color =
                            Muted
                    )

                    Text(
                        text =
                            "Peso: ${formatearKg(encomienda.pesoKg)} kg",
                        fontSize =
                            12.sp,
                        color =
                            Muted
                    )
                }
            }
        },
        text = {

            if (
                cargando
            ) {

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 30.dp
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color =
                            Navy
                    )
                }

            } else if (
                traslados.isEmpty()
            ) {

                Text(
                    text =
                        "No existen movilidades disponibles para esta ruta.",
                    color =
                        Muted,
                    fontSize =
                        13.sp,
                    textAlign =
                        TextAlign.Center
                )

            } else {

                LazyColumn(
                    modifier =
                        Modifier.heightIn(
                            max = 420.dp
                        ),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    items(
                        items =
                            traslados,
                        key = { traslado ->
                            traslado.trasladoId
                        }
                    ) { traslado ->

                        var bloqueado =
                            false

                        if (
                            asignandoTrasladoId != null
                        ) {

                            bloqueado =
                                true
                        }

                        var cargandoTraslado =
                            false

                        if (
                            asignandoTrasladoId != null
                        ) {

                            if (
                                asignandoTrasladoId ==
                                traslado.trasladoId
                            ) {

                                cargandoTraslado =
                                    true
                            }
                        }

                        var capacidadSuficiente =
                            true

                        if (
                            encomienda != null
                        ) {

                            if (
                                encomienda.pesoKg >
                                traslado.vehiculo
                                    .capacidadDisponibleKg
                            ) {

                                capacidadSuficiente =
                                    false
                            }
                        }

                        MovilidadItem(
                            traslado =
                                traslado,
                            cargando =
                                cargandoTraslado,
                            bloqueado =
                                bloqueado,
                            capacidadSuficiente =
                                capacidadSuficiente,
                            onAsignar = {

                                onAsignar(
                                    traslado.trasladoId
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {

            TextButton(
                onClick = {

                    if (
                        asignandoTrasladoId ==
                        null
                    ) {

                        onCerrar()
                    }
                }
            ) {

                Text(
                    text =
                        "Cerrar",
                    color =
                        Navy
                )
            }
        },
        containerColor =
            Surface
    )
}

@Composable
private fun MovilidadItem(
    traslado: TrasladoDisponibleAlmacen,
    cargando: Boolean,
    bloqueado: Boolean,
    capacidadSuficiente: Boolean,
    onAsignar: () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                14.dp
            ),
        border =
            BorderStroke(
                1.dp,
                Border
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Surface
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        14.dp
                    )
        ) {

            Text(
                text =
                    traslado.vehiculo.placa,
                fontSize =
                    16.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Ink
            )

            Text(
                text =
                    traslado.vehiculo.descripcion,
                fontSize =
                    13.sp,
                color =
                    Muted
            )

            Text(
                text =
                    "Conductor: ${traslado.conductor.name}",
                fontSize =
                    13.sp,
                color =
                    Muted
            )

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        8.dp
                    )
            ) {

                DatoResumen(
                    titulo =
                        "Capacidad",
                    valor =
                        "${formatearKg(traslado.vehiculo.capacidadKg)} kg",
                    modifier =
                        Modifier.weight(
                            1f
                        )
                )

                DatoResumen(
                    titulo =
                        "Disponible",
                    valor =
                        "${formatearKg(traslado.vehiculo.capacidadDisponibleKg)} kg",
                    modifier =
                        Modifier.weight(
                            1f
                        )
                )
            }

            if (
                traslado.recorrido
                    .isNotEmpty()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )

                MaterialSurface(
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(
                            10.dp
                        ),
                    color =
                        NavySoft
                ) {

                    Text(
                        text =
                            traslado.recorrido
                                .joinToString(
                                    " → "
                                ),
                        modifier =
                            Modifier.padding(
                                10.dp
                            ),
                        fontSize =
                            12.sp,
                        color =
                            Navy
                    )
                }
            }

            if (
                !capacidadSuficiente
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            8.dp
                        )
                )

                Text(
                    text =
                        "Capacidad insuficiente para esta encomienda",
                    fontSize =
                        12.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Muted
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            var textoBoton =
                "Asignar"

            if (
                !capacidadSuficiente
            ) {
                textoBoton =
                    "Sin capacidad"
            } else if (
                bloqueado
            ) {
                textoBoton =
                    "Procesando"
            }

            BotonAccion(
                texto =
                    textoBoton,
                cargando =
                    cargando,
                bloqueado =
                    bloqueado ||
                            !capacidadSuficiente,
                onClick =
                    onAsignar
            )
        }
    }
}

@Composable
private fun EstadoVacio(
    mensaje: String
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
                Border
            ),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Surface
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        30.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.Inventory2,
                contentDescription =
                    null,
                tint =
                    Muted,
                modifier =
                    Modifier.size(
                        40.dp
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
                    mensaje,
                color =
                    Muted,
                fontSize =
                    14.sp,
                textAlign =
                    TextAlign.Center
            )
        }
    }
}

private fun obtenerTituloLista(
    filtro: String,
    total: Int
): String {

    if (
        filtro == "asignadas"
    ) {

        return "Asignadas ($total)"
    }

    if (
        filtro == "pendientes"
    ) {

        return "Pendientes ($total)"
    }

    return "Encomiendas ($total)"
}

private fun formatearMonto(
    valor: Double
): String {

    val texto =
        valor.toString()

    val partes =
        texto.split(
            "."
        )

    if (
        partes.size == 2
    ) {

        val decimales =
            partes[1]

        if (
            decimales.length == 1
        ) {

            return "${partes[0]}.${decimales}0"
        }
    }

    return texto
}

private fun formatearKg(
    valor: Double
): String {

    if (
        valor % 1.0 ==
        0.0
    ) {

        return valor
            .toInt()
            .toString()
    }

    return valor.toString()
}

private fun formatearEstadoPago(
    estado: String
): String {

    val estadoNormalizado =
        estado
            .trim()
            .uppercase()

    if (
        estadoNormalizado ==
        "PAGADO"
    ) {

        return "Pagado"
    }

    if (
        estadoNormalizado ==
        "PENDIENTE"
    ) {

        return "Pendiente"
    }

    return estado
}