package pe.edu.pe.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.AuthResponse
import pe.edu.pe.Datas.remote.dto.GoogleChallengeDto
import pe.edu.pe.Datas.remote.dto.GoogleLinkDto
import pe.edu.pe.Datas.remote.dto.GoogleLoginDto
import pe.edu.pe.Datas.remote.dto.LoginRequest
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository
import pe.edu.pe.domain.repository.GoogleLinkRequiredException


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

    override suspend fun googleChallenge(): Result<String> {
        return try {

            val response =
                client.post(
                    "$baseUrl/auth/google/challenge"
                ) {

                    expectSuccess = false
                }

            if (response.status.isSuccess()) {

                val challenge =
                    response.body<GoogleChallengeDto>()

                Result.success(
                    challenge.nonce
                )

            } else {

                Result.failure(
                    obtenerError(response)
                )
            }

        } catch (e: CancellationException) {

            throw e

        } catch (e: Exception) {

            Result.failure(e)
        }

    }

    override suspend fun googleLogin(idToken: String): Result<User> {
        return try {

            val response =
                client.post(
                    "$baseUrl/auth/google"
                ) {

                    expectSuccess = false

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        GoogleLoginDto(
                            idToken = idToken
                        )
                    )
                }

            procesarRespuestaGoogle(
                response
            )

        } catch (e: CancellationException) {

            throw e

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    override suspend fun googleLink(
        idToken: String,
        password: String
    ): Result<User> {
        return try {

            val response =
                client.post(
                    "$baseUrl/auth/google/link"
                ) {

                    expectSuccess = false

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        GoogleLinkDto(
                            idToken = idToken,
                            password = password
                        )
                    )
                }

            procesarRespuestaGoogle(
                response
            )

        } catch (e: CancellationException) {

            throw e

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
    private suspend fun procesarRespuestaGoogle(
        response: HttpResponse
    ): Result<User> {

        if (!response.status.isSuccess()) {

            return Result.failure(
                obtenerError(response)
            )
        }

        val authResponse =
            response.body<AuthResponse>()

        val user =
            authResponse.toDomain()

        if (
            user.role.uppercase() != "CLIENTE"
        ) {

            return Result.failure(
                Exception(
                    "El inicio de sesión con Google solo está disponible para clientes"
                )
            )
        }

        sessionManager.saveToken(
            authResponse.accessToken
        )

        sessionManager.saveUser(
            user
        )

        return Result.success(
            user
        )
    }

    private suspend fun obtenerError(
        response: HttpResponse
    ): Exception {

        val responseText =
            response.bodyAsText()

        val errorJson =
            runCatching {

                Json.parseToJsonElement(
                    responseText
                ) as? JsonObject

            }.getOrNull()

        val message =
            (
                    errorJson?.get("message")
                            as? JsonPrimitive
                    )?.contentOrNull
                ?: "Error de autenticación: ${response.status.value}"

        val errorCode =
            (
                    errorJson?.get("code")
                            as? JsonPrimitive
                    )?.contentOrNull

        if (
            response.status.value == 409 &&
            errorCode == "GOOGLE_LINK_REQUIRED"
        ) {

            return GoogleLinkRequiredException(
                message
            )
        }

        return Exception(
            message
        )
    }
}