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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.api.ApiClient
import pe.edu.pe.Datas.remote.dto.IngresoAlmacenResponseDto
import pe.edu.pe.Datas.remote.dto.ListaEncomiendasAlmacenDto
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.ConductorTrasladoAlmacen
import pe.edu.pe.domain.model.IngresoAlmacen
import pe.edu.pe.domain.model.ListaEncomiendasAlmacen
import pe.edu.pe.domain.model.ListaTrasladosDisponiblesAlmacen
import pe.edu.pe.domain.model.TrasladoDisponibleAlmacen
import pe.edu.pe.domain.model.VehiculoTrasladoAlmacen
import pe.edu.pe.domain.repository.AlmacenRepository


class AlmacenRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : AlmacenRepository {


    private val baseUrl =
        ApiClient.getBaseUrl()


    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    override suspend fun getEncomiendasPorDestino(
        destinoId: Int,
        filtro: String
    ): Result<ListaEncomiendasAlmacen> {

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


            val cleanToken =
                token
                    .removePrefix("Bearer ")
                    .trim()


            val filtroNormalizado =
                filtro
                    .trim()
                    .lowercase()


            if (
                filtroNormalizado != "pendientes" &&
                filtroNormalizado != "asignadas" &&
                filtroNormalizado != "todas"
            ) {

                return Result.failure(
                    Exception(
                        "Filtro no válido"
                    )
                )
            }


            if (destinoId <= 0) {

                return Result.failure(
                    Exception(
                        "Sede destino no válida"
                    )
                )
            }


            val response =
                client.get(
                    "$baseUrl/encomienda/almacen/destino/$destinoId?filtro=$filtroNormalizado"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "ALMACEN LIST STATUS: ${response.status}"
            )

            println(
                "ALMACEN LIST BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            ListaEncomiendasAlmacenDto
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

        } catch (e: Exception) {

            println(
                "ERROR ALMACEN LIST: ${e.message}"
            )


            Result.failure(
                e
            )
        }
    }


    override suspend fun ingresarAlmacen(
        codigoRastreo: String
    ): Result<IngresoAlmacen> {

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


            val cleanToken =
                token
                    .removePrefix("Bearer ")
                    .trim()


            val codigo =
                codigoRastreo
                    .trim()
                    .uppercase()


            if (codigo.isBlank()) {

                return Result.failure(
                    Exception(
                        "Ingrese un código de rastreo"
                    )
                )
            }


            val response =
                client.put(
                    "$baseUrl/encomienda/codigo/$codigo/ingresar-almacen"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "INGRESAR ALMACEN STATUS: ${response.status}"
            )

            println(
                "INGRESAR ALMACEN BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            IngresoAlmacenResponseDto
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

        } catch (e: Exception) {

            println(
                "ERROR INGRESAR ALMACEN: ${e.message}"
            )


            Result.failure(
                e
            )
        }
    }


    override suspend fun getTrasladosDisponibles(
        destinoId: Int
    ): Result<ListaTrasladosDisponiblesAlmacen> {

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


            if (destinoId <= 0) {

                return Result.failure(
                    Exception(
                        "Sede destino no válida"
                    )
                )
            }


            val cleanToken =
                token
                    .removePrefix("Bearer ")
                    .trim()


            val response =
                client.get(
                    "$baseUrl/traslados/disponibles/destino/$destinoId"
                ) {

                    bearerAuth(
                        cleanToken
                    )
                }


            val responseBody =
                response.bodyAsText()


            println(
                "TRASLADOS DISPONIBLES STATUS: ${response.status}"
            )

            println(
                "TRASLADOS DISPONIBLES BODY: $responseBody"
            )


            if (
                response.status.isSuccess()
            ) {

                val dto =
                    json.decodeFromString<
                            ListaTrasladosDisponiblesDto
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

        } catch (e: Exception) {

            println(
                "ERROR TRASLADOS DISPONIBLES: ${e.message}"
            )


            Result.failure(
                e
            )
        }
    }

    override suspend fun asignarEncomiendaATraslado(
        trasladoId: Int,
        encomiendaId: Int
    ): Result<Unit> {

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


            if (trasladoId <= 0) {

                return Result.failure(
                    Exception(
                        "Traslado no válido"
                    )
                )
            }


            if (encomiendaId <= 0) {

                return Result.failure(
                    Exception(
                        "Encomienda no válida"
                    )
                )
            }


            val cleanToken =
                token
                    .removePrefix("Bearer ")
                    .trim()


            val requestBody =
                AsignarEncomiendaTrasladoRequestDto(

                    trasladoId =
                        trasladoId,

                    encomiendaId =
                        encomiendaId
                )


            val response =
                client.post(
                    "$baseUrl/traslado-encomienda"
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
                "ASIGNAR MOVILIDAD STATUS: ${response.status}"
            )

            println(
                "ASIGNAR MOVILIDAD BODY: $responseBody"
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

        } catch (e: Exception) {

            println(
                "ERROR ASIGNAR MOVILIDAD: ${e.message}"
            )


            Result.failure(
                e
            )
        }
    }


    private fun obtenerMensajeError(
        responseBody: String
    ): String {

        return try {

            val elemento =
                json.parseToJsonElement(
                    responseBody
                )


            val objeto =
                elemento.jsonObject


            val mensaje =
                objeto["message"]


            if (mensaje != null) {

                mensaje
                    .toString()
                    .removePrefix("\"")
                    .removeSuffix("\"")

            } else {

                responseBody
            }

        } catch (_: Exception) {

            responseBody
        }
    }

}


@Serializable
private data class AsignarEncomiendaTrasladoRequestDto(

    val trasladoId: Int,

    val encomiendaId: Int
)


@Serializable
private data class ListaTrasladosDisponiblesDto(

    @SerialName("sede_origen_id")
    val sedeOrigenId: Int,

    @SerialName("sede_destino_id")
    val sedeDestinoId: Int,

    val total: Int,

    val traslados:
    List<TrasladoDisponibleDto>
)



@Serializable
private data class TrasladoDisponibleDto(

    val trasladoId: Int,

    val estado: String,

    val vehiculo:
    VehiculoTrasladoDto,

    val conductor:
    ConductorTrasladoDto,

    val recorrido:
    List<String>
)



@Serializable
private data class VehiculoTrasladoDto(

    val id: Int,

    val placa: String,

    val descripcion: String,

    @SerialName("capacidad_kg")
    val capacidadKg: Double,

    @SerialName("carga_actual_kg")
    val cargaActualKg: Double,

    @SerialName("capacidad_disponible_kg")
    val capacidadDisponibleKg: Double
)


@Serializable
private data class ConductorTrasladoDto(

    val id: Int,

    val name: String
)


private fun ListaTrasladosDisponiblesDto.toDomain():
        ListaTrasladosDisponiblesAlmacen {

    return ListaTrasladosDisponiblesAlmacen(

        sedeOrigenId =
            sedeOrigenId,

        sedeDestinoId =
            sedeDestinoId,

        total =
            total,

        traslados =
            traslados.map {
                it.toDomain()
            }
    )
}


private fun TrasladoDisponibleDto.toDomain():
        TrasladoDisponibleAlmacen {

    return TrasladoDisponibleAlmacen(

        trasladoId =
            trasladoId,

        estado =
            estado,

        vehiculo =
            VehiculoTrasladoAlmacen(

                id =
                    vehiculo.id,

                placa =
                    vehiculo.placa,

                descripcion =
                    vehiculo.descripcion,

                capacidadKg =
                    vehiculo.capacidadKg,

                cargaActualKg =
                    vehiculo.cargaActualKg,

                capacidadDisponibleKg =
                    vehiculo.capacidadDisponibleKg
            ),

        conductor =
            ConductorTrasladoAlmacen(

                id =
                    conductor.id,

                name =
                    conductor.name
            ),

        recorrido =
            recorrido
    )
}