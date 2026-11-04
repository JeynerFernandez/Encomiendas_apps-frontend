package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.MovilidadUseCase
import pe.edu.pe.domain.model.TrasladoMovilidad


data class MovilidadUiState(
    val selectedTab: Int = 0,
    val conductorNombre: String = "",
    val traslados: List<TrasladoMovilidad> = emptyList(),
    val trasladoSeleccionadoId: Int = 0,
    val sedeSeleccionadaId: Int = 0,
    val busquedaSede: String = "",
    val isLoading: Boolean = false,
    val procesando: Boolean = false,
    val error: String = "",
    val mensaje: String = "",
    val tipoReporte: String = "RETRASO",
    val descripcionReporte: String = ""
)


class MovilidadViewModel(
    private val movilidadUseCase: MovilidadUseCase
) : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            MovilidadUiState()
        )


    val uiState: StateFlow<MovilidadUiState> =
        _uiState.asStateFlow()


    init {

        cargarTraslados()
    }


    fun cambiarTab(
        tab: Int
    ) {

        if (
            tab < 0 ||
            tab > 2
        ) {

            return
        }


        _uiState.update {

            it.copy(
                selectedTab =
                    tab,

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun cargarTraslados() {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading =
                        true,

                    error =
                        ""
                )
            }


            movilidadUseCase
                .getMisTraslados()
                .onSuccess {
                        resultado ->

                    var trasladoId =
                        _uiState.value
                            .trasladoSeleccionadoId


                    var existeSeleccionado =
                        false


                    for (
                    traslado in
                    resultado.traslados
                    ) {

                        if (
                            traslado.id ==
                            trasladoId
                        ) {

                            existeSeleccionado =
                                true

                            break
                        }
                    }


                    if (
                        !existeSeleccionado
                    ) {

                        trasladoId =
                            obtenerTrasladoPrincipal(
                                resultado.traslados
                            )
                    }


                    var sedeId =
                        0


                    for (
                    traslado in
                    resultado.traslados
                    ) {

                        if (
                            traslado.id ==
                            trasladoId
                        ) {

                            sedeId =
                                obtenerSedeInicial(
                                    traslado
                                )

                            break
                        }
                    }


                    _uiState.update {

                        it.copy(
                            conductorNombre =
                                resultado
                                    .conductor
                                    .name,

                            traslados =
                                resultado
                                    .traslados,

                            trasladoSeleccionadoId =
                                trasladoId,

                            sedeSeleccionadaId =
                                sedeId,

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
                                obtenerMensajeError(
                                    exception,
                                    "No se pudieron cargar los traslados"
                                )
                        )
                    }
                }
        }
    }


    fun seleccionarTraslado(
        trasladoId: Int
    ) {

        if (
            trasladoId <= 0
        ) {

            return
        }


        var sedeId =
            0


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                sedeId =
                    obtenerSedeInicial(
                        traslado
                    )

                break
            }
        }


        _uiState.update {

            it.copy(
                trasladoSeleccionadoId =
                    trasladoId,

                sedeSeleccionadaId =
                    sedeId,

                busquedaSede =
                    "",

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun seleccionarSede(
        sedeId: Int
    ) {

        if (
            sedeId <= 0
        ) {

            return
        }


        _uiState.update {

            it.copy(
                sedeSeleccionadaId =
                    sedeId,

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun cambiarBusquedaSede(
        valor: String
    ) {

        _uiState.update {

            it.copy(
                busquedaSede =
                    valor
            )
        }
    }


    fun iniciarTraslado() {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        if (
            trasladoId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado"
                )
            }

            return
        }


        var estado =
            ""

        var tieneEncomiendas =
            false


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                estado =
                    traslado.estado
                        .trim()
                        .uppercase()


                tieneEncomiendas =
                    traslado.encomiendas
                        .isNotEmpty()

                break
            }
        }


        if (
            estado !=
            "PROGRAMADO"
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Solo se puede iniciar un traslado programado"
                )
            }

            return
        }


        if (
            !tieneEncomiendas
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "El traslado no tiene encomiendas asignadas"
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


            movilidadUseCase
                .iniciarTraslado(
                    trasladoId
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(
                            mensaje =
                                "Traslado iniciado correctamente",

                            error =
                                ""
                        )
                    }


                    cargarTraslados()
                }
                .onFailure {
                        exception ->

                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            error =
                                obtenerMensajeError(
                                    exception,
                                    "No se pudo iniciar el traslado"
                                )
                        )
                    }
                }
        }
    }


    fun descargarEnSede() {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        val sedeId =
            _uiState.value
                .sedeSeleccionadaId


        if (
            trasladoId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado"
                )
            }

            return
        }


        if (
            sedeId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione una sede"
                )
            }

            return
        }


        var trasladoEncontrado =
            false

        var trasladoEnCurso =
            false

        var cantidadPendiente =
            0


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                trasladoEncontrado =
                    true


                if (
                    traslado.estado
                        .trim()
                        .uppercase() ==
                    "EN_CURSO"
                ) {

                    trasladoEnCurso =
                        true
                }


                for (
                detalle in
                traslado.encomiendas
                ) {

                    if (
                        detalle.descargadoAt
                            .isBlank() &&
                        detalle.encomienda
                            .sedeDestino
                            .id ==
                        sedeId
                    ) {

                        cantidadPendiente =
                            cantidadPendiente +
                                    1
                    }
                }


                break
            }
        }


        if (
            !trasladoEncontrado
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "El traslado seleccionado no existe"
                )
            }

            return
        }


        if (
            !trasladoEnCurso
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "El traslado debe encontrarse en curso"
                )
            }

            return
        }


        if (
            cantidadPendiente ==
            0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "No existen encomiendas pendientes para esta sede"
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


            movilidadUseCase
                .descargarPorSede(
                    trasladoId =
                        trasladoId,

                    sedeId =
                        sedeId
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(
                            mensaje =
                                "Encomiendas descargadas correctamente",

                            error =
                                ""
                        )
                    }


                    cargarTraslados()
                }
                .onFailure {
                        exception ->

                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            error =
                                obtenerMensajeError(
                                    exception,
                                    "No se pudieron descargar las encomiendas"
                                )
                        )
                    }
                }
        }
    }


    fun finalizarTraslado() {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        if (
            trasladoId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado"
                )
            }

            return
        }


        var trasladoEncontrado =
            false

        var estado =
            ""

        var pendientes =
            0


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                trasladoEncontrado =
                    true

                estado =
                    traslado.estado
                        .trim()
                        .uppercase()

                pendientes =
                    traslado.resumen
                        .pendientesDescarga

                break
            }
        }


        if (
            !trasladoEncontrado
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "El traslado seleccionado no existe"
                )
            }

            return
        }


        if (
            estado !=
            "EN_CURSO"
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Solo se puede finalizar un traslado en curso"
                )
            }

            return
        }


        if (
            pendientes >
            0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Todavía existen encomiendas pendientes de descargar"
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


            movilidadUseCase
                .finalizarTraslado(
                    trasladoId
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(
                            mensaje =
                                "Traslado finalizado correctamente",

                            error =
                                ""
                        )
                    }


                    cargarTraslados()
                }
                .onFailure {
                        exception ->

                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            error =
                                obtenerMensajeError(
                                    exception,
                                    "No se pudo finalizar el traslado"
                                )
                        )
                    }
                }
        }
    }


    fun retornarASedeCentral() {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        if (
            trasladoId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado"
                )
            }

            return
        }


        var trasladoEncontrado =
            false

        var estado =
            ""

        var pendientes =
            0


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                trasladoEncontrado =
                    true

                estado =
                    traslado.estado
                        .trim()
                        .uppercase()

                pendientes =
                    traslado.resumen
                        .pendientesDescarga

                break
            }
        }


        if (
            !trasladoEncontrado
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "El traslado seleccionado no existe"
                )
            }

            return
        }


        if (
            estado !=
            "FINALIZADO"
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Solo puede retornar a sede central después de finalizar el traslado"
                )
            }

            return
        }


        if (
            pendientes >
            0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Todavía existen encomiendas pendientes de descargar"
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


            movilidadUseCase
                .retornarASedeCentral(
                    trasladoId
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(
                            trasladoSeleccionadoId =
                                0,

                            sedeSeleccionadaId =
                                0,

                            busquedaSede =
                                "",

                            procesando =
                                false,

                            error =
                                "",

                            mensaje =
                                "Retorno registrado. La movilidad está disponible nuevamente."
                        )
                    }


                    cargarTraslados()
                }
                .onFailure {
                        exception ->

                    _uiState.update {

                        it.copy(
                            procesando =
                                false,

                            error =
                                obtenerMensajeError(
                                    exception,
                                    "No se pudo registrar el retorno a sede central"
                                )
                        )
                    }
                }
        }
    }


    fun actualizarUbicacion(
        latitud: Double,
        longitud: Double
    ) {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        if (
            trasladoId <= 0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado"
                )
            }

            return
        }


        var trasladoEnCurso =
            false


        for (
        traslado in
        _uiState.value.traslados
        ) {

            if (
                traslado.id ==
                trasladoId
            ) {

                if (
                    traslado.estado
                        .trim()
                        .uppercase() ==
                    "EN_CURSO"
                ) {

                    trasladoEnCurso =
                        true
                }

                break
            }
        }


        if (
            !trasladoEnCurso
        ) {

            return
        }


        viewModelScope.launch {

            movilidadUseCase
                .actualizarUbicacion(
                    trasladoId =
                        trasladoId,

                    latitud =
                        latitud,

                    longitud =
                        longitud
                )
                .onFailure {
                        exception ->

                    _uiState.update {

                        it.copy(
                            error =
                                obtenerMensajeError(
                                    exception,
                                    "No se pudo actualizar la ubicación"
                                )
                        )
                    }
                }
        }
    }


    fun cambiarTipoReporte(
        tipo: String
    ) {

        if (
            tipo !=
            "RETRASO" &&
            tipo !=
            "DANIO" &&
            tipo !=
            "EXTRAVIO"
        ) {

            return
        }


        _uiState.update {

            it.copy(
                tipoReporte =
                    tipo,

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun cambiarDescripcionReporte(
        descripcion: String
    ) {

        if (
            descripcion.length >
            1000
        ) {

            return
        }


        _uiState.update {

            it.copy(
                descripcionReporte =
                    descripcion,

                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    fun validarReporte() {

        val trasladoId =
            _uiState.value
                .trasladoSeleccionadoId


        val descripcion =
            _uiState.value
                .descripcionReporte
                .trim()


        if (
            trasladoId <=
            0
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Seleccione un traslado antes de registrar un reporte"
                )
            }

            return
        }


        if (
            descripcion.isBlank()
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "Ingrese la descripción del reporte"
                )
            }

            return
        }


        if (
            descripcion.length <
            5
        ) {

            _uiState.update {

                it.copy(
                    error =
                        "La descripción debe contener al menos 5 caracteres"
                )
            }

            return
        }


        _uiState.update {

            it.copy(
                mensaje =
                    "Reporte validado. Falta conectar el DTO de incidencias del backend",

                error =
                    ""
            )
        }
    }


    fun limpiarMensajes() {

        _uiState.update {

            it.copy(
                error =
                    "",

                mensaje =
                    ""
            )
        }
    }


    private fun obtenerTrasladoPrincipal(
        traslados: List<TrasladoMovilidad>
    ): Int {

        for (
        traslado in
        traslados
        ) {

            if (
                traslado.estado
                    .trim()
                    .uppercase() ==
                "EN_CURSO"
            ) {

                return traslado.id
            }
        }


        for (
        traslado in
        traslados
        ) {

            if (
                traslado.estado
                    .trim()
                    .uppercase() ==
                "PROGRAMADO"
            ) {

                return traslado.id
            }
        }


        for (
        traslado in
        traslados
        ) {

            if (
                traslado.estado
                    .trim()
                    .uppercase() ==
                "FINALIZADO"
            ) {

                return traslado.id
            }
        }


        if (
            traslados.isNotEmpty()
        ) {

            return traslados[0].id
        }


        return 0
    }


    private fun obtenerSedeInicial(
        traslado: TrasladoMovilidad
    ): Int {

        for (
        detalle in
        traslado.encomiendas
        ) {

            if (
                detalle.descargadoAt
                    .isBlank()
            ) {

                return detalle
                    .encomienda
                    .sedeDestino
                    .id
            }
        }


        if (
            traslado.ruta
                .recorrido
                .size >
            1
        ) {

            return traslado.ruta
                .recorrido[1]
                .sede
                .id
        }


        if (
            traslado.ruta
                .recorrido
                .isNotEmpty()
        ) {

            return traslado.ruta
                .recorrido[0]
                .sede
                .id
        }


        return 0
    }


    private fun obtenerMensajeError(
        exception: Throwable,
        defecto: String
    ): String {

        val detalle =
            exception.message


        if (
            detalle != null &&
            detalle.isNotBlank()
        ) {

            return detalle
        }


        return defecto
    }
}