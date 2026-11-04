package pe.edu.pe.Datas.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
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
import pe.edu.pe.Datas.remote.dto.TrackingRequestDto
import pe.edu.pe.Datas.remote.dto.TrackingResponseDto
import pe.edu.pe.Datas.remote.mapper.toDomain

import pe.edu.pe.domain.model.RastreoPedido
import pe.edu.pe.domain.repository.TrackingRepository


class TrackingRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : TrackingRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    private val json =
        Json {

            ignoreUnknownKeys =
                true

            isLenient =
                true
        }


    override suspend fun rastrear(
        codigoRastreo: String,
        clave: String
    ): Result<RastreoPedido> {

        return try {

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


            val body =
                TrackingRequestDto(
                    codigoRastreo =
                        codigoRastreo
                            .trim()
                            .uppercase(),

                    clave =
                        clave.trim()
                )


            val response =
                client.post(
                    "$baseUrl/encomienda/rastrear"
                ) {

                    bearerAuth(
                        cleanToken
                    )

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        body
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "TRACKING STATUS: ${response.status}"
            )

            println(
                "TRACKING BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            TrackingResponseDto
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


            val objeto =
                element.jsonObject


            val message =
                objeto[
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