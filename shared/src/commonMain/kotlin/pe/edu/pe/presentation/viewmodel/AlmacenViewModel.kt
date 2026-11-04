package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.domain.model.EncomiendaAlmacen
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.model.TrasladoDisponibleAlmacen
import pe.edu.pe.domain.repository.SedeRepository
import pe.edu.pe.domain.usecase.AlmacenUseCase

data class AlmacenUiState(
    val sedeActualId: Int? = null,
    val sedeActualNombre: String = "",
    val sedesDestino: List<Sede> = emptyList(),
    val sedeDestinoSeleccionada: Sede? = null,
    val filtro: String = "pendientes",
    val encomiendas: List<EncomiendaAlmacen> = emptyList(),
    val total: Int = 0,
    val isLoading: Boolean = false,
    val procesandoCodigo: String? = null,
    val error: String? = null,
    val mensaje: String? = null,
    val encomiendaSeleccionada: EncomiendaAlmacen? = null,
    val trasladosDisponibles: List<TrasladoDisponibleAlmacen> = emptyList(),
    val mostrarSelectorMovilidad: Boolean = false,
    val cargandoMovilidades: Boolean = false,
    val asignandoTrasladoId: Int? = null
)

class AlmacenViewModel(
    private val almacenUseCase: AlmacenUseCase,
    private val sedeRepository: SedeRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AlmacenUiState()
        )

    val uiState: StateFlow<AlmacenUiState> =
        _uiState.asStateFlow()

    init {
        cargarDatosIniciales()
    }

    private fun cargarDatosIniciales() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    mensaje = null
                )
            }

            val usuario =
                sessionManager.getUser()

            if (
                usuario == null
            ) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No existe una sesión activa"
                    )
                }

                return@launch
            }

            val sedeId =
                usuario.sedeId

            if (
                sedeId == null
            ) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "El usuario no tiene una sede asignada"
                    )
                }

                return@launch
            }

            if (
                sedeId <= 0
            ) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "La sede asignada al usuario no es válida"
                    )
                }

                return@launch
            }

            sedeRepository
                .getSedes()
                .onSuccess { sedes ->

                    var sedeActualNombre =
                        "Sede $sedeId"

                    val destinos =
                        mutableListOf<Sede>()

                    for (
                    sede in sedes
                    ) {

                        if (
                            sede.id == sedeId
                        ) {

                            sedeActualNombre =
                                sede.name
                        }

                        if (
                            sede.active &&
                            sede.id != sedeId
                        ) {

                            destinos.add(
                                sede
                            )
                        }
                    }

                    _uiState.update {
                        it.copy(
                            sedeActualId = sedeId,
                            sedeActualNombre = sedeActualNombre,
                            sedesDestino = destinos,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    val mensajeError =
                        obtenerMensajeError(
                            exception = exception,
                            mensajePorDefecto =
                                "Error al cargar las sedes"
                        )

                    _uiState.update {
                        it.copy(
                            sedeActualId = sedeId,
                            isLoading = false,
                            error = mensajeError
                        )
                    }
                }
        }
    }

    fun seleccionarDestino(
        sede: Sede
    ) {

        if (
            sede.id <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La sede seleccionada no es válida",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            !sede.active
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La sede seleccionada está inactiva",
                    mensaje =
                        null
                )
            }

            return
        }

        val sedeActualId =
            _uiState.value.sedeActualId

        if (
            sedeActualId != null &&
            sede.id == sedeActualId
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La sede destino debe ser diferente a la sede actual",
                    mensaje =
                        null
                )
            }

            return
        }

        _uiState.update {
            it.copy(
                sedeDestinoSeleccionada = sede,
                encomiendas = emptyList(),
                total = 0,
                error = null,
                mensaje = null,
                mostrarSelectorMovilidad = false,
                encomiendaSeleccionada = null,
                trasladosDisponibles = emptyList(),
                cargandoMovilidades = false,
                asignandoTrasladoId = null
            )
        }

        cargarEncomiendas()
    }

    fun cambiarFiltro(
        filtro: String
    ) {

        val filtroNormalizado =
            filtro
                .trim()
                .lowercase()

        if (
            filtroNormalizado != "pendientes" &&
            filtroNormalizado != "asignadas" &&
            filtroNormalizado != "todas"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "Filtro no válido",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            _uiState.value.filtro ==
            filtroNormalizado
        ) {

            return
        }

        _uiState.update {
            it.copy(
                filtro = filtroNormalizado,
                encomiendas = emptyList(),
                total = 0,
                error = null,
                mensaje = null,
                mostrarSelectorMovilidad = false,
                encomiendaSeleccionada = null,
                trasladosDisponibles = emptyList(),
                cargandoMovilidades = false,
                asignandoTrasladoId = null
            )
        }

        val destino =
            _uiState.value
                .sedeDestinoSeleccionada

        if (
            destino != null
        ) {

            cargarEncomiendas()
        }
    }

    fun cargarEncomiendas(
        mantenerMensaje: Boolean = false
    ) {

        val destino =
            _uiState.value
                .sedeDestinoSeleccionada

        if (
            destino == null
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "Seleccione una sede destino",
                    encomiendas =
                        emptyList(),
                    total =
                        0
                )
            }

            return
        }

        val destinoId =
            destino.id

        if (
            destinoId <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La sede destino no es válida",
                    encomiendas =
                        emptyList(),
                    total =
                        0
                )
            }

            return
        }

        val filtro =
            _uiState.value.filtro

        viewModelScope.launch {

            _uiState.update {

                var mensajeActual =
                    it.mensaje

                if (
                    !mantenerMensaje
                ) {
                    mensajeActual =
                        null
                }

                it.copy(
                    isLoading = true,
                    error = null,
                    mensaje = mensajeActual
                )
            }

            almacenUseCase
                .getEncomiendas(
                    destinoId = destinoId,
                    filtro = filtro
                )
                .onSuccess { resultado ->

                    val estadoActual =
                        _uiState.value

                    val destinoActual =
                        estadoActual
                            .sedeDestinoSeleccionada

                    var mismaConsulta =
                        false

                    if (
                        destinoActual != null
                    ) {

                        if (
                            destinoActual.id ==
                            destinoId &&
                            estadoActual.filtro ==
                            filtro
                        ) {

                            mismaConsulta =
                                true
                        }
                    }

                    if (
                        mismaConsulta
                    ) {

                        _uiState.update {
                            it.copy(
                                encomiendas =
                                    resultado.encomiendas,
                                total =
                                    resultado.total,
                                sedeActualNombre =
                                    resultado.sedeOrigen.name,
                                isLoading =
                                    false,
                                error =
                                    null
                            )
                        }
                    }
                }
                .onFailure { exception ->

                    val mensajeError =
                        obtenerMensajeError(
                            exception = exception,
                            mensajePorDefecto =
                                "Error al obtener las encomiendas"
                        )

                    val estadoActual =
                        _uiState.value

                    val destinoActual =
                        estadoActual
                            .sedeDestinoSeleccionada

                    var mismaConsulta =
                        false

                    if (
                        destinoActual != null
                    ) {

                        if (
                            destinoActual.id ==
                            destinoId &&
                            estadoActual.filtro ==
                            filtro
                        ) {

                            mismaConsulta =
                                true
                        }
                    }

                    if (
                        mismaConsulta
                    ) {

                        _uiState.update {
                            it.copy(
                                encomiendas =
                                    emptyList(),
                                total =
                                    0,
                                isLoading =
                                    false,
                                error =
                                    mensajeError
                            )
                        }
                    }
                }
        }
    }

    fun ingresarAlmacen(
        encomienda: EncomiendaAlmacen
    ) {

        if (
            encomienda.id <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda seleccionada no es válida",
                    mensaje =
                        null
                )
            }

            return
        }

        val codigo =
            encomienda.codigoRastreo
                .trim()
                .uppercase()

        if (
            codigo.isBlank()
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "Código de rastreo no válido",
                    mensaje =
                        null
                )
            }

            return
        }

        val estado =
            encomienda.estado
                .trim()
                .uppercase()

        if (
            estado != "REGISTRADO" &&
            estado != "CAMINO_ALMACEN"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda no puede ingresar al almacén en su estado actual",
                    mensaje =
                        null
                )
            }

            return
        }

        var modalidadPago =
            ""

        val modalidad =
            encomienda.modalidadPago

        if (
            modalidad != null
        ) {

            modalidadPago =
                modalidad
                    .trim()
                    .uppercase()
        }

        var estadoPago =
            ""

        val pago =
            encomienda.estadoPago

        if (
            pago != null
        ) {

            estadoPago =
                pago
                    .trim()
                    .uppercase()
        }

        if (
            modalidadPago == "ORIGEN" &&
            estadoPago != "PAGADO"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda debe ser pagada en origen antes de ingresar al almacén",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            modalidadPago.isNotBlank() &&
            modalidadPago != "ORIGEN" &&
            modalidadPago != "DESTINO"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La modalidad de pago de la encomienda no es válida",
                    mensaje =
                        null
                )
            }

            return
        }

        ingresarAlmacenBackend(
            codigo
        )
    }

    private fun ingresarAlmacenBackend(
        codigo: String
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    procesandoCodigo = codigo,
                    error = null,
                    mensaje = null
                )
            }

            almacenUseCase
                .ingresarAlmacen(
                    codigoRastreo = codigo
                )
                .onSuccess { resultado ->

                    _uiState.update {
                        it.copy(
                            procesandoCodigo = null,
                            mensaje = resultado.message,
                            error = null
                        )
                    }

                    cargarEncomiendas(
                        mantenerMensaje = true
                    )
                }
                .onFailure { exception ->

                    val mensajeError =
                        obtenerMensajeError(
                            exception = exception,
                            mensajePorDefecto =
                                "No se pudo ingresar la encomienda al almacén"
                        )

                    _uiState.update {
                        it.copy(
                            procesandoCodigo = null,
                            error = mensajeError,
                            mensaje = null
                        )
                    }
                }
        }
    }

    fun abrirSelectorMovilidad(
        encomienda: EncomiendaAlmacen
    ) {

        if (
            encomienda.id <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda seleccionada no es válida",
                    mensaje =
                        null
                )
            }

            return
        }

        val destino =
            _uiState.value
                .sedeDestinoSeleccionada

        if (
            destino == null
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "Seleccione una sede destino",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            encomienda.sedeDestinoId !=
            destino.id
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda no corresponde al destino seleccionado",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            encomienda.estado
                .trim()
                .uppercase() !=
            "EN_ALMACEN"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda debe encontrarse en almacén antes de asignar una movilidad",
                    mensaje =
                        null
                )
            }

            return
        }

        val trasladoAsignado =
            encomienda.trasladoAsignado

        if (
            trasladoAsignado != null
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda ya tiene una movilidad asignada",
                    mensaje =
                        null
                )
            }

            return
        }

        val destinoId =
            destino.id

        _uiState.update {
            it.copy(
                encomiendaSeleccionada = encomienda,
                mostrarSelectorMovilidad = true,
                trasladosDisponibles = emptyList(),
                cargandoMovilidades = true,
                asignandoTrasladoId = null,
                error = null,
                mensaje = null
            )
        }

        viewModelScope.launch {

            almacenUseCase
                .getTrasladosDisponibles(
                    destinoId = destinoId
                )
                .onSuccess { resultado ->

                    val seleccionada =
                        _uiState.value
                            .encomiendaSeleccionada

                    if (
                        seleccionada != null &&
                        seleccionada.id ==
                        encomienda.id &&
                        _uiState.value
                            .mostrarSelectorMovilidad
                    ) {

                        _uiState.update {
                            it.copy(
                                trasladosDisponibles =
                                    resultado.traslados,
                                cargandoMovilidades =
                                    false,
                                error =
                                    null
                            )
                        }
                    }
                }
                .onFailure { exception ->

                    val mensajeError =
                        obtenerMensajeError(
                            exception = exception,
                            mensajePorDefecto =
                                "No se pudieron cargar las movilidades disponibles"
                        )

                    val seleccionada =
                        _uiState.value
                            .encomiendaSeleccionada

                    if (
                        seleccionada != null &&
                        seleccionada.id ==
                        encomienda.id
                    ) {

                        _uiState.update {
                            it.copy(
                                trasladosDisponibles =
                                    emptyList(),
                                cargandoMovilidades =
                                    false,
                                error =
                                    mensajeError
                            )
                        }
                    }
                }
        }
    }

    fun cerrarSelectorMovilidad() {

        _uiState.update {
            it.copy(
                mostrarSelectorMovilidad = false,
                encomiendaSeleccionada = null,
                trasladosDisponibles = emptyList(),
                cargandoMovilidades = false,
                asignandoTrasladoId = null
            )
        }
    }

    fun asignarMovilidad(
        trasladoId: Int
    ) {

        if (
            trasladoId <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "El traslado seleccionado no es válido",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            _uiState.value
                .asignandoTrasladoId != null
        ) {

            return
        }

        val encomienda =
            _uiState.value
                .encomiendaSeleccionada

        if (
            encomienda == null
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "No existe una encomienda seleccionada",
                    mensaje =
                        null
                )
            }

            return
        }

        if (
            encomienda.estado
                .trim()
                .uppercase() !=
            "EN_ALMACEN"
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda debe encontrarse en almacén antes de asignar movilidad",
                    mensaje =
                        null
                )
            }

            return
        }

        val trasladoActual =
            encomienda.trasladoAsignado

        if (
            trasladoActual != null
        ) {

            _uiState.update {
                it.copy(
                    asignandoTrasladoId =
                        null,
                    mostrarSelectorMovilidad =
                        false,
                    encomiendaSeleccionada =
                        null,
                    trasladosDisponibles =
                        emptyList(),
                    cargandoMovilidades =
                        false,
                    error =
                        "La encomienda ya tiene una movilidad asignada",
                    mensaje =
                        null
                )
            }

            cargarEncomiendas()

            return
        }

        val encomiendaId =
            encomienda.id

        if (
            encomiendaId <= 0
        ) {

            _uiState.update {
                it.copy(
                    error =
                        "La encomienda seleccionada no es válida",
                    mensaje =
                        null
                )
            }

            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    asignandoTrasladoId = trasladoId,
                    error = null,
                    mensaje = null
                )
            }

            almacenUseCase
                .asignarEncomiendaATraslado(
                    trasladoId = trasladoId,
                    encomiendaId = encomiendaId
                )
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            asignandoTrasladoId =
                                null,
                            mostrarSelectorMovilidad =
                                false,
                            encomiendaSeleccionada =
                                null,
                            trasladosDisponibles =
                                emptyList(),
                            cargandoMovilidades =
                                false,
                            mensaje =
                                "Movilidad asignada correctamente",
                            error =
                                null
                        )
                    }

                    cargarEncomiendas(
                        mantenerMensaje = true
                    )
                }
                .onFailure { exception ->

                    val mensajeError =
                        obtenerMensajeError(
                            exception = exception,
                            mensajePorDefecto =
                                "No se pudo asignar la movilidad"
                        )

                    var encomiendaYaAsignada =
                        false

                    val mensajeNormalizado =
                        mensajeError
                            .trim()
                            .lowercase()

                    if (
                        mensajeNormalizado.contains(
                            "ya está asignada"
                        ) ||
                        mensajeNormalizado.contains(
                            "ya esta asignada"
                        ) ||
                        mensajeNormalizado.contains(
                            "ya tiene una movilidad"
                        ) ||
                        mensajeNormalizado.contains(
                            "asignación activa"
                        ) ||
                        mensajeNormalizado.contains(
                            "asignacion activa"
                        )
                    ) {

                        encomiendaYaAsignada =
                            true
                    }

                    if (
                        encomiendaYaAsignada
                    ) {

                        _uiState.update {
                            it.copy(
                                asignandoTrasladoId =
                                    null,
                                mostrarSelectorMovilidad =
                                    false,
                                encomiendaSeleccionada =
                                    null,
                                trasladosDisponibles =
                                    emptyList(),
                                cargandoMovilidades =
                                    false,
                                error =
                                    mensajeError,
                                mensaje =
                                    null
                            )
                        }

                        cargarEncomiendas()

                    } else {

                        _uiState.update {
                            it.copy(
                                asignandoTrasladoId =
                                    null,
                                error =
                                    mensajeError,
                                mensaje =
                                    null
                            )
                        }
                    }
                }
        }
    }

    fun refrescar() {

        val destino =
            _uiState.value
                .sedeDestinoSeleccionada

        if (
            destino != null
        ) {

            cargarEncomiendas()
        }
    }

    fun limpiarMensaje() {

        _uiState.update {
            it.copy(
                mensaje = null,
                error = null
            )
        }
    }

    private fun obtenerMensajeError(
        exception: Throwable,
        mensajePorDefecto: String
    ): String {

        val detalle =
            exception.message

        if (
            detalle != null
        ) {

            val detalleLimpio =
                detalle.trim()

            if (
                detalleLimpio.isNotBlank()
            ) {

                return detalleLimpio
            }
        }

        return mensajePorDefecto
    }
}