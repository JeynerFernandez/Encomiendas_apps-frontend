package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.GetRemoteNotificationsUseCase
import pe.edu.pe.domain.UseCase.GetUnreadNotificationsCountUseCase
import pe.edu.pe.domain.UseCase.MarkAllRemoteNotificationsAsReadUseCase
import pe.edu.pe.domain.UseCase.MarkRemoteNotificationAsReadUseCase

class RemoteNotificationsViewModel(
    private val getRemoteNotificationsUseCase: GetRemoteNotificationsUseCase,
    private val getUnreadNotificationsCountUseCase: GetUnreadNotificationsCountUseCase,
    private val markRemoteNotificationAsReadUseCase: MarkRemoteNotificationAsReadUseCase,
    private val markAllRemoteNotificationsAsReadUseCase: MarkAllRemoteNotificationsAsReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemoteNotificationsUiState())
    val uiState: StateFlow<RemoteNotificationsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var updateJob: Job? = null
    private var sessionGeneration = 0

    fun loadNotifications() {
        if (_uiState.value.isLoading) return

        val generation = sessionGeneration
        loadJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            val notificationsDeferred = async {
                getRemoteNotificationsUseCase()
            }
            val unreadCountDeferred = async {
                getUnreadNotificationsCountUseCase()
            }
            val notificationsResult = notificationsDeferred.await()
            val unreadCountResult = unreadCountDeferred.await()

            val error = notificationsResult.exceptionOrNull()
                ?: unreadCountResult.exceptionOrNull()

            if (generation == sessionGeneration) {
                _uiState.update {
                    it.copy(
                        notifications = notificationsResult.getOrNull() ?: it.notifications,
                        unreadCount = unreadCountResult.getOrNull() ?: it.unreadCount,
                        isLoading = false,
                        error = error?.userMessage()
                    )
                }
            }
        }
    }

    fun reloadNotifications() {
        loadNotifications()
    }

    fun markAsRead(notificationId: Int) {
        if (_uiState.value.isUpdating) return

        val generation = sessionGeneration
        updateJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isUpdating = true, error = null)
            }

            markRemoteNotificationAsReadUseCase(notificationId)
                .fold(
                    onSuccess = { markedNotification ->
                        if (generation == sessionGeneration) {
                            _uiState.update { state ->
                                state.copy(
                                    notifications = state.notifications.map { notification ->
                                        if (notification.id == markedNotification.id) {
                                            notification.copy(
                                                isRead = markedNotification.isRead,
                                                readAt = markedNotification.readAt
                                            )
                                        } else {
                                            notification
                                        }
                                    }
                                )
                            }
                            refreshUnreadCountAfterMarking(generation)
                        }
                    },
                    onFailure = { error ->
                        if (generation == sessionGeneration) {
                            _uiState.update {
                                it.copy(
                                    isUpdating = false,
                                    error = error.userMessage()
                                )
                            }
                        }
                    }
                )
        }
    }

    fun markAllAsRead() {
        if (_uiState.value.isUpdating) return

        val generation = sessionGeneration
        updateJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isUpdating = true, error = null)
            }

            markAllRemoteNotificationsAsReadUseCase()
                .fold(
                    onSuccess = {
                        if (generation == sessionGeneration) {
                            _uiState.update { state ->
                                state.copy(
                                    notifications = state.notifications.map { notification ->
                                        notification.copy(isRead = true)
                                    },
                                    unreadCount = 0,
                                    isUpdating = false
                                )
                            }
                        }
                    },
                    onFailure = { error ->
                        if (generation == sessionGeneration) {
                            _uiState.update {
                                it.copy(
                                    isUpdating = false,
                                    error = error.userMessage()
                                )
                            }
                        }
                    }
                )
        }
    }

    fun clearNotifications() {
        sessionGeneration += 1
        loadJob?.cancel()
        updateJob?.cancel()
        loadJob = null
        updateJob = null
        _uiState.value = RemoteNotificationsUiState()
    }

    private suspend fun refreshUnreadCountAfterMarking(generation: Int) {
        getUnreadNotificationsCountUseCase()
            .fold(
                onSuccess = { unreadCount ->
                    if (generation == sessionGeneration) {
                        _uiState.update {
                            it.copy(
                                unreadCount = unreadCount,
                                isUpdating = false
                            )
                        }
                    }
                },
                onFailure = { error ->
                    if (generation == sessionGeneration) {
                        _uiState.update {
                            it.copy(
                                isUpdating = false,
                                error = error.userMessage()
                            )
                        }
                    }
                }
            )
    }

    private fun Throwable.userMessage(): String =
        message?.takeIf { it.isNotBlank() }
            ?: "No se pudieron cargar las notificaciones"
}
