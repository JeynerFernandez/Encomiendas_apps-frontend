
package pe.edu.pe.presentation

import androidx.compose.runtime.Composable

@Composable
actual fun rememberGoogleSignInLauncher(
    webClientId: String,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
): (String) -> Unit {

    return { _ ->

        onError(
            "El inicio de sesión con Google aún no está disponible en iOS"
        )
    }
}
