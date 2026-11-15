package pe.edu.pe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.pe.domain.model.Persona
import pe.edu.pe.domain.UseCase.GetPersonaByDniUseCase
import pe.edu.pe.domain.UseCase.GetPersonasUseCase
import pe.edu.pe.domain.UseCase.SavePersonaUseCase


sealed class PersonaState {

    object Idle :
        PersonaState()

    object Loading :
        PersonaState()

    data class Success(
        val personas: List<Persona>
    ) : PersonaState()

    data class Error(
        val message: String
    ) : PersonaState()
}


sealed class PersonaSearchState {

    object Idle :
        PersonaSearchState()

    object Loading :
        PersonaSearchState()

    data class Success(
        val persona: Persona
    ) : PersonaSearchState()

    data class Error(
        val message: String
    ) : PersonaSearchState()
}


class PersonaViewModel(

    private val getPersonasUseCase:
    GetPersonasUseCase,

    private val getPersonaByDniUseCase:
    GetPersonaByDniUseCase,

    private val savePersonaUseCase:
    SavePersonaUseCase

) : ViewModel() {


    private val _state =
        MutableStateFlow<PersonaState>(
            PersonaState.Idle
        )

    val state:
            StateFlow<PersonaState> =
        _state.asStateFlow()


    private val _searchState =
        MutableStateFlow<PersonaSearchState>(
            PersonaSearchState.Idle
        )

    val searchState:
            StateFlow<PersonaSearchState> =
        _searchState.asStateFlow()


    // GET de todas las personas
    fun getPersonas() {

        viewModelScope.launch {

            _state.value =
                PersonaState.Loading


            getPersonasUseCase()
                .onSuccess {

                    _state.value =
                        PersonaState.Success(
                            it
                        )
                }

                .onFailure {

                    _state.value =
                        PersonaState.Error(
                            it.message
                                ?: "Error al obtener personas"
                        )
                }
        }
    }


    // Busca una persona y devuelve el resultado a la pantalla
    fun buscarPorDni(

        dni: String,

        onSuccess: (pe.edu.pe.domain.model.Persona) -> Unit,

        onError: (String) -> Unit

    ) {

        viewModelScope.launch {

            getPersonaByDniUseCase(
                dni
            )
                .onSuccess {

                    onSuccess(
                        it
                    )
                }

                .onFailure {

                    onError(
                        it.message
                            ?: "Persona no encontrada"
                    )
                }
        }
    }

    // POST o PUT según el ID
    fun savePersona(
        id: Int? = null,
        dni: String,
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        onSuccess: (Persona) -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {

            savePersonaUseCase(

                id =
                    id,

                dni =
                    dni,

                nombres =
                    nombres,

                apellidoPaterno =
                    apellidoPaterno,

                apellidoMaterno =
                    apellidoMaterno
            )
                .onSuccess {

                    // Actualiza la lista después de guardar
                    getPersonas()

                    onSuccess(
                        it
                    )
                }

                .onFailure {

                    onError(
                        it.message
                            ?: "Error al guardar persona"
                    )
                }
        }
    }


    // Limpia una búsqueda anterior
    fun limpiarBusqueda() {

        _searchState.value =
            PersonaSearchState.Idle
    }
}