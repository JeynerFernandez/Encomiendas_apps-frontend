
package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.domain.UseCase.GetCurrentUserUseCase
import pe.edu.pe.domain.UseCase.LoginUseCase
import pe.edu.pe.domain.UseCase.GoogleChallengeUseCase
import pe.edu.pe.domain.UseCase.GoogleLoginUseCase
import pe.edu.pe.domain.UseCase.GoogleLinkUseCase
import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthHttpException
import pe.edu.pe.domain.repository.GoogleLinkRequiredException

sealed class AuthState {

    object Idle : AuthState()

    object Loading : AuthState()

    data class Success(
        val user: User
    ) : AuthState()

    data class Error(
        val message: String
    ) : AuthState()

    object RestoringSession : AuthState()

    object NoSession : AuthState()

    data class SessionExpired(
        val message: String
    ) : AuthState()

    data class SessionRestoreError(
        val message: String
    ) : AuthState()

    data class GoogleChallengeReady(
        val nonce: String
    ) : AuthState()

    object GoogleAwaitingCredential : AuthState()

    data class GoogleLinkRequired(
        val message: String
    ) : AuthState()

    val isBusy: Boolean
        get() =
            this is Loading ||
                    this is RestoringSession ||
                    this is GoogleChallengeReady ||
                    this is GoogleAwaitingCredential
}

class AuthViewModel(

    private val loginUseCase:
    LoginUseCase,

    private val googleChallengeUseCase:
    GoogleChallengeUseCase,

    private val googleLoginUseCase:
    GoogleLoginUseCase,

    private val googleLinkUseCase:
    GoogleLinkUseCase,

    private val getCurrentUserUseCase:
    GetCurrentUserUseCase,

    private val sessionManager:
    SessionManager

) : ViewModel() {

    private val _state =
        MutableStateFlow<AuthState>(
            AuthState.Idle
        )

    val state: StateFlow<AuthState> =
        _state.asStateFlow()

    private var pendingLinkPassword:
            String? = null

    fun login(
        email: String,
        password: String
    ) {

        if (_state.value.isBusy) {
            return
        }

        pendingLinkPassword = null

        _state.value =
            AuthState.Loading

        viewModelScope.launch {

            val result =
                loginUseCase(
                    email,
                    password
                )

            result

                .onSuccess { user ->

                    _state.value =
                        AuthState.Success(
                            user
                        )
                }

                .onFailure { error ->

                    _state.value =
                        AuthState.Error(
                            error.message
                                ?: "Error al iniciar sesión"
                        )
                }
        }
    }

    fun restoreSession() {
        if (_state.value.isBusy) {
            return
        }

        _state.value = AuthState.RestoringSession

        viewModelScope.launch {
            val token = sessionManager.getToken()

            if (token.isNullOrBlank()) {
                _state.value = AuthState.NoSession
                return@launch
            }

            try {
                getCurrentUserUseCase()
                    .onSuccess { user ->
                        _state.value = AuthState.Success(user)
                    }
                    .onFailure { error ->
                        if (error is AuthHttpException && error.statusCode == 401) {
                            sessionManager.clear()
                            _state.value = AuthState.SessionExpired(
                                error.message ?: "La sesión ha expirado"
                            )
                        } else {
                            _state.value = AuthState.SessionRestoreError(
                                error.message ?: "No se pudo restaurar la sesión"
                            )
                        }
                    }
            } catch (e: CancellationException) {
                throw e
            }
        }
    }

    fun requestGoogleChallenge() {

        if (_state.value.isBusy) {
            return
        }

        pendingLinkPassword = null

        solicitarDesafioGoogle()
    }

    fun requestGoogleLinkChallenge(
        password: String
    ) {

        if (
            _state.value !is
                    AuthState.GoogleLinkRequired
        ) {
            return
        }

        if (password.isBlank()) {

            _state.value =
                AuthState.GoogleLinkRequired(
                    "Ingresa tu contraseña de MANDA YA"
                )

            return
        }

        pendingLinkPassword =
            password

        solicitarDesafioGoogle()
    }

    private fun solicitarDesafioGoogle() {

        _state.value =
            AuthState.Loading

        viewModelScope.launch {

            val result =
                googleChallengeUseCase()

            result

                .onSuccess { nonce ->

                    if (nonce.isBlank()) {

                        manejarErrorGoogle(
                            "No se pudo obtener el desafío de seguridad"
                        )

                    } else {

                        _state.value =
                            AuthState.GoogleChallengeReady(
                                nonce
                            )
                    }
                }

                .onFailure { error ->

                    manejarErrorGoogle(
                        error.message
                            ?: "No se pudo conectar con el servidor"
                    )
                }
        }
    }

    fun consumeGoogleChallenge(): String? {

        val currentState =
            _state.value

        if (
            currentState !is
                    AuthState.GoogleChallengeReady
        ) {
            return null
        }

        _state.value =
            AuthState.GoogleAwaitingCredential

        return currentState.nonce
    }

    fun onGoogleToken(
        idToken: String
    ) {

        if (
            _state.value !is
                    AuthState.GoogleAwaitingCredential
        ) {
            return
        }

        if (idToken.isBlank()) {

            manejarErrorGoogle(
                "No se pudo obtener el token de Google"
            )

            return
        }

        val password =
            pendingLinkPassword

        pendingLinkPassword = null

        _state.value =
            AuthState.Loading

        viewModelScope.launch {

            val result =
                if (password != null) {

                    googleLinkUseCase(
                        idToken = idToken,
                        password = password
                    )

                } else {

                    googleLoginUseCase(
                        idToken = idToken
                    )
                }

            result

                .onSuccess { user ->

                    _state.value =
                        AuthState.Success(
                            user
                        )
                }

                .onFailure { error ->

                    if (
                        error is GoogleLinkRequiredException ||
                        password != null
                    ) {

                        _state.value =
                            AuthState.GoogleLinkRequired(
                                error.message
                                    ?: "Debes vincular tu cuenta de Google"
                            )

                    } else {

                        _state.value =
                            AuthState.Error(
                                error.message
                                    ?: "Error al iniciar sesión con Google"
                            )
                    }
                }
        }
    }

    fun onGoogleError(
        message: String
    ) {

        if (
            _state.value !is
                    AuthState.GoogleAwaitingCredential &&
            _state.value !is
                    AuthState.GoogleChallengeReady
        ) {
            return
        }

        manejarErrorGoogle(
            message
        )
    }

    private fun manejarErrorGoogle(
        message: String
    ) {

        val isLinking =
            pendingLinkPassword != null

        pendingLinkPassword = null

        _state.value =
            if (isLinking) {

                AuthState.GoogleLinkRequired(
                    message
                )

            } else {

                AuthState.Error(
                    message
                )
            }
    }

    fun resetState() {

        if (_state.value.isBusy) {
            return
        }

        pendingLinkPassword = null

        _state.value =
            AuthState.Idle
    }
}
