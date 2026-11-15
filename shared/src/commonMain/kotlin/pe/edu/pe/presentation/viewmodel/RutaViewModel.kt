package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.RutaUseCase
import pe.edu.pe.domain.model.Ruta
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.repository.SedeRepository

data class RutaUiState(
    val rutas: List<Ruta> = emptyList(),
    val sedes: List<Sede> = emptyList(),
    val textoBusqueda: String = "",
    val isLoading: Boolean = false,
    val guardando: Boolean = false,
    val error: String? = null,
    val mensaje: String? = null,
    val mostrarFormulario: Boolean = false,
    val rutaEditandoId: Int? = null,
    val origenId: Int? = null,
    val destinoId: Int? = null,
    val paradasIds: List<Int> = emptyList(),
    val rutaActiva: Boolean = true
)

class RutaViewModel(
    private val rutaUseCase: RutaUseCase,
    private val sedeRepository: SedeRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            RutaUiState()
        )

    val uiState: StateFlow<RutaUiState> =
        _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val sedesResult =
                sedeRepository
                    .getSedes()

            if (
                sedesResult.isFailure
            ) {

                val exception =
                    sedesResult.exceptionOrNull()

                var mensaje =
                    "No se pudieron cargar las sedes"

                if (
                    exception != null
                ) {

                    if (
                        exception.message != null
                    ) {

                        if (
                            exception.message!!
                                .isNotBlank()
                        ) {

                            mensaje =
                                exception.message!!
                        }
                    }
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = mensaje
                    )
                }

                return@launch
            }

            val todasLasSedes =
                sedesResult.getOrThrow()

            val sedesActivas =
                mutableListOf<Sede>()

            for (
            sede in todasLasSedes
            ) {

                if (
                    sede.active
                ) {

                    sedesActivas.add(
                        sede
                    )
                }
            }

            rutaUseCase
                .getRutas()
                .onSuccess { rutas ->

                    _uiState.update {
                        it.copy(
                            rutas = rutas,
                            sedes = sedesActivas,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    var mensaje =
                        "No se pudieron cargar las rutas"

                    if (
                        exception.message != null
                    ) {

                        if (
                            exception.message!!
                                .isNotBlank()
                        ) {

                            mensaje =
                                exception.message!!
                        }
                    }

                    _uiState.update {
                        it.copy(
                            sedes = sedesActivas,
                            isLoading = false,
                            error = mensaje
                        )
                    }
                }
        }
    }

    fun cambiarBusqueda(
        texto: String
    ) {

        _uiState.update {
            it.copy(
                textoBusqueda = texto
            )
        }
    }

    fun abrirNuevaRuta() {

        _uiState.update {
            it.copy(
                mostrarFormulario = true,
                rutaEditandoId = null,
                origenId = null,
                destinoId = null,
                paradasIds = emptyList(),
                rutaActiva = true,
                error = null,
                mensaje = null
            )
        }
    }

    fun abrirEditarRuta(
        ruta: Ruta
    ) {

        val sedesOrdenadas =
            ruta.sedes.sortedBy {
                it.orden
            }

        if (
            sedesOrdenadas.size < 2
        ) {

            _uiState.update {
                it.copy(
                    error = "La ruta seleccionada no tiene suficientes sedes"
                )
            }

            return
        }

        val origen =
            sedesOrdenadas.first()

        val destino =
            sedesOrdenadas.last()

        val paradas =
            mutableListOf<Int>()

        if (
            sedesOrdenadas.size > 2
        ) {

            var index =
                1

            while (
                index <
                sedesOrdenadas.size - 1
            ) {

                paradas.add(
                    sedesOrdenadas[index]
                        .sedeId
                )

                index++
            }
        }

        _uiState.update {
            it.copy(
                mostrarFormulario = true,
                rutaEditandoId = ruta.id,
                origenId = origen.sedeId,
                destinoId = destino.sedeId,
                paradasIds = paradas,
                rutaActiva = ruta.active,
                error = null,
                mensaje = null
            )
        }
    }

    fun cerrarFormulario() {

        _uiState.update {
            it.copy(
                mostrarFormulario = false,
                rutaEditandoId = null,
                origenId = null,
                destinoId = null,
                paradasIds = emptyList(),
                rutaActiva = true
            )
        }
    }

    fun seleccionarOrigen(
        sedeId: Int
    ) {

        if (
            sedeId <= 0
        ) {

            return
        }

        _uiState.update {
            it.copy(
                origenId = sedeId,
                error = null
            )
        }
    }

    fun seleccionarDestino(
        sedeId: Int
    ) {

        if (
            sedeId <= 0
        ) {

            return
        }

        _uiState.update {
            it.copy(
                destinoId = sedeId,
                error = null
            )
        }
    }

    fun agregarParada() {

        val nuevasParadas =
            _uiState.value
                .paradasIds
                .toMutableList()

        nuevasParadas.add(
            0
        )

        _uiState.update {
            it.copy(
                paradasIds = nuevasParadas,
                error = null
            )
        }
    }

    fun seleccionarParada(
        index: Int,
        sedeId: Int
    ) {

        if (
            index < 0
        ) {

            return
        }

        if (
            sedeId <= 0
        ) {

            return
        }

        val paradas =
            _uiState.value
                .paradasIds
                .toMutableList()

        if (
            index >= paradas.size
        ) {

            return
        }

        paradas[index] =
            sedeId

        _uiState.update {
            it.copy(
                paradasIds = paradas,
                error = null
            )
        }
    }

    fun eliminarParada(
        index: Int
    ) {

        val paradas =
            _uiState.value
                .paradasIds
                .toMutableList()

        if (
            index < 0 ||
            index >= paradas.size
        ) {

            return
        }

        paradas.removeAt(
            index
        )

        _uiState.update {
            it.copy(
                paradasIds = paradas,
                error = null
            )
        }
    }

    fun cambiarEstadoRuta(
        active: Boolean
    ) {

        _uiState.update {
            it.copy(
                rutaActiva = active
            )
        }
    }

    fun guardarRuta() {

        val estado =
            _uiState.value

        val origenId =
            estado.origenId

        val destinoId =
            estado.destinoId

        if (
            origenId == null
        ) {

            mostrarError(
                "Selecciona la sede de origen"
            )

            return
        }

        if (
            destinoId == null
        ) {

            mostrarError(
                "Selecciona la sede de destino"
            )

            return
        }

        if (
            origenId ==
            destinoId
        ) {

            mostrarError(
                "La sede de origen y destino no pueden ser iguales"
            )

            return
        }

        for (
        paradaId in estado.paradasIds
        ) {

            if (
                paradaId <= 0
            ) {

                mostrarError(
                    "Selecciona todas las paradas intermedias"
                )

                return
            }
        }

        val recorrido =
            mutableListOf<Int>()

        recorrido.add(
            origenId
        )

        for (
        paradaId in estado.paradasIds
        ) {

            recorrido.add(
                paradaId
            )
        }

        recorrido.add(
            destinoId
        )

        if (
            recorrido
                .toSet()
                .size !=
            recorrido.size
        ) {

            mostrarError(
                "Una sede no puede repetirse dentro de la ruta"
            )

            return
        }

        val nombreRuta =
            construirNombreRuta(
                recorrido
            )

        if (
            nombreRuta.isBlank()
        ) {

            mostrarError(
                "No se pudo generar el nombre de la ruta"
            )

            return
        }

        val rutaEditandoId =
            estado.rutaEditandoId

        if (
            rutaEditandoId == null
        ) {

            crearRuta(
                nombreRuta,
                recorrido
            )

        } else {

            actualizarRuta(
                rutaEditandoId,
                nombreRuta,
                estado.rutaActiva,
                recorrido
            )
        }
    }

    private fun crearRuta(
        nombre: String,
        recorrido: List<Int>
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    guardando = true,
                    error = null,
                    mensaje = null
                )
            }

            rutaUseCase
                .crearRuta(
                    name = nombre,
                    sedeIds = recorrido
                )
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            guardando = false,
                            mostrarFormulario = false,
                            rutaEditandoId = null,
                            origenId = null,
                            destinoId = null,
                            paradasIds = emptyList(),
                            rutaActiva = true,
                            mensaje = "Ruta creada correctamente",
                            error = null
                        )
                    }

                    cargarRutas()
                }
                .onFailure { exception ->

                    var mensaje =
                        "No se pudo crear la ruta"

                    if (
                        exception.message != null
                    ) {

                        if (
                            exception.message!!
                                .isNotBlank()
                        ) {

                            mensaje =
                                exception.message!!
                        }
                    }

                    _uiState.update {
                        it.copy(
                            guardando = false,
                            error = mensaje
                        )
                    }
                }
        }
    }

    private fun actualizarRuta(
        id: Int,
        nombre: String,
        active: Boolean,
        recorrido: List<Int>
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    guardando = true,
                    error = null,
                    mensaje = null
                )
            }

            rutaUseCase
                .actualizarRuta(
                    id = id,
                    name = nombre,
                    active = active,
                    sedeIds = recorrido
                )
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            guardando = false,
                            mostrarFormulario = false,
                            rutaEditandoId = null,
                            origenId = null,
                            destinoId = null,
                            paradasIds = emptyList(),
                            rutaActiva = true,
                            mensaje = "Ruta actualizada correctamente",
                            error = null
                        )
                    }

                    cargarRutas()
                }
                .onFailure { exception ->

                    var mensaje =
                        "No se pudo actualizar la ruta"

                    if (
                        exception.message != null
                    ) {

                        if (
                            exception.message!!
                                .isNotBlank()
                        ) {

                            mensaje =
                                exception.message!!
                        }
                    }

                    _uiState.update {
                        it.copy(
                            guardando = false,
                            error = mensaje
                        )
                    }
                }
        }
    }

    private fun cargarRutas() {

        viewModelScope.launch {

            rutaUseCase
                .getRutas()
                .onSuccess { rutas ->

                    _uiState.update {
                        it.copy(
                            rutas = rutas,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    var mensaje =
                        "No se pudieron actualizar las rutas"

                    if (
                        exception.message != null
                    ) {

                        if (
                            exception.message!!
                                .isNotBlank()
                        ) {

                            mensaje =
                                exception.message!!
                        }
                    }

                    _uiState.update {
                        it.copy(
                            error = mensaje
                        )
                    }
                }
        }
    }

    private fun construirNombreRuta(
        recorrido: List<Int>
    ): String {

        val nombres =
            mutableListOf<String>()

        for (
        sedeId in recorrido
        ) {

            var nombreSede =
                ""

            for (
            sede in _uiState.value.sedes
            ) {

                if (
                    sede.id ==
                    sedeId
                ) {

                    nombreSede =
                        sede.name

                    break
                }
            }

            if (
                nombreSede.isBlank()
            ) {

                return ""
            }

            nombres.add(
                nombreSede
            )
        }

        return nombres.joinToString(
            " - "
        )
    }

    fun obtenerNombreSede(
        sedeId: Int
    ): String {

        for (
        sede in _uiState.value.sedes
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

    fun obtenerRutasFiltradas():
            List<Ruta> {

        val texto =
            _uiState.value
                .textoBusqueda
                .trim()
                .lowercase()

        if (
            texto.isBlank()
        ) {

            return _uiState.value.rutas
        }

        val resultado =
            mutableListOf<Ruta>()

        for (
        ruta in _uiState.value.rutas
        ) {

            var encontrada =
                false

            if (
                ruta.name
                    .lowercase()
                    .contains(
                        texto
                    )
            ) {

                encontrada =
                    true
            }

            if (
                !encontrada
            ) {

                for (
                sede in ruta.sedes
                ) {

                    if (
                        sede.sedeNombre
                            .lowercase()
                            .contains(
                                texto
                            )
                    ) {

                        encontrada =
                            true

                        break
                    }
                }
            }

            if (
                encontrada
            ) {

                resultado.add(
                    ruta
                )
            }
        }

        return resultado
    }

    fun limpiarMensaje() {

        _uiState.update {
            it.copy(
                mensaje = null,
                error = null
            )
        }
    }

    private fun mostrarError(
        mensaje: String
    ) {

        _uiState.update {
            it.copy(
                error = mensaje,
                mensaje = null
            )
        }
    }
}