package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.GetNotificationSettingsUseCase
import pe.edu.pe.domain.UseCase.SendTestNotificationUseCase
import pe.edu.pe.domain.UseCase.SetNotificationEnableUseCase

data class NotificationSettingsUiState(
    val isEnable: Boolean = false,
    val isSendingTest: Boolean = false,
    val feedbackMessage: String? = null,
    val isFeedBackError: Boolean = false
)

class NotificationSettingsViewModel(
    private val getNotificationSettingsUseCase: GetNotificationSettingsUseCase,
    private val setNotificationEnableUseCase: SetNotificationEnableUseCase,
    private val sendTestNotificationUseCase: SendTestNotificationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        NotificationSettingsUiState(
            isEnable = getNotificationSettingsUseCase().isEnable
        )
    )
    val uiState: StateFlow<NotificationSettingsUiState> = _uiState.asStateFlow()

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            setNotificationEnableUseCase(true)
            _uiState.update {
                it.copy(
                    isEnable = true,
                    feedbackMessage = "Notificación activada",
                    isFeedBackError = false
                )
            }
        } else {
            setNotificationEnableUseCase(false)
            _uiState.update {
                it.copy(
                    isEnable = false,
                    feedbackMessage = "Se necesitan permisos para activar las notificaciones",
                    isFeedBackError = true
                )
            }
        }
    }

    fun setNotificationEnabled(enabled: Boolean) {
        setNotificationEnableUseCase(enabled)
        _uiState.update {
            it.copy(
                isEnable = enabled,
                feedbackMessage = if (enabled) "Notificaciones activadas" else "Notificaciones desactivadas",
                isFeedBackError = false
            )
        }
    }

    fun sendNotification() {
        if (_uiState.value.isSendingTest) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSendingTest = true,
                    feedbackMessage = null
                )
            }

            sendTestNotificationUseCase().fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSendingTest = false,
                            feedbackMessage = "Notificación local enviada",
                            isFeedBackError = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isSendingTest = false,
                            feedbackMessage = error.message ?: "No se pudo enviar la notificación",
                            isFeedBackError = true
                        )
                    }
                }
            )
        }
    }
}