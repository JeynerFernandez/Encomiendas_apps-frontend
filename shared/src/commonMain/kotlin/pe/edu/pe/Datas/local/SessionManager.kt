package pe.edu.pe.Datas.local

import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pe.edu.pe.domain.model.User

class SessionManager(
    private val setting: Settings
) {

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER = "user_data"
    }

    // Guarda el JWT después de iniciar sesión
    fun saveToken(token: String) {

        setting.putString(
            KEY_TOKEN,
            token
        )

        println(
            "DEBUG_SESSION: Token guardado correctamente"
        )
    }

    // Recupera el JWT para enviarlo a endpoints protegidos
    fun getToken(): String? {

        val token =
            setting.getStringOrNull(
                KEY_TOKEN
            )

        println(
            "DEBUG_SESSION: Existe token = ${token != null}"
        )

        return token
    }

    // Guarda los datos del usuario autenticado
    fun saveUser(user: User) {

        val json =
            Json.encodeToString(
                user
            )

        setting.putString(
            KEY_USER,
            json
        )
    }

    fun getUser(): User? {

        val json =
            setting.getStringOrNull(
                KEY_USER
            )

        return try {

            Json.decodeFromString<User>(
                json ?: return null
            )

        } catch (e: Exception) {

            null
        }
    }

    // Elimina la sesión al cerrar sesión
    fun clear() {

        setting.remove(
            KEY_TOKEN
        )

        setting.remove(
            KEY_USER
        )
    }

    fun isLoggedIn(): Boolean {

        return getToken() != null
    }
}