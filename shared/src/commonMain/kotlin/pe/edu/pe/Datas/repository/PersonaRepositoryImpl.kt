package pe.edu.pe.Datas.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.CreatePersonaRequest
import pe.edu.pe.Datas.remote.dto.PersonaDto
import pe.edu.pe.Datas.remote.dto.UpdatePersonaRequest
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.Persona
import pe.edu.pe.domain.repository.PersonaRepository


class PersonaRepositoryImpl(

    private val client: HttpClient,

    private val sessionManager: SessionManager

) : PersonaRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    // GET /personas
    override suspend fun getPersonas():
            Result<List<Persona>> {

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


            val response =
                client.get(
                    "$baseUrl/personas"
                ) {

                    bearerAuth(
                        token
                    )
                }


            if (
                response.status.isSuccess()
            ) {

                val personasDto =
                    response.body<
                            List<PersonaDto>
                            >()


                val personas =
                    personasDto.map {
                        it.toDomain()
                    }


                Result.success(
                    personas
                )

            } else {

                Result.failure(
                    Exception(
                        "Error al obtener personas: ${response.status}"
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


    // GET /personas/dni/:dni
    override suspend fun getPersonaByDni(
        dni: String
    ): Result<Persona> {

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


            val response =
                client.get(
                    "$baseUrl/personas/dni/$dni"
                ) {

                    bearerAuth(
                        token
                    )
                }


            when {

                response.status.isSuccess() -> {

                    val personaDto =
                        response.body<
                                PersonaDto
                                >()


                    Result.success(
                        personaDto.toDomain()
                    )
                }


                response.status ==
                        HttpStatusCode.NotFound -> {

                    Result.failure(
                        Exception(
                            "No existe una persona con el DNI $dni"
                        )
                    )
                }


                else -> {

                    val error =
                        response.bodyAsText()


                    Result.failure(
                        Exception(
                            "Error al buscar persona: $error"
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


    // POST /personas
    // PUT /personas/:id
    override suspend fun savePersona(
        id: Int?,
        dni: String,
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String
    ): Result<Persona> {

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


            // Si no existe ID crea la persona
            if (
                id == null
            ) {

                val response =
                    client.post(
                        "$baseUrl/personas"
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
                            CreatePersonaRequest(

                                dni =
                                    dni.trim(),

                                nombres =
                                    nombres.trim(),

                                apellidoPaterno =
                                    apellidoPaterno.trim(),

                                apellidoMaterno =
                                    apellidoMaterno.trim()
                            )
                        )
                    }


                if (
                    response.status.isSuccess()
                ) {

                    val personaDto =
                        response.body<
                                PersonaDto
                                >()


                    Result.success(
                        personaDto.toDomain()
                    )

                } else {

                    val error =
                        response.bodyAsText()


                    Result.failure(
                        Exception(
                            "Error al registrar persona: $error"
                        )
                    )
                }

            } else {

                // Si existe ID actualiza la persona
                val response =
                    client.put(
                        "$baseUrl/personas/$id"
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
                            UpdatePersonaRequest(

                                dni =
                                    dni.trim(),

                                nombres =
                                    nombres.trim(),

                                apellidoPaterno =
                                    apellidoPaterno.trim(),

                                apellidoMaterno =
                                    apellidoMaterno.trim()
                            )
                        )
                    }


                if (
                    response.status.isSuccess()
                ) {

                    val personaDto =
                        response.body<
                                PersonaDto
                                >()


                    Result.success(
                        personaDto.toDomain()
                    )

                } else {

                    val error =
                        response.bodyAsText()


                    Result.failure(
                        Exception(
                            "Error al actualizar persona: $error"
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