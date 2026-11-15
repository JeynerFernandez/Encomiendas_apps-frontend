package pe.edu.pe.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

private const val GOOGLE_SIGNIN_TAG = "MandaYa_GoogleSignIn"

private fun logGoogleSignIn(message: String) {
    Log.d(GOOGLE_SIGNIN_TAG, message)
}

private fun logGoogleSignInError(message: String, error: Throwable?) {
    Log.e(
        GOOGLE_SIGNIN_TAG,
        "$message | exceptionClass=${error?.javaClass?.name} | exceptionMessage=${error?.message}",
        error
    )
}

@Composable
actual fun rememberGoogleSignInLauncher(
    webClientId: String,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
): (String) -> Unit {

    val context = LocalContext.current

    val coroutineScope =
        rememberCoroutineScope()

    val successCallback =
        rememberUpdatedState(onSuccess)

    val errorCallback =
        rememberUpdatedState(onError)

    val isSigningIn = remember {
        mutableStateOf(false)
    }

    val credentialManager = remember(context) {

        CredentialManager.create(
            context
        )
    }

    return { nonce ->

        logGoogleSignIn("Inicio del proceso de autenticación con Google")

        logGoogleSignIn("Validación del nonce: nonce recibido")

        if (nonce.isBlank()) {

            errorCallback.value(
                "No se pudo obtener el desafío de seguridad"
            )

        } else if (!isSigningIn.value) {

            isSigningIn.value = true

            coroutineScope.launch {

                try {

                    logGoogleSignIn("Creación del GetSignInWithGoogleOption")

                    val googleIdOption =
                        GetSignInWithGoogleOption
                            .Builder(
                                serverClientId = webClientId
                            )
                            .setNonce(
                                nonce
                            )
                            .build()

                    logGoogleSignIn("Creación del GetCredentialRequest")

                    val request =
                        GetCredentialRequest
                            .Builder()
                            .addCredentialOption(
                                googleIdOption
                            )
                            .build()

                    logGoogleSignIn("Inicio de la llamada a credentialManager.getCredential()")

                    val result =
                        credentialManager.getCredential(
                            context = context,
                            request = request
                        )

                    logGoogleSignIn("Respuesta de Credential Manager recibida")

                    val credential =
                        result.credential

                    logGoogleSignIn("Tipo de credencial recibida = ${credential?.javaClass?.simpleName ?: "null"}")

                    if (
                        credential is CustomCredential &&
                        credential.type ==
                        GoogleIdTokenCredential
                            .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {

                        logGoogleSignIn("Inicio de la conversión con GoogleIdTokenCredential.createFrom()")

                        val googleCredential =
                            GoogleIdTokenCredential
                                .createFrom(
                                    credential.data
                                )

                        val idToken =
                            googleCredential.idToken

                        if (idToken.isBlank()) {
                            logGoogleSignIn("ID Token: vacío")
                        } else {
                            logGoogleSignIn("ID Token: extraído correctamente")
                        }

                        logGoogleSignIn("Invocando successCallback de autenticación con Google")

                        successCallback.value(
                            idToken
                        )

                    } else {

                        logGoogleSignIn("Tipo de credencial no válido o no soportado")

                        errorCallback.value(
                            "No se pudo obtener la cuenta de Google"
                        )
                    }

                } catch (
                    e: GetCredentialCancellationException
                ) {

                    logGoogleSignInError("GetCredentialCancellationException", e)

                    errorCallback.value(
                        "Inicio de sesión cancelado"
                    )

                } catch (
                    e: GoogleIdTokenParsingException
                ) {

                    logGoogleSignInError("GoogleIdTokenParsingException", e)

                    errorCallback.value(
                        "No se pudo procesar la cuenta de Google"
                    )

                } catch (
                    e: GetCredentialException
                ) {

                    logGoogleSignInError("GetCredentialException", e)
                    logGoogleSignIn("Excepción capturada en Credential Manager: clase=${e.javaClass.name}, mensaje=${e.message}")

                    errorCallback.value(
                        "No se pudo iniciar sesión con Google"
                    )

                } catch (
                    e: CancellationException
                ) {

                    logGoogleSignIn("CancellationException capturada: clase=${e.javaClass.name}")

                    throw e

                } catch (
                    e: Exception
                ) {

                    logGoogleSignInError("Excepción no controlada capturada", e)

                    errorCallback.value(
                        "No se pudo conectar con Google"
                    )

                } finally {

                    isSigningIn.value = false
                }
            }
        }
    }
}
