package pe.edu.pe.Datas.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.Datas.remote.dto.MarkAllNotificationsReadResponseDto
import pe.edu.pe.Datas.remote.dto.MarkNotificationReadResponseDto
import pe.edu.pe.Datas.remote.dto.RemoteNotificationDto
import pe.edu.pe.Datas.remote.dto.UnreadNotificationCountDto

class NotificationsApi(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) {

    private val baseUrl =
        ApiClient.getBaseUrl()

    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    suspend fun getNotifications(): Result<List<RemoteNotificationDto>> {
        return execute {
            client.get("$baseUrl/notifications") {
                bearerAuth(obtenerToken())
            }
        }
    }

    suspend fun getUnreadCount(): Result<UnreadNotificationCountDto> {
        return execute {
            client.get("$baseUrl/notifications/unread-count") {
                bearerAuth(obtenerToken())
            }
        }
    }

    suspend fun markAsRead(
        notificationId: Int
    ): Result<MarkNotificationReadResponseDto> {
        if (notificationId <= 0) {
            return Result.failure(
                IllegalArgumentException(
                    "El ID de la notificación no es válido"
                )
            )
        }

        return execute {
            client.patch(
                "$baseUrl/notifications/$notificationId/read"
            ) {
                bearerAuth(obtenerToken())
            }
        }
    }

    suspend fun markAllAsRead(): Result<MarkAllNotificationsReadResponseDto> {
        return execute {
            client.patch("$baseUrl/notifications/read-all") {
                bearerAuth(obtenerToken())
            }
        }
    }

    private fun obtenerToken(): String {
        val token =
            sessionManager.getToken()
                ?.removePrefix("Bearer ")
                ?.trim()

        if (token.isNullOrBlank()) {
            throw IllegalStateException(
                "No existe una sesión activa"
            )
        }

        return token
    }

    private suspend inline fun <reified T> execute(
        crossinline request: suspend () -> HttpResponse
    ): Result<T> {
        return try {
            val response = request()
            val responseBody = response.bodyAsText()

            if (response.status.isSuccess()) {
                Result.success(
                    json.decodeFromString<T>(responseBody)
                )
            } else {
                Result.failure(
                    NotificationsApiException(
                        statusCode = response.status.value,
                        message = obtenerMensajeError(responseBody)
                    )
                )
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: SerializationException) {
            Result.failure(exception)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun obtenerMensajeError(
        responseBody: String
    ): String {
        if (responseBody.isBlank()) {
            return "Error al comunicarse con el servidor"
        }

        return try {
            val message =
                json.parseToJsonElement(responseBody)
                    .jsonObject["message"]

            when (message) {
                is JsonPrimitive ->
                    message.contentOrNull?.takeIf { it.isNotBlank() }
                        ?: "Error al comunicarse con el servidor"

                is JsonArray ->
                    message
                        .mapNotNull { (it as? JsonPrimitive)?.contentOrNull }
                        .filter { it.isNotBlank() }
                        .joinToString("\n")
                        .ifBlank { "Error al comunicarse con el servidor" }

                else -> "Error al comunicarse con el servidor"
            }
        } catch (_: Exception) {
            "Error al comunicarse con el servidor"
        }
    }
}

class NotificationsApiException(
    val statusCode: Int,
    message: String
) : Exception(message)
