
package pe.edu.pe.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGoogleSignInLauncher(
    webClientId: String,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
): (String) -> Unit
