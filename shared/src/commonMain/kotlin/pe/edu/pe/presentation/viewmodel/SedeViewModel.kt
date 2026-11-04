package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.GetSedesUseCase
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.usecase.SaveSedeUseCase


sealed class SedeState {

    object Idle : SedeState()

    object Loading : SedeState()

    data class Success(
        val sedes: List<Sede>
    ) : SedeState()

    data class Error(
        val message: String
    ) : SedeState()
}


class SedeViewModel(
    private val getSedesUseCase: GetSedesUseCase,
    private val saveSedeUseCase: SaveSedeUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<SedeState>(
            SedeState.Idle
        )

    val state: StateFlow<SedeState> =
        _state.asStateFlow()


    fun getSedes() {

        viewModelScope.launch {

            _state.value =
                SedeState.Loading

            val result =
                getSedesUseCase()

            result
                .onSuccess { sedes ->

                    _state.value =
                        SedeState.Success(
                            sedes
                        )
                }

                .onFailure { error ->

                    _state.value =
                        SedeState.Error(
                            error.message
                                ?: "Error al obtener sedes"
                        )
                }
        }
    }


    fun saveSede(
        id: Int? = null,
        name: String,
        city: String,
        address: String,
        latitude: Double,
        longitude: Double,
        active: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            val result =
                saveSedeUseCase(
                    id = id,
                    name = name,
                    city = city,
                    address = address,
                    latitude = latitude,
                    longitude = longitude,
                    active = active
                )


            result
                .onSuccess {

                    // Actualiza la tabla después de crear o editar
                    getSedes()

                    onSuccess()
                }

                .onFailure { error ->

                    onError(
                        error.message
                            ?: "Error al guardar la sede"
                    )
                }
        }
    }


    fun resetState() {

        _state.value =
            SedeState.Idle
    }
}