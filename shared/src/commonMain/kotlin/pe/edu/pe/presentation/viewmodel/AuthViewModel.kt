package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.domain.UseCase.LoginUseCase
import pe.edu.pe.domain.model.User

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<AuthState>(AuthState.Idle)

    val state: StateFlow<AuthState> =
        _state.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {

            _state.value = AuthState.Loading

            val result = loginUseCase(
                email,
                password
            )

            result
                .onSuccess { user ->

                    println(
                        "DEBUG_LOGIN: Login exitoso ${user.email}"
                    )

                    _state.value =
                        AuthState.Success(user)
                }
                .onFailure { error ->

                    println(
                        "DEBUG_LOGIN: ${error.message}"
                    )

                    _state.value =
                        AuthState.Error(
                            error.message
                                ?: "Error al iniciar sesión"
                        )
                }
        }
    }

    fun resetState() {
        _state.value = AuthState.Idle
    }
}