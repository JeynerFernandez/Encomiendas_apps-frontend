package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.Persona
import pe.edu.pe.domain.repository.PersonaRepository


class GetPersonasUseCase(
    private val repository:
    PersonaRepository
) {

    suspend operator fun invoke():
            Result<List<Persona>> {

        return repository
            .getPersonas()
    }
}


class GetPersonaByDniUseCase(
    private val repository:
    PersonaRepository
) {

    suspend operator fun invoke(
        dni: String
    ): Result<Persona> {

        return repository
            .getPersonaByDni(
                dni
            )
    }
}


class SavePersonaUseCase(
    private val repository:
    PersonaRepository
) {

    suspend operator fun invoke(
        id: Int? = null,
        dni: String,
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String
    ): Result<Persona> {

        return repository
            .savePersona(

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
    }
}