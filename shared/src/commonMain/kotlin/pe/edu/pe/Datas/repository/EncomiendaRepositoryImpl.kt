package pe.edu.pe.Datas.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.CreateEncomiendaRequest
import pe.edu.pe.Datas.remote.dto.CreateEncomiendaResponseDto
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.RegistroEncomienda
import pe.edu.pe.domain.repository.EncomiendaRepository


class EncomiendaRepositoryImpl(

    private val client: HttpClient,

    private val sessionManager: SessionManager

) : EncomiendaRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    override suspend fun registrarEncomienda(

        remitenteId: Int,

        destinatarioId: Int,

        sedeOrigenId: Int,

        sedeDestinoId: Int,

        altoCm: Double,

        anchoCm: Double,

        largoCm: Double,

        pesoKg: Double,

        modalidadPago: String,

        observacion: String?

    ): Result<RegistroEncomienda> {

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


            // Registra la encomienda real
            val response =
                client.post(
                    "$baseUrl/encomienda"
                ) {

                    bearerAuth(
                        token
                    )

                    contentType(
                        ContentType.Application.Json
                    )

                    setBody(
                        CreateEncomiendaRequest(

                            remitenteId =
                                remitenteId,

                            destinatarioId =
                                destinatarioId,

                            sedeOrigenId =
                                sedeOrigenId,

                            sedeDestinoId =
                                sedeDestinoId,

                            altoCm =
                                altoCm,

                            anchoCm =
                                anchoCm,

                            largoCm =
                                largoCm,

                            pesoKg =
                                pesoKg,

                            modalidadPago =
                                modalidadPago,

                            observacion =
                                observacion
                                    ?.trim()
                                    ?.takeIf {
                                        it.isNotBlank()
                                    }
                        )
                    )
                }


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    response.body<
                            CreateEncomiendaResponseDto
                            >()


                Result.success(
                    dto.toDomain()
                )

            } else {

                val error =
                    response.bodyAsText()


                Result.failure(
                    Exception(
                        error
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