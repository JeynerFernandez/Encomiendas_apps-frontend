package pe.edu.pe.Datas.repository
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.CreateRutaRequestDto
import pe.edu.pe.Datas.remote.dto.RutaDto
import pe.edu.pe.Datas.remote.dto.RutaSedeRequestDto
import pe.edu.pe.Datas.remote.dto.UpdateRutaRequestDto
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.Ruta
import pe.edu.pe.domain.repository.RutaRepository

class RutaRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : RutaRepository {

    private val baseUrl =
        ApiClient.getBaseUrl()

    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    override suspend fun getRutas():
            Result<List<Ruta>> {

        return try {

            val token =
                sessionManager.getToken()

            if (
                token == null ||
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

            val response =
                client.get(
                    "$baseUrl/rutas"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }

            val responseBody =
                response.bodyAsText()

            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            List<RutaDto>
                            >(
                        responseBody
                    )

                val rutas =
                    mutableListOf<Ruta>()

                for (
                rutaDto in dto
                ) {

                    rutas.add(
                        rutaDto.toDomain()
                    )
                }

                Result.success(
                    rutas
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

    override suspend fun crearRuta(
        name: String,
        sedeIds: List<Int>
    ): Result<Ruta> {

        return try {

            val token =
                sessionManager.getToken()

            if (
                token == null ||
                token.isBlank()
            ) {

                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }

            if (
                name.isBlank()
            ) {

                return Result.failure(
                    Exception(
                        "El nombre de la ruta es obligatorio"
                    )
                )
            }

            if (
                sedeIds.size < 2
            ) {

                return Result.failure(
                    Exception(
                        "La ruta debe tener al menos dos sedes"
                    )
                )
            }

            val sedesRequest =
                mutableListOf<RutaSedeRequestDto>()

            var orden =
                1

            for (
            sedeId in sedeIds
            ) {

                sedesRequest.add(
                    RutaSedeRequestDto(
                        sedeId = sedeId,
                        orden = orden
                    )
                )

                orden++
            }

            val request =
                CreateRutaRequestDto(
                    name = name.trim(),
                    sedes = sedesRequest
                )

            val cleanToken =
                token
                    .removePrefix(
                        "Bearer "
                    )
                    .trim()

            val response =
                client.post(
                    "$baseUrl/rutas"
                ) {

                    bearerAuth(
                        cleanToken
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

            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<RutaDto>(
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

    override suspend fun actualizarRuta(
        id: Int,
        name: String,
        active: Boolean,
        sedeIds: List<Int>
    ): Result<Ruta> {

        return try {

            val token =
                sessionManager.getToken()

            if (
                token == null ||
                token.isBlank()
            ) {

                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }

            if (
                id <= 0
            ) {

                return Result.failure(
                    Exception(
                        "La ruta seleccionada no es válida"
                    )
                )
            }

            if (
                name.isBlank()
            ) {

                return Result.failure(
                    Exception(
                        "El nombre de la ruta es obligatorio"
                    )
                )
            }

            if (
                sedeIds.size < 2
            ) {

                return Result.failure(
                    Exception(
                        "La ruta debe tener al menos dos sedes"
                    )
                )
            }

            val sedesRequest =
                mutableListOf<RutaSedeRequestDto>()

            var orden =
                1

            for (
            sedeId in sedeIds
            ) {

                sedesRequest.add(
                    RutaSedeRequestDto(
                        sedeId = sedeId,
                        orden = orden
                    )
                )

                orden++
            }

            val request =
                UpdateRutaRequestDto(
                    name = name.trim(),
                    active = active,
                    sedes = sedesRequest
                )

            val cleanToken =
                token
                    .removePrefix(
                        "Bearer "
                    )
                    .trim()

            val response =
                client.put(
                    "$baseUrl/rutas/$id"
                ) {

                    bearerAuth(
                        cleanToken
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

            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<RutaDto>(
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

        try {

            val element =
                json.parseToJsonElement(
                    responseBody
                )

            val objectJson =
                element.jsonObject

            val message =
                objectJson["message"]

            if (
                message is JsonPrimitive
            ) {

                return message.content
            }

            if (
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

                        mensajes.add(
                            item.content
                        )
                    }
                }

                if (
                    mensajes.isNotEmpty()
                ) {

                    return mensajes.joinToString(
                        "\n"
                    )
                }
            }

        } catch (
            exception: Exception
        ) {

            return responseBody
        }

        if (
            responseBody.isNotBlank()
        ) {

            return responseBody
        }

        return "Ocurrió un error al procesar la solicitud"
    }
}