package pe.edu.pe.Datas.repository

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
import pe.edu.pe.Datas.remote.dto.CreateTarifaRequest
import pe.edu.pe.Datas.remote.dto.TarifaDto
import pe.edu.pe.Datas.remote.dto.UpdateTarifaRequest
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.Tarifa
import pe.edu.pe.domain.repository.TarifaRepository


class TarifaRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : TarifaRepository {

    private val baseUrl =
        ApiClient.getBaseUrl()


    override suspend fun getTarifas():
            Result<List<Tarifa>> {

        return try {

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


            // Obtiene todas las tarifas del backend
            val response =
                client.get(
                    "$baseUrl/tarifas"
                ) {

                    bearerAuth(
                        token
                    )
                }


            if (
                response.status.isSuccess()
            ) {

                val tarifasDto =
                    response.body<
                            List<TarifaDto>
                            >()


                val tarifas =
                    tarifasDto.map {
                        it.toDomain()
                    }


                Result.success(
                    tarifas
                )

            } else {

                Result.failure(
                    Exception(
                        "Error al obtener tarifas: ${response.status}"
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


    override suspend fun saveTarifa(
        id: Int?,
        sedeOrigenId: Int,
        sedeDestinoId: Int,
        precioBase: Double,
        precioKgExcedente: Double,
        active: Boolean
    ): Result<Tarifa> {

        return try {

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


            // Sin ID crea una tarifa nueva
            if (
                id == null
            ) {

                val response =
                    client.post(
                        "$baseUrl/tarifas"
                    ) {

                        bearerAuth(
                            token
                        )


                        contentType(
                            ContentType
                                .Application
                                .Json
                        )


                        setBody(
                            CreateTarifaRequest(

                                sedeOrigenId =
                                    sedeOrigenId,

                                sedeDestinoId =
                                    sedeDestinoId,

                                precioBase =
                                    precioBase,

                                precioKgExcedente =
                                    precioKgExcedente
                            )
                        )
                    }


                if (
                    response.status.isSuccess()
                ) {

                    val tarifaDto =
                        response.body<
                                TarifaDto
                                >()


                    Result.success(
                        tarifaDto.toDomain()
                    )

                } else {

                    Result.failure(
                        Exception(
                            "Error al registrar tarifa: ${response.status}"
                        )
                    )
                }

            } else {

                // Con ID actualiza la tarifa existente
                val response =
                    client.put(
                        "$baseUrl/tarifas/$id"
                    ) {

                        bearerAuth(
                            token
                        )


                        contentType(
                            ContentType
                                .Application
                                .Json
                        )


                        setBody(
                            UpdateTarifaRequest(

                                sedeOrigenId =
                                    sedeOrigenId,

                                sedeDestinoId =
                                    sedeDestinoId,

                                precioBase =
                                    precioBase,

                                precioKgExcedente =
                                    precioKgExcedente,

                                active =
                                    active
                            )
                        )
                    }


                if (
                    response.status.isSuccess()
                ) {

                    val tarifaDto =
                        response.body<
                                TarifaDto
                                >()


                    Result.success(
                        tarifaDto.toDomain()
                    )

                } else {

                    Result.failure(
                        Exception(
                            "Error al actualizar tarifa: ${response.status}"
                        )
                    )
                }
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