package pe.edu.pe.Datas.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
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
import pe.edu.pe.Datas.remote.dto.ListaTrasladosMovilidadDto
import pe.edu.pe.Datas.remote.dto.UpdateUbicacionMovilidadRequestDto
import pe.edu.pe.Datas.remote.mapper.toDomain

import pe.edu.pe.domain.model.ListaTrasladosMovilidad
import pe.edu.pe.domain.repository.MovilidadRepository


class MovilidadRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : MovilidadRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    private val json =
        Json {

            ignoreUnknownKeys =
                true

            isLenient =
                true
        }

    override suspend fun getMisTraslados():
            Result<ListaTrasladosMovilidad> {

        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()

                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val response =
                client.get(
                    "$baseUrl/traslados/movilidad/mis-traslados"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD MIS TRASLADOS STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD MIS TRASLADOS BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            ListaTrasladosMovilidadDto
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

            println(
                "ERROR MOVILIDAD MIS TRASLADOS: ${exception.message}"
            )


            Result.failure(
                exception
            )
        }
    }

    override suspend fun iniciarTraslado(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()

                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val response =
                client.put(
                    "$baseUrl/traslados/$trasladoId/iniciar"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD INICIAR STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD INICIAR BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                Result.success(
                    Unit
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

            println(
                "ERROR MOVILIDAD INICIAR: ${exception.message}"
            )


            Result.failure(
                exception
            )
        }
    }
    override suspend fun finalizarTraslado(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()

                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val response =
                client.put(
                    "$baseUrl/traslados/$trasladoId/finalizar"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD FINALIZAR STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD FINALIZAR BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                Result.success(
                    Unit
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

            println(
                "ERROR MOVILIDAD FINALIZAR: ${exception.message}"
            )


            Result.failure(
                exception
            )
        }
    }

    override suspend fun retornarASedeCentral(
        trasladoId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()


                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val response =
                client.put(
                    "$baseUrl/traslados/$trasladoId/retornar-sede-central"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD RETORNO STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD RETORNO BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                Result.success(
                    Unit
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

            println(
                "ERROR MOVILIDAD RETORNO: ${exception.message}"
            )


            Result.failure(
                exception
            )
        }
    }

    override suspend fun descargarPorSede(
        trasladoId: Int,
        sedeId: Int
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        if (
            sedeId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Sede no válida"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()

                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val response =
                client.put(
                    "$baseUrl/traslado-encomienda/traslado/$trasladoId/sede/$sedeId/descargar"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD DESCARGAR STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD DESCARGAR BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                Result.success(
                    Unit
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

            println(
                "ERROR MOVILIDAD DESCARGAR: ${exception.message}"
            )


            Result.failure(
                exception
            )
        }
    }

    override suspend fun actualizarUbicacion(
        trasladoId: Int,
        latitud: Double,
        longitud: Double
    ): Result<Unit> {

        if (
            trasladoId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Traslado no válido"
                )
            )
        }


        if (
            latitud < -90.0 ||
            latitud > 90.0
        ) {

            return Result.failure(
                Exception(
                    "Latitud no válida"
                )
            )
        }


        if (
            longitud < -180.0 ||
            longitud > 180.0
        ) {

            return Result.failure(
                Exception(
                    "Longitud no válida"
                )
            )
        }


        return try {

            val token =
                obtenerToken()


            if (
                token.isFailure
            ) {

                val exception =
                    token.exceptionOrNull()

                if (
                    exception != null
                ) {

                    return Result.failure(
                        exception
                    )
                }


                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val cleanToken =
                token.getOrThrow()


            val requestBody =
                UpdateUbicacionMovilidadRequestDto(

                    latitud =
                        latitud,

                    longitud =
                        longitud
                )


            val response =
                client.put(
                    "$baseUrl/ubicacion-traslado/traslado/$trasladoId"
                ) {

                    bearerAuth(
                        cleanToken
                    )

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        requestBody
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "MOVILIDAD GPS STATUS: ${response.status}"
            )

            println(
                "MOVILIDAD GPS BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                Result.success(
                    Unit
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

            println(
                "ERROR MOVILIDAD GPS: ${exception.message}"
            )


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
            token.isNullOrBlank()
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
                    "El token de sesión no es válido"
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

            val elemento =
                json.parseToJsonElement(
                    responseBody
                )


            val objeto =
                elemento.jsonObject


            val mensaje =
                objeto[
                    "message"
                ]


            if (
                mensaje is JsonPrimitive
            ) {

                val contenido =
                    mensaje.contentOrNull


                if (
                    contenido != null &&
                    contenido.isNotBlank()
                ) {

                    contenido

                } else {

                    responseBody
                }

            } else if (
                mensaje is JsonArray
            ) {

                val mensajes =
                    mutableListOf<String>()


                for (
                item in mensaje
                ) {

                    if (
                        item is JsonPrimitive
                    ) {

                        val contenido =
                            item.contentOrNull


                        if (
                            contenido != null &&
                            contenido.isNotBlank()
                        ) {

                            mensajes.add(
                                contenido
                            )
                        }
                    }
                }


                if (
                    mensajes.isNotEmpty()
                ) {

                    mensajes.joinToString(
                        separator = "\n"
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