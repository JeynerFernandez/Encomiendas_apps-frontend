package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.User



class GoogleLinkRequiredException(
    message: String
) : Exception(message)

class AuthHttpException(
    val statusCode: Int,
    message: String
) : Exception(message)

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<User>
    suspend fun googleChallenge(): Result<String>
    suspend fun googleLogin(
        idToken: String
    ): Result<User>

    suspend fun googleLink(
        idToken: String,
        password: String
    ): Result<User>

    suspend fun getCurrentUser(): Result<User>
}
