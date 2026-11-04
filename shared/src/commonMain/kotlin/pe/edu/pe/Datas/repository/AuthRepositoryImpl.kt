package pe.edu.pe.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.AuthResponse
import pe.edu.pe.Datas.remote.dto.LoginRequest
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository


class AuthRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : AuthRepository {

    private val baseUrl =
        ApiClient.getBaseUrl()

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        return try {

            val response =
                client.post(
                    "$baseUrl/auth/login"
                ) {

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        LoginRequest(
                            email = email,
                            password = password
                        )
                    )
                }

            if (
                response.status.isSuccess()
            ) {

                val authResponse =
                    response.body<AuthResponse>()

                val user =
                    authResponse.toDomain()

                // Guarda el JWT recibido del backend
                sessionManager.saveToken(
                    authResponse.accessToken
                )

                // Guarda los datos del usuario autenticado
                sessionManager.saveUser(
                    user
                )

                // Verifica que el token quedó almacenado
                println(
                    "DEBUG_SESSION: Token guardado = ${
                        sessionManager.getToken() != null
                    }"
                )

                Result.success(
                    user
                )

            } else {

                Result.failure(
                    Exception(
                        "Error al iniciar sesión: ${response.status}"
                    )
                )
            }

        } catch (
            e: Exception
        ) {

            Result.failure(
                e
            )
        }
    }
}