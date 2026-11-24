package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.GetTotalEncomiendasUseCase
import pe.edu.pe.presentation.reception.ReceptionState

class ReceptionViewModel(
    private val getTotalEncomiendasUseCase: GetTotalEncomiendasUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ReceptionState())
    val state: StateFlow<ReceptionState> = _state.asStateFlow()

    private var hasLoaded = false

    fun loadCount(force: Boolean = false) {
        if ((!force && hasLoaded) || (!force && _state.value.isLoading)) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, message = null)
            getTotalEncomiendasUseCase()
                .onSuccess { total ->
                    hasLoaded = true
                    _state.value = ReceptionState(totalEncomiendas = total)
                }
                .onFailure { error ->
                    _state.value = ReceptionState(message = error.message ?: "No se pudo cargar el contador")
                }
        }
    }
}
