package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import pe.edu.pe.domain.model.EncomiendaPendienteEntrega
import pe.edu.pe.domain.UseCase.EntregaRecepcionUseCase


data class EntregaRecepcionUiState(

    val sedeId: Int = 0,

    val total: Int = 0,

    val encomiendas:
    List<EncomiendaPendienteEntrega> =
        emptyList(),

    val busqueda: String = "",

    val encomiendaSeleccionadaId:
    Int = 0,

    val dni: String = "",

    val clave: String = "",

    val mostrarDialogo:
    Boolean = false,

    val isLoading:
    Boolean = false,

    val procesando:
    Boolean = false,

    val error:
    String = "",

    val mensaje:
    String = ""
)


class EntregaRecepcionViewModel(
    private val useCase:
    EntregaRecepcionUseCase
) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            EntregaRecepcionUiState()
        )


    val uiState:
            StateFlow<EntregaRecepcionUiState> =
        _uiState.asStateFlow()


    init {

        cargarPendientes()
    }


    fun cargarPendientes() {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading =
                        true,

                    error =
                        ""
                )
            }


            useCase
                .getPendientes()
                .onSuccess {
                        resultado ->


                    _uiState.update {

                        it.copy(
                            sedeId =
                                resultado.sedeId,

                            total =
                                resultado.total,

                            encomiendas =
                                resultado.encomiendas,

                            isLoading =
                                false,

                            procesando =
                                false,

                            error =
                                ""
                        )
                    }
                }
                .onFailure {
                        exception ->


                    _uiState.update {

                        it.copy(
                            isLoading =
                                false,

                            procesando =
                                false,

                            error =
                                obtenerMensaje(
                                    exception,
                                    "No se pudieron cargar las encomiendas"
                                )
                        )
                    }
                }
        }
    }


    fun cambiarBusqueda(
        valor: String
    ) {

        _uiState.update {

            it.copy(
                busqueda =
                    valor
            )
        }
    }


    fun abrirEntrega(
        encomiendaId: Int
    ) {

        if (
            encomiendaId <= 0
        ) {

            return
        }


        _uiState.update {

            it.copy(
                encomiendaSeleccionadaId =
                    encomiendaId,

                dni =
                    "",

                clave =
                    "",

                mostrarDialogo =
                    true,

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun cerrarEntrega() {

        if (
            _uiState.value
                .procesando
        ) {

            return
        }


        _uiState.update {

            it.copy(
                encomiendaSeleccionadaId =
                    0,

                dni =
                    "",

                clave =
                    "",

                mostrarDialogo =
                    false,

                error =
                    ""
            )
        }
    }


    fun cambiarDni(
        valor: String
    ) {

        var nuevoValor =
            ""


        for (
        caracter in valor
        ) {

            if (
                caracter.isDigit()
            ) {

                nuevoValor =
                    nuevoValor +
                            caracter
            }
        }


        if (
            nuevoValor.length >
            8
        ) {

            return
        }


        _uiState.update {

            it.copy(
                dni =
                    nuevoValor,

                error =
                    ""
            )
        }
    }


    fun cambiarClave(
        valor: String
    ) {

        var nuevoValor =
            ""


        for (
        caracter in valor
        ) {

            if (
                caracter.isDigit()
            ) {

                nuevoValor =
                    nuevoValor +
                            caracter
            }
        }


        if (
            nuevoValor.length >
            4
        ) {

            return
        }


        _uiState.update {

            it.copy(
                clave =
                    nuevoValor,

                error =
                    ""
            )
        }
    }


    fun confirmarEntrega() {

        val estado =
            _uiState.value


        if (
            estado.encomiendaSeleccionadaId <=
            0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "No existe una encomienda seleccionada"
                )
            }

            return
        }


        if (
            estado.dni.length !=
            8
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Ingrese un DNI válido de 8 dígitos"
                )
            }

            return
        }


        if (
            estado.clave.length !=
            4
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Ingrese la clave de 4 dígitos"
                )
            }

            return
        }


        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    procesando =
                        true,

                    error =
                        "",

                    mensaje =
                        ""
                )
            }


            useCase
                .entregar(

                    encomiendaId =
                        estado
                            .encomiendaSeleccionadaId,

                    dni =
                        estado.dni,

                    clave =
                        estado.clave
                )
                .onSuccess {
                        mensaje ->


                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            mostrarDialogo =
                                false,

                            encomiendaSeleccionadaId =
                                0,

                            dni =
                                "",

                            clave =
                                "",

                            mensaje =
                                mensaje,

                            error =
                                ""
                        )
                    }


                    cargarPendientes()
                }
                .onFailure {
                        exception ->


                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            error =
                                obtenerMensaje(
                                    exception,
                                    "No se pudo realizar la entrega"
                                )
                        )
                    }
                }
        }
    }


    fun limpiarMensaje() {

        _uiState.update {

            it.copy(
                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    private fun obtenerMensaje(
        exception: Throwable,
        mensajeDefecto: String
    ): String {

        val message =
            exception.message


        if (
            message != null &&
            message.isNotBlank()
        ) {

            return message
        }


        return mensajeDefecto
    }
}