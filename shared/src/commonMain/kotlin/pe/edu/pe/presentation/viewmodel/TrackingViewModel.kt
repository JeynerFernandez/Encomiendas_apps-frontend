package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import pe.edu.pe.domain.model.RastreoPedido
import pe.edu.pe.domain.usecase.TrackingUseCase


data class TrackingUiState(

    val codigo:
    String =
        "",

    val clave:
    String =
        "",

    val isLoading:
    Boolean =
        false,

    val pedidoEncontrado:
    Boolean =
        false,

    val error:
    String =
        "",

    val resultado:
    RastreoPedido =
        RastreoPedido()
)


class TrackingViewModel(
    private val useCase:
    TrackingUseCase
) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            TrackingUiState()
        )


    val uiState:
            StateFlow<TrackingUiState> =
        _uiState.asStateFlow()


    fun cambiarCodigo(
        valor: String
    ) {

        _uiState.update {

            it.copy(
                codigo =
                    valor.uppercase(),

                pedidoEncontrado =
                    false,

                error =
                    ""
            )
        }
    }


    fun cambiarClave(
        valor: String
    ) {

        var nuevaClave =
            ""


        for (
        caracter in valor
        ) {

            if (
                caracter.isDigit()
            ) {

                nuevaClave =
                    nuevaClave +
                            caracter
            }
        }


        if (
            nuevaClave.length >
            4
        ) {

            return
        }


        _uiState.update {

            it.copy(
                clave =
                    nuevaClave,

                pedidoEncontrado =
                    false,

                error =
                    ""
            )
        }
    }


    fun rastrear() {

        val estado =
            _uiState.value


        if (
            estado.isLoading
        ) {

            return
        }


        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading =
                        true,

                    pedidoEncontrado =
                        false,

                    error =
                        ""
                )
            }


            useCase.rastrear(
                codigoRastreo =
                    estado.codigo,

                clave =
                    estado.clave
            )
                .onSuccess {
                        resultado ->

                    _uiState.update {

                        it.copy(
                            isLoading =
                                false,

                            pedidoEncontrado =
                                true,

                            resultado =
                                resultado,

                            error =
                                ""
                        )
                    }
                }
                .onFailure {
                        exception ->

                    var mensaje =
                        "No se pudo rastrear la encomienda"


                    if (
                        exception.message !=
                        null
                    ) {

                        val message =
                            exception.message


                        if (
                            message != null &&
                            message.isNotBlank()
                        ) {

                            mensaje =
                                message
                        }
                    }


                    _uiState.update {

                        it.copy(
                            isLoading =
                                false,

                            pedidoEncontrado =
                                false,

                            error =
                                mensaje
                        )
                    }
                }
        }
    }
}