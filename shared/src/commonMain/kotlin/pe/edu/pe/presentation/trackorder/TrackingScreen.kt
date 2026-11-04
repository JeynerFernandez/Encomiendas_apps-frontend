package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import org.koin.compose.viewmodel.koinViewModel

import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.NavySoft
import pe.edu.pe.presentation.theme.ScaledText

import pe.edu.pe.presentation.viewmodel.TrackingViewModel


@Composable
fun TrackingScreen(
    onBackClick: () -> Unit,
    viewModel:
    TrackingViewModel =
        koinViewModel()
) {

    val state by
    viewModel.uiState
        .collectAsState()


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    LightBackground
                )
                .statusBarsPadding()
                .padding(
                    horizontal =
                        20.dp
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
                    onBackClick,

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


            ScaledText(
                text =
                    "Rastrear pedido",

                fontSize =
                    25f,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    24.dp
                )
        )


        HorizontalDivider(
            color =
                Border
        )


        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        top =
                            24.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp
                )
        ) {

            ScaledText(
                text =
                    "Consulta el estado de tu encomienda",

                fontSize =
                    18f,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )


            ScaledText(
                text =
                    "Ingresa el código de rastreo y la clave de 4 dígitos.",

                fontSize =
                    13f,

                color =
                    Muted
            )


            OutlinedTextField(
                value =
                    state.codigo,

                onValueChange = {

                    viewModel
                        .cambiarCodigo(
                            it
                        )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {

                    ScaledText(
                        text =
                            "Código de rastreo",

                        fontSize =
                            14f
                    )
                },

                placeholder = {

                    ScaledText(
                        text =
                            "Ejemplo: MY-1F7476B9",

                        fontSize =
                            14f,

                        color =
                            Muted
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


            OutlinedTextField(
                value =
                    state.clave,

                onValueChange = {

                    viewModel
                        .cambiarClave(
                            it
                        )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {

                    ScaledText(
                        text =
                            "Clave de 4 dígitos",

                        fontSize =
                            14f
                    )
                },

                placeholder = {

                    ScaledText(
                        text =
                            "••••",

                        fontSize =
                            14f,

                        color =
                            Muted
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

                    viewModel
                        .rastrear()
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            56.dp
                        ),

                enabled =
                    state.codigo
                        .isNotBlank() &&
                            state.clave.length ==
                            4 &&
                            !state.isLoading,

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
                                Color.White,

                            disabledContainerColor =
                                NavySoft,

                            disabledContentColor =
                                Navy
                        )
            ) {

                if (
                    state.isLoading
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


                    Spacer(
                        modifier =
                            Modifier.width(
                                10.dp
                            )
                    )


                    ScaledText(
                        text =
                            "BUSCANDO...",

                        fontSize =
                            14f,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )

                } else {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            null
                    )


                    Spacer(
                        modifier =
                            Modifier.width(
                                8.dp
                            )
                    )


                    ScaledText(
                        text =
                            "RASTREAR PEDIDO",

                        fontSize =
                            15f,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )
                }
            }


            if (
                state.error
                    .isNotBlank()
            ) {

                ScaledText(
                    text =
                        state.error,

                    color =
                        Color(
                            0xFFE53935
                        ),

                    fontSize =
                        13f,

                    fontWeight =
                        FontWeight.Medium
                )
            }


            if (
                state.pedidoEncontrado
            ) {

                PedidoEncontrado(
                    rastreo =
                        state.resultado,

                    onActualizar = {

                        viewModel
                            .rastrear()
                    }
                )
            }


            Spacer(
                modifier =
                    Modifier.height(
                        40.dp
                    )
            )
        }
    }
}