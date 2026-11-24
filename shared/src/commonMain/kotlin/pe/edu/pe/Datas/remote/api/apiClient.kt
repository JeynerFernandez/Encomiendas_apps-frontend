
package pe.edu.pe.Datas.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    private object Configuration {
        const val BASE_URL = "http://10.0.2.2:3000"
    }

    private val safeHttpLogger = object : Logger {
        override fun log(message: String) {
            println(
                "HTTP: ${sanitizeHttpLog(message)}"
            )
        }
    }

    val httpClient = HttpClient {

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 30_000
        }

        install(ContentNegotiation) {

            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = safeHttpLogger
            level = LogLevel.INFO

            sanitizeHeader { header ->
                header.equals(
                    HttpHeaders.Authorization,
                    ignoreCase = true
                ) || header.equals(
                    HttpHeaders.Cookie,
                    ignoreCase = true
                ) || header.equals(
                    HttpHeaders.SetCookie,
                    ignoreCase = true
                )
            }
        }
    }

    fun getBaseUrl() = Configuration.BASE_URL

    private fun sanitizeHttpLog(
        message: String
    ): String {
        val sensitiveQueryParameterPattern =
            Regex(
                "(?i)([?&](?:dni|pin|token|access_token|codigo|codigo_rastreo|clave|clave_rastreo|password|passwd|secret)=)[^&#\\s]*"
            )

        val sensitivePathSegmentPattern =
            Regex(
                "(?i)(/(?:dni|pin|token|codigo|codigo_rastreo|clave|clave_rastreo|password|passwd|secret|tracking)/)[^/?\\s]+"
            )

        return message
            .replace(
                Regex(
                    "(?i)(authorization\\s*[:=]\\s*(?:Bearer\\s+)?)[^\\s,}\\]]+"
                ),
                "$1[REDACTED]"
            )
            .replace(
                sensitiveQueryParameterPattern,
                "$1[REDACTED]"
            )
            .replace(
                sensitivePathSegmentPattern,
                "$1[REDACTED]"
            )
    }
}
