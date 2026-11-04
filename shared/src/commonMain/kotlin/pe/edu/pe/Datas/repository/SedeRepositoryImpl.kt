package pe.edu.pe.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.CreateSedeRequest
import pe.edu.pe.Datas.remote.dto.SedeDto
import pe.edu.pe.Datas.remote.dto.UpdateSedeRequest
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.repository.SedeRepository


class SedeRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : SedeRepository {

    private val baseUrl =
        ApiClient.getBaseUrl()


    override suspend fun getSedes(): Result<List<Sede>> {

        return try {

            val token =
                sessionManager.getToken()

            if (token.isNullOrBlank()) {

                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            val response =
                client.get(
                    "$baseUrl/sedes"
                ) {

                    // Envía el JWT al backend
                    bearerAuth(token)
                }


            if (response.status.isSuccess()) {

                val sedesDto =
                    response.body<List<SedeDto>>()

                val sedes =
                    sedesDto.map {
                        it.toDomain()
                    }

                Result.success(
                    sedes
                )

            } else {

                Result.failure(
                    Exception(
                        "Error al obtener sedes: ${response.status}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    override suspend fun saveSede(
        id: Int?,
        name: String,
        city: String,
        address: String,
        latitude: Double,
        longitude: Double,
        active: Boolean
    ): Result<Sede> {

        return try {

            val token =
                sessionManager.getToken()

            if (token.isNullOrBlank()) {

                return Result.failure(
                    Exception(
                        "No existe una sesión activa"
                    )
                )
            }


            // Si no existe ID, registra una nueva sede
            if (id == null) {

                val response =
                    client.post(
                        "$baseUrl/sedes"
                    ) {

                        bearerAuth(token)

                        contentType(
                            ContentType.Application.Json
                        )

                        setBody(
                            CreateSedeRequest(
                                name = name,
                                city = city,
                                address = address,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }


                if (response.status.isSuccess()) {

                    val sedeDto =
                        response.body<SedeDto>()

                    Result.success(
                        sedeDto.toDomain()
                    )

                } else {

                    Result.failure(
                        Exception(
                            "Error al registrar sede: ${response.status}"
                        )
                    )
                }

            } else {

                // Si existe ID, actualiza la sede
                val response =
                    client.put(
                        "$baseUrl/sedes/$id"
                    ) {

                        bearerAuth(token)

                        contentType(
                            ContentType.Application.Json
                        )

                        setBody(
                            UpdateSedeRequest(
                                name = name,
                                city = city,
                                address = address,
                                latitude = latitude,
                                longitude = longitude,
                                active = active
                            )
                        )
                    }


                if (response.status.isSuccess()) {

                    val sedeDto =
                        response.body<SedeDto>()

                    Result.success(
                        sedeDto.toDomain()
                    )

                } else {

                    Result.failure(
                        Exception(
                            "Error al actualizar sede: ${response.status}"
                        )
                    )
                }
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}