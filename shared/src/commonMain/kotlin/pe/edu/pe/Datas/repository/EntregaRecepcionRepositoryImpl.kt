package pe.edu.pe.Datas.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject

import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.ConfirmarEntregaRequestDto
import pe.edu.pe.Datas.remote.dto.ConfirmarEntregaResponseDto
import pe.edu.pe.Datas.remote.dto.ListaPendientesEntregaDto
import pe.edu.pe.Datas.remote.mapper.toDomain

import pe.edu.pe.domain.model.ListaPendientesEntrega
import pe.edu.pe.domain.repository.EntregaRecepcionRepository


class EntregaRecepcionRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : EntregaRecepcionRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    private val json =
        Json {

            ignoreUnknownKeys =
                true

            isLenient =
                true
        }


    override suspend fun getPendientes():
            Result<ListaPendientesEntrega> {

        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val error =
                    token.exceptionOrNull()


                if (
                    error != null
                ) {

                    return Result.failure(
                        error
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val response =
                client.get(
                    "$baseUrl/entregas/pendientes"
                ) {

                    bearerAuth(
                        token.getOrThrow()
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "ENTREGAS PENDIENTES STATUS: ${response.status}"
            )

            println(
                "ENTREGAS PENDIENTES BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            ListaPendientesEntregaDto
                            >(
                        responseBody
                    )


                Result.success(
                    dto.toDomain()
                )

            } else {

                Result.failure(
                    Exception(
                        obtenerMensajeError(
                            responseBody
                        )
                    )
                )
            }

        } catch (
            exception: Exception
        ) {

            Result.failure(
                exception
            )
        }
    }


    override suspend fun entregar(
        encomiendaId: Int,
        dni: String,
        clave: String
    ): Result<String> {

        if (
            encomiendaId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Encomienda no válida"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val error =
                    token.exceptionOrNull()


                if (
                    error != null
                ) {

                    return Result.failure(
                        error
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val request =
                ConfirmarEntregaRequestDto(

                    dni =
                        dni,

                    clave =
                        clave
                )


            val response =
                client.post(
                    "$baseUrl/entregas/encomienda/$encomiendaId"
                ) {

                    bearerAuth(
                        token.getOrThrow()
                    )

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        request
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "CONFIRMAR ENTREGA STATUS: ${response.status}"
            )

            println(
                "CONFIRMAR ENTREGA BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val resultado =
                    json.decodeFromString<
                            ConfirmarEntregaResponseDto
                            >(
                        responseBody
                    )


                Result.success(
                    resultado.message
                )

            } else {

                Result.failure(
                    Exception(
                        obtenerMensajeError(
                            responseBody
                        )
                    )
                )
            }

        } catch (
            exception: Exception
        ) {

            Result.failure(
                exception
            )
        }
    }


    private fun obtenerToken():
            Result<String> {

        val token =
            sessionManager.getToken()


        if (
            token == null
        ) {

            return Result.failure(
                Exception(
                    "No existe una sesión activa"
                )
            )
        }


        if (
            token.isBlank()
        ) {

            return Result.failure(
                Exception(
                    "No existe una sesión activa"
                )
            )
        }


        val cleanToken =
            token
                .removePrefix(
                    "Bearer "
                )
                .trim()


        if (
            cleanToken.isBlank()
        ) {

            return Result.failure(
                Exception(
                    "Token no válido"
                )
            )
        }


        return Result.success(
            cleanToken
        )
    }


    private fun obtenerMensajeError(
        responseBody: String
    ): String {

        if (
            responseBody.isBlank()
        ) {

            return "Error al comunicarse con el servidor"
        }


        return try {

            val element =
                json.parseToJsonElement(
                    responseBody
                )


            val obj =
                element.jsonObject


            val message =
                obj[
                    "message"
                ]


            if (
                message is JsonPrimitive
            ) {

                val content =
                    message.contentOrNull


                if (
                    content != null &&
                    content.isNotBlank()
                ) {

                    content

                } else {

                    responseBody
                }

            } else if (
                message is JsonArray
            ) {

                val mensajes =
                    mutableListOf<String>()


                for (
                item in message
                ) {

                    if (
                        item is JsonPrimitive
                    ) {

                        val content =
                            item.contentOrNull


                        if (
                            content != null &&
                            content.isNotBlank()
                        ) {

                            mensajes.add(
                                content
                            )
                        }
                    }
                }


                if (
                    mensajes.isNotEmpty()
                ) {

                    mensajes.joinToString(
                        separator =
                            "\n"
                    )

                } else {

                    responseBody
                }

            } else {

                responseBody
            }

        } catch (
            exception: Exception
        ) {

            responseBody
        }
    }
}