package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.User

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<User>
}