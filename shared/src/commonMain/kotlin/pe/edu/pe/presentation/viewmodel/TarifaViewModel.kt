package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.GetTarifasUseCase
import pe.edu.pe.domain.UseCase.SaveTarifaUseCase
import pe.edu.pe.domain.model.Tarifa


sealed class TarifaState {

    object Idle : TarifaState()

    object Loading : TarifaState()

    data class Success(
        val tarifas: List<Tarifa>
    ) : TarifaState()

    data class Error(
        val message: String
    ) : TarifaState()
}


class TarifaViewModel(
    private val getTarifasUseCase: GetTarifasUseCase,
    private val saveTarifaUseCase: SaveTarifaUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<TarifaState>(
            TarifaState.Idle
        )

    val state: StateFlow<TarifaState> =
        _state.asStateFlow()


    // Obtiene las tarifas desde MySQL
    fun getTarifas() {

        viewModelScope.launch {

            _state.value =
                TarifaState.Loading

            val result =
                getTarifasUseCase()

            result
                .onSuccess { tarifas ->

                    _state.value =
                        TarifaState.Success(
                            tarifas
                        )
                }

                .onFailure { error ->

                    _state.value =
                        TarifaState.Error(
                            error.message
                                ?: "Error al obtener las tarifas"
                        )
                }
        }
    }


    // Crea o actualiza dependiendo del ID
    fun saveTarifa(
        id: Int? = null,
        sedeOrigenId: Int,
        sedeDestinoId: Int,
        precioBase: Double,
        precioKgExcedente: Double,
        active: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            val result =
                saveTarifaUseCase(
                    id = id,
                    sedeOrigenId = sedeOrigenId,
                    sedeDestinoId = sedeDestinoId,
                    precioBase = precioBase,
                    precioKgExcedente = precioKgExcedente,
                    active = active
                )


            result
                .onSuccess {

                    // Recarga la tabla después de guardar
                    getTarifas()

                    onSuccess()
                }

                .onFailure { error ->

                    onError(
                        error.message
                            ?: "Error al guardar la tarifa"
                    )
                }
        }
    }


    fun resetState() {

        _state.value =
            TarifaState.Idle
    }
}