package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.Persona


interface PersonaRepository {


    // GET /personas
    suspend fun getPersonas():
            Result<List<Persona>>


    // GET /personas/dni/:dni
    suspend fun getPersonaByDni(
        dni: String
    ): Result<Persona>


    // POST o PUT dependiendo si existe ID
    suspend fun savePersona(
        id: Int? = null,
        dni: String,
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String
    ): Result<Persona>
}