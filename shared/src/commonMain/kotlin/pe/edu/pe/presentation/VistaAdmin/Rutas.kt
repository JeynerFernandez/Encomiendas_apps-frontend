package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.domain.model.Ruta
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.NavySoft
import pe.edu.pe.presentation.theme.Surface
import pe.edu.pe.presentation.viewmodel.RutaViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun GestionRutasAdminContent(
    viewModel: RutaViewModel,
    onSalirClick: () -> Unit = {}
) {

    val uiState by
    viewModel.uiState
        .collectAsState()

    var origenExpandido by
    remember {
        mutableStateOf(
            false
        )
    }

    var destinoExpandido by
    remember {
        mutableStateOf(
            false
        )
    }

    var paradaExpandida by
    remember {
        mutableIntStateOf(
            -1
        )
    }

    val rutasFiltradas =
        viewModel
            .obtenerRutasFiltradas()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    LightBackground
                )
                .statusBarsPadding()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 18.dp
                )
    ) {

        Spacer(
            modifier =
                Modifier.height(
                    10.dp
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
                    onSalirClick,
                modifier =
                    Modifier.size(
                        40.dp
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored
                            .Filled
                            .ArrowBack,
                    contentDescription =
                        "Regresar",
                    tint =
                        Navy,
                    modifier =
                        Modifier.size(
                            24.dp
                        )
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
                        "Administrador",
                    fontSize =
                        12.sp,
                    color =
                        Muted
                )

                Text(
                    text =
                        "Gestión de rutas",
                    fontSize =
                        25.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Ink
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    15.dp
                )
        )

        HorizontalDivider(
            color =
                Border
        )

        Spacer(
            modifier =
                Modifier.height(
                    26.dp
                )
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(
                    12.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value =
                    uiState.textoBusqueda,
                onValueChange = { texto ->

                    viewModel
                        .cambiarBusqueda(
                            texto
                        )
                },
                modifier =
                    Modifier.weight(
                        1f
                    ),
                placeholder = {

                    Text(
                        text =
                            "Buscar ruta",
                        color =
                            Muted
                    )
                },
                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,
                        contentDescription =
                            "Buscar ruta",
                        tint =
                            Muted
                    )
                },
                singleLine =
                    true,
                shape =
                    RoundedCornerShape(
                        14.dp
                    ),
                colors =
                    OutlinedTextFieldDefaults
                        .colors(
                            focusedBorderColor =
                                Navy,
                            unfocusedBorderColor =
                                Border,
                            focusedTextColor =
                                Ink,
                            unfocusedTextColor =
                                Ink
                        )
            )

            Button(
                onClick = {

                    if (
                        !uiState.guardando
                    ) {

                        origenExpandido =
                            false

                        destinoExpandido =
                            false

                        paradaExpandida =
                            -1

                        viewModel
                            .abrirNuevaRuta()
                    }
                },
                enabled =
                    !uiState.guardando,
                modifier =
                    Modifier.height(
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
                                Navy,
                            contentColor =
                                Color.White
                        )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,
                    contentDescription =
                        "Nueva ruta"
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            6.dp
                        )
                )

                Text(
                    text =
                        "Nueva ruta",
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    18.dp
                )
        )

        val mensaje =
            uiState.mensaje

        if (
            mensaje != null
        ) {

            MensajeRuta(
                texto =
                    mensaje,
                esError =
                    false,
                onCerrar = {

                    viewModel
                        .limpiarMensaje()
                }
            )

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
            error != null &&
            !uiState.mostrarFormulario
        ) {

            MensajeRuta(
                texto =
                    error,
                esError =
                    true,
                onCerrar = {

                    viewModel
                        .limpiarMensaje()
                }
            )

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )
        }

        if (
            uiState.isLoading
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 60.dp
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

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        )
            ) {

                Card(
                    modifier =
                        Modifier.width(
                            980.dp
                        ),
                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),
                    border =
                        BorderStroke(
                            width =
                                1.dp,
                            color =
                                Border
                        ),
                    colors =
                        CardDefaults
                            .cardColors(
                                containerColor =
                                    Surface
                            )
                ) {

                    Column {

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal =
                                            14.dp,
                                        vertical =
                                            14.dp
                                    ),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text =
                                    "Ruta",
                                modifier =
                                    Modifier.weight(
                                        3.2f
                                    ),
                                fontSize =
                                    13.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Muted
                            )

                            Text(
                                text =
                                    "Paradas",
                                modifier =
                                    Modifier.weight(
                                        2.2f
                                    ),
                                fontSize =
                                    13.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Muted
                            )

                            Text(
                                text =
                                    "Estado",
                                modifier =
                                    Modifier.weight(
                                        1.1f
                                    ),
                                fontSize =
                                    13.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Muted
                            )

                            Text(
                                text =
                                    "Editar",
                                modifier =
                                    Modifier.weight(
                                        0.7f
                                    ),
                                fontSize =
                                    13.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Muted,
                                textAlign =
                                    TextAlign.Center
                            )
                        }

                        HorizontalDivider(
                            color =
                                Border
                        )

                        if (
                            rutasFiltradas
                                .isEmpty()
                        ) {

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

                                Column(
                                    horizontalAlignment =
                                        Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Route,
                                        contentDescription =
                                            null,
                                        tint =
                                            Muted,
                                        modifier =
                                            Modifier.size(
                                                38.dp
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
                                            "No se encontraron rutas",
                                        fontSize =
                                            14.sp,
                                        color =
                                            Muted
                                    )
                                }
                            }

                        } else {

                            for (
                            ruta in rutasFiltradas
                            ) {

                                RutaFila(
                                    ruta =
                                        ruta,
                                    onEditar = {

                                        origenExpandido =
                                            false

                                        destinoExpandido =
                                            false

                                        paradaExpandida =
                                            -1

                                        viewModel
                                            .abrirEditarRuta(
                                                ruta
                                            )
                                    }
                                )

                                HorizontalDivider(
                                    color =
                                        Border
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    32.dp
                )
        )
    }

    if (
        uiState.mostrarFormulario
    ) {

        val origenNombre =
            obtenerNombreSede(
                sedes =
                    uiState.sedes,
                sedeId =
                    uiState.origenId
            )

        val destinoNombre =
            obtenerNombreSede(
                sedes =
                    uiState.sedes,
                sedeId =
                    uiState.destinoId
            )

        val editando =
            uiState.rutaEditandoId !=
                    null

        AlertDialog(
            onDismissRequest = {

                if (
                    !uiState.guardando
                ) {

                    origenExpandido =
                        false

                    destinoExpandido =
                        false

                    paradaExpandida =
                        -1

                    viewModel
                        .cerrarFormulario()
                }
            },
            title = {

                Text(
                    text =
                        if (
                            editando
                        ) {
                            "Editar ruta"
                        } else {
                            "Nueva ruta"
                        },
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Ink
                )
            },
            text = {

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(
                                rememberScrollState()
                            ),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            14.dp
                        )
                ) {

                    Text(
                        text =
                            "Ruta origen",
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Ink
                    )

                    ExposedDropdownMenuBox(
                        expanded =
                            origenExpandido,
                        onExpandedChange = {

                            if (
                                !uiState.guardando
                            ) {

                                origenExpandido =
                                    !origenExpandido

                                destinoExpandido =
                                    false

                                paradaExpandida =
                                    -1
                            }
                        }
                    ) {

                        OutlinedTextField(
                            value =
                                origenNombre,
                            onValueChange = {},
                            readOnly =
                                true,
                            enabled =
                                !uiState.guardando,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            placeholder = {

                                Text(
                                    text =
                                        "Selecciona sede",
                                    color =
                                        Muted
                                )
                            },
                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(
                                        expanded =
                                            origenExpandido
                                    )
                            },
                            singleLine =
                                true,
                            shape =
                                RoundedCornerShape(
                                    12.dp
                                ),
                            colors =
                                OutlinedTextFieldDefaults
                                    .colors(
                                        focusedBorderColor =
                                            Navy,
                                        unfocusedBorderColor =
                                            Border,
                                        focusedTextColor =
                                            Ink,
                                        unfocusedTextColor =
                                            Ink
                                    )
                        )

                        ExposedDropdownMenu(
                            expanded =
                                origenExpandido,
                            onDismissRequest = {

                                origenExpandido =
                                    false
                            }
                        ) {

                            for (
                            sede in uiState.sedes
                            ) {

                                val habilitada =
                                    sedeDisponible(
                                        sedeId =
                                            sede.id,
                                        origenId =
                                            uiState.origenId,
                                        destinoId =
                                            uiState.destinoId,
                                        paradas =
                                            uiState.paradasIds,
                                        posicion =
                                            -1
                                    )

                                DropdownMenuItem(
                                    text = {

                                        Text(
                                            text =
                                                sede.name
                                        )
                                    },
                                    enabled =
                                        habilitada ||
                                                uiState.origenId ==
                                                sede.id,
                                    onClick = {

                                        if (
                                            habilitada ||
                                            uiState.origenId ==
                                            sede.id
                                        ) {

                                            viewModel
                                                .seleccionarOrigen(
                                                    sede.id
                                                )

                                            origenExpandido =
                                                false
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        text =
                            "Paradas intermedias",
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Ink
                    )

                    if (
                        uiState.paradasIds
                            .isEmpty()
                    ) {

                        Surface(
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
                                    "Sin paradas intermedias. La ruta será directa.",
                                modifier =
                                    Modifier.padding(
                                        14.dp
                                    ),
                                fontSize =
                                    13.sp,
                                color =
                                    Navy
                            )
                        }
                    }

                    var indice =
                        0

                    while (
                        indice <
                        uiState.paradasIds.size
                    ) {

                        val paradaId =
                            uiState.paradasIds[
                                indice
                            ]

                        val paradaNombre =
                            obtenerNombreSede(
                                sedes =
                                    uiState.sedes,
                                sedeId =
                                    paradaId
                            )

                        val indiceActual =
                            indice

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),
                            verticalAlignment =
                                Alignment.CenterVertically,
                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    8.dp
                                )
                        ) {

                            Surface(
                                shape =
                                    RoundedCornerShape(
                                        20.dp
                                    ),
                                color =
                                    NavySoft
                            ) {

                                Text(
                                    text =
                                        "${indice + 2}",
                                    modifier =
                                        Modifier.padding(
                                            horizontal =
                                                10.dp,
                                            vertical =
                                                7.dp
                                        ),
                                    fontWeight =
                                        FontWeight.Bold,
                                    color =
                                        Navy
                                )
                            }

                            ExposedDropdownMenuBox(
                                expanded =
                                    paradaExpandida ==
                                            indice,
                                onExpandedChange = {

                                    if (
                                        !uiState.guardando
                                    ) {

                                        if (
                                            paradaExpandida ==
                                            indice
                                        ) {

                                            paradaExpandida =
                                                -1

                                        } else {

                                            paradaExpandida =
                                                indice
                                        }

                                        origenExpandido =
                                            false

                                        destinoExpandido =
                                            false
                                    }
                                },
                                modifier =
                                    Modifier.weight(
                                        1f
                                    )
                            ) {

                                OutlinedTextField(
                                    value =
                                        paradaNombre,
                                    onValueChange = {},
                                    readOnly =
                                        true,
                                    enabled =
                                        !uiState.guardando,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .menuAnchor(),
                                    placeholder = {

                                        Text(
                                            text =
                                                "Selecciona sede",
                                            color =
                                                Muted
                                        )
                                    },
                                    trailingIcon = {

                                        Icon(
                                            imageVector =
                                                Icons.Default
                                                    .ArrowDropDown,
                                            contentDescription =
                                                "Parada",
                                            tint =
                                                Muted
                                        )
                                    },
                                    singleLine =
                                        true,
                                    shape =
                                        RoundedCornerShape(
                                            12.dp
                                        ),
                                    colors =
                                        OutlinedTextFieldDefaults
                                            .colors(
                                                focusedBorderColor =
                                                    Navy,
                                                unfocusedBorderColor =
                                                    Border,
                                                focusedTextColor =
                                                    Ink,
                                                unfocusedTextColor =
                                                    Ink
                                            )
                                )

                                ExposedDropdownMenu(
                                    expanded =
                                        paradaExpandida ==
                                                indice,
                                    onDismissRequest = {

                                        paradaExpandida =
                                            -1
                                    }
                                ) {

                                    for (
                                    sede in uiState.sedes
                                    ) {

                                        val habilitada =
                                            sedeDisponible(
                                                sedeId =
                                                    sede.id,
                                                origenId =
                                                    uiState.origenId,
                                                destinoId =
                                                    uiState.destinoId,
                                                paradas =
                                                    uiState.paradasIds,
                                                posicion =
                                                    indiceActual
                                            )

                                        DropdownMenuItem(
                                            text = {

                                                Text(
                                                    text =
                                                        sede.name
                                                )
                                            },
                                            enabled =
                                                habilitada ||
                                                        paradaId ==
                                                        sede.id,
                                            onClick = {

                                                if (
                                                    habilitada ||
                                                    paradaId ==
                                                    sede.id
                                                ) {

                                                    viewModel
                                                        .seleccionarParada(
                                                            index =
                                                                indiceActual,
                                                            sedeId =
                                                                sede.id
                                                        )

                                                    paradaExpandida =
                                                        -1
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = {

                                    if (
                                        !uiState.guardando
                                    ) {

                                        viewModel
                                            .eliminarParada(
                                                indiceActual
                                            )

                                        paradaExpandida =
                                            -1
                                    }
                                },
                                enabled =
                                    !uiState.guardando
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Delete,
                                    contentDescription =
                                        "Eliminar parada",
                                    tint =
                                        Color(
                                            0xFFE53935
                                        )
                                )
                            }
                        }

                        indice++
                    }

                    OutlinedButton(
                        onClick = {

                            if (
                                !uiState.guardando
                            ) {

                                viewModel
                                    .agregarParada()

                                paradaExpandida =
                                    -1
                            }
                        },
                        enabled =
                            !uiState.guardando &&
                                    uiState.sedes.size >
                                    uiState.paradasIds.size + 2,
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),
                        border =
                            BorderStroke(
                                width =
                                    1.dp,
                                color =
                                    Navy
                            ),
                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(
                                    contentColor =
                                        Navy
                                )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Add,
                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    6.dp
                                )
                        )

                        Text(
                            text =
                                "Agregar parada intermedia",
                            fontWeight =
                                FontWeight.Bold
                        )
                    }

                    Text(
                        text =
                            "Ruta destino",
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Ink
                    )

                    ExposedDropdownMenuBox(
                        expanded =
                            destinoExpandido,
                        onExpandedChange = {

                            if (
                                !uiState.guardando
                            ) {

                                destinoExpandido =
                                    !destinoExpandido

                                origenExpandido =
                                    false

                                paradaExpandida =
                                    -1
                            }
                        }
                    ) {

                        OutlinedTextField(
                            value =
                                destinoNombre,
                            onValueChange = {},
                            readOnly =
                                true,
                            enabled =
                                !uiState.guardando,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            placeholder = {

                                Text(
                                    text =
                                        "Selecciona sede",
                                    color =
                                        Muted
                                )
                            },
                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(
                                        expanded =
                                            destinoExpandido
                                    )
                            },
                            singleLine =
                                true,
                            shape =
                                RoundedCornerShape(
                                    12.dp
                                ),
                            colors =
                                OutlinedTextFieldDefaults
                                    .colors(
                                        focusedBorderColor =
                                            Navy,
                                        unfocusedBorderColor =
                                            Border,
                                        focusedTextColor =
                                            Ink,
                                        unfocusedTextColor =
                                            Ink
                                    )
                        )

                        ExposedDropdownMenu(
                            expanded =
                                destinoExpandido,
                            onDismissRequest = {

                                destinoExpandido =
                                    false
                            }
                        ) {

                            for (
                            sede in uiState.sedes
                            ) {

                                val habilitada =
                                    sedeDisponible(
                                        sedeId =
                                            sede.id,
                                        origenId =
                                            uiState.origenId,
                                        destinoId =
                                            uiState.destinoId,
                                        paradas =
                                            uiState.paradasIds,
                                        posicion =
                                            -2
                                    )

                                DropdownMenuItem(
                                    text = {

                                        Text(
                                            text =
                                                sede.name
                                        )
                                    },
                                    enabled =
                                        habilitada ||
                                                uiState.destinoId ==
                                                sede.id,
                                    onClick = {

                                        if (
                                            habilitada ||
                                            uiState.destinoId ==
                                            sede.id
                                        ) {

                                            viewModel
                                                .seleccionarDestino(
                                                    sede.id
                                                )

                                            destinoExpandido =
                                                false
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Surface(
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
                                construirVistaPrevia(
                                    sedes =
                                        uiState.sedes,
                                    origenId =
                                        uiState.origenId,
                                    paradas =
                                        uiState.paradasIds,
                                    destinoId =
                                        uiState.destinoId
                                ),
                            modifier =
                                Modifier.padding(
                                    14.dp
                                ),
                            fontSize =
                                13.sp,
                            color =
                                Navy,
                            fontWeight =
                                FontWeight.Medium
                        )
                    }

                    if (
                        editando
                    ) {

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),
                            verticalAlignment =
                                Alignment.CenterVertically,
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Column(
                                modifier =
                                    Modifier.weight(
                                        1f
                                    )
                            ) {

                                Text(
                                    text =
                                        "Ruta activa",
                                    fontSize =
                                        14.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color =
                                        Ink
                                )

                                Text(
                                    text =
                                        if (
                                            uiState.rutaActiva
                                        ) {
                                            "Disponible para nuevos traslados"
                                        } else {
                                            "No disponible para nuevos traslados"
                                        },
                                    fontSize =
                                        12.sp,
                                    color =
                                        Muted
                                )
                            }

                            Switch(
                                checked =
                                    uiState.rutaActiva,
                                onCheckedChange = { valor ->

                                    if (
                                        !uiState.guardando
                                    ) {

                                        viewModel
                                            .cambiarEstadoRuta(
                                                valor
                                            )
                                    }
                                },
                                enabled =
                                    !uiState.guardando,
                                colors =
                                    SwitchDefaults
                                        .colors(
                                            checkedThumbColor =
                                                Color.White,
                                            checkedTrackColor =
                                                Navy
                                        )
                            )
                        }
                    }

                    val errorFormulario =
                        uiState.error

                    if (
                        errorFormulario !=
                        null
                    ) {

                        Surface(
                            modifier =
                                Modifier.fillMaxWidth(),
                            shape =
                                RoundedCornerShape(
                                    10.dp
                                ),
                            color =
                                Color(
                                    0xFFFFEBEE
                                )
                        ) {

                            Text(
                                text =
                                    errorFormulario,
                                modifier =
                                    Modifier.padding(
                                        12.dp
                                    ),
                                color =
                                    Color(
                                        0xFFC62828
                                    ),
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

                        if (
                            !uiState.guardando
                        ) {

                            origenExpandido =
                                false

                            destinoExpandido =
                                false

                            paradaExpandida =
                                -1

                            viewModel
                                .guardarRuta()
                        }
                    },
                    enabled =
                        !uiState.guardando,
                    modifier =
                        Modifier.fillMaxWidth(),
                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Navy,
                                contentColor =
                                    Color.White
                            )
                ) {

                    if (
                        uiState.guardando
                    ) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(
                                    20.dp
                                ),
                            strokeWidth =
                                2.dp,
                            color =
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
                                "Guardando..."
                        )

                    } else {

                        Text(
                            text =
                                if (
                                    editando
                                ) {
                                    "Guardar cambios"
                                } else {
                                    "Guardar ruta"
                                },
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {

                        if (
                            !uiState.guardando
                        ) {

                            origenExpandido =
                                false

                            destinoExpandido =
                                false

                            paradaExpandida =
                                -1

                            viewModel
                                .cerrarFormulario()
                        }
                    },
                    enabled =
                        !uiState.guardando
                ) {

                    Text(
                        text =
                            "Cancelar",
                        color =
                            Navy
                    )
                }
            },
            containerColor =
                Surface
        )
    }
}

@Composable
private fun RutaFila(
    ruta: Ruta,
    onEditar: () -> Unit
) {

    val sedesOrdenadas =
        ruta.sedes
            .sortedBy {
                it.orden
            }

    val recorrido =
        mutableListOf<String>()

    for (
    rutaSede in sedesOrdenadas
    ) {

        recorrido.add(
            rutaSede.sedeNombre
        )
    }

    val recorridoTexto =
        recorrido.joinToString(
            " → "
        )

    val paradas =
        mutableListOf<String>()

    if (
        recorrido.size > 2
    ) {

        var index =
            1

        while (
            index <
            recorrido.size - 1
        ) {

            paradas.add(
                recorrido[index]
            )

            index++
        }
    }

    val paradasTexto =
        if (
            paradas.isEmpty()
        ) {
            "Directa"
        } else {
            paradas.joinToString(
                ", "
            )
        }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                        14.dp,
                    vertical =
                        14.dp
                ),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Column(
            modifier =
                Modifier.weight(
                    3.2f
                )
        ) {

            Text(
                text =
                    recorridoTexto,
                fontSize =
                    14.sp,
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
                    ruta.name,
                fontSize =
                    11.sp,
                color =
                    Muted
            )
        }

        Text(
            text =
                paradasTexto,
            modifier =
                Modifier.weight(
                    2.2f
                ),
            fontSize =
                13.sp,
            color =
                Ink
        )

        Box(
            modifier =
                Modifier.weight(
                    1.1f
                )
        ) {

            EstadoRuta(
                active =
                    ruta.active
            )
        }

        Box(
            modifier =
                Modifier.weight(
                    0.7f
                ),
            contentAlignment =
                Alignment.Center
        ) {

            IconButton(
                onClick =
                    onEditar
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Edit,
                    contentDescription =
                        "Editar ruta",
                    tint =
                        Navy
                )
            }
        }
    }
}

@Composable
private fun EstadoRuta(
    active: Boolean
) {

    val texto =
        if (
            active
        ) {
            "Activa"
        } else {
            "Inactiva"
        }

    val fondo =
        if (
            active
        ) {
            NavySoft
        } else {
            Color(
                0xFFF2F2F2
            )
        }

    val textoColor =
        if (
            active
        ) {
            Navy
        } else {
            Muted
        }

    Surface(
        shape =
            RoundedCornerShape(
                20.dp
            ),
        color =
            fondo
    ) {

        Text(
            text =
                texto,
            modifier =
                Modifier.padding(
                    horizontal =
                        12.dp,
                    vertical =
                        6.dp
                ),
            fontSize =
                12.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                textoColor
        )
    }
}

@Composable
private fun MensajeRuta(
    texto: String,
    esError: Boolean,
    onCerrar: () -> Unit
) {

    val fondo =
        if (
            esError
        ) {
            Color(
                0xFFFFEBEE
            )
        } else {
            NavySoft
        }

    val colorTexto =
        if (
            esError
        ) {
            Color(
                0xFFC62828
            )
        } else {
            Navy
        }

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(
                12.dp
            ),
        colors =
            CardDefaults
                .cardColors(
                    containerColor =
                        fondo
                ),
        border =
            BorderStroke(
                width =
                    1.dp,
                color =
                    if (
                        esError
                    ) {
                        Color(
                            0xFFEF9A9A
                        )
                    } else {
                        Navy
                    }
            )
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        12.dp
                    ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text =
                    texto,
                modifier =
                    Modifier.weight(
                        1f
                    ),
                fontSize =
                    13.sp,
                color =
                    colorTexto
            )

            TextButton(
                onClick =
                    onCerrar
            ) {

                Text(
                    text =
                        "Cerrar",
                    color =
                        colorTexto
                )
            }
        }
    }
}

private fun obtenerNombreSede(
    sedes: List<Sede>,
    sedeId: Int
): String {

    if (
        sedeId <= 0
    ) {

        return ""
    }

    for (
    sede in sedes
    ) {

        if (
            sede.id ==
            sedeId
        ) {

            return sede.name
        }
    }

    return ""
}

private fun obtenerNombreSede(
    sedes: List<Sede>,
    sedeId: Int?
): String {

    if (
        sedeId == null
    ) {

        return ""
    }

    return obtenerNombreSede(
        sedes =
            sedes,
        sedeId =
            sedeId
    )
}

private fun sedeDisponible(
    sedeId: Int,
    origenId: Int?,
    destinoId: Int?,
    paradas: List<Int>,
    posicion: Int
): Boolean {

    if (
        sedeId <= 0
    ) {

        return false
    }

    if (
        posicion != -1
    ) {

        if (
            origenId != null
        ) {

            if (
                sedeId ==
                origenId
            ) {

                return false
            }
        }
    }

    if (
        posicion != -2
    ) {

        if (
            destinoId != null
        ) {

            if (
                sedeId ==
                destinoId
            ) {

                return false
            }
        }
    }

    var index =
        0

    while (
        index <
        paradas.size
    ) {

        if (
            index !=
            posicion
        ) {

            if (
                paradas[index] ==
                sedeId
            ) {

                return false
            }
        }

        index++
    }

    return true
}

private fun construirVistaPrevia(
    sedes: List<Sede>,
    origenId: Int?,
    paradas: List<Int>,
    destinoId: Int?
): String {

    val nombres =
        mutableListOf<String>()

    if (
        origenId != null
    ) {

        val nombreOrigen =
            obtenerNombreSede(
                sedes =
                    sedes,
                sedeId =
                    origenId
            )

        if (
            nombreOrigen.isNotBlank()
        ) {

            nombres.add(
                nombreOrigen
            )
        }
    }

    for (
    paradaId in paradas
    ) {

        if (
            paradaId > 0
        ) {

            val nombreParada =
                obtenerNombreSede(
                    sedes =
                        sedes,
                    sedeId =
                        paradaId
                )

            if (
                nombreParada.isNotBlank()
            ) {

                nombres.add(
                    nombreParada
                )
            }
        }
    }

    if (
        destinoId != null
    ) {

        val nombreDestino =
            obtenerNombreSede(
                sedes =
                    sedes,
                sedeId =
                    destinoId
            )

        if (
            nombreDestino.isNotBlank()
        ) {

            nombres.add(
                nombreDestino
            )
        }
    }

    if (
        nombres.isEmpty()
    ) {

        return "Selecciona las sedes de la ruta"
    }

    return nombres.joinToString(
        " → "
    )
}