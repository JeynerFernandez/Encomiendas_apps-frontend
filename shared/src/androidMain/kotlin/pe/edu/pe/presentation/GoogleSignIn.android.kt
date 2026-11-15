
package pe.edu.pe.presentation

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

        if (nonce.isBlank()) {

            errorCallback.value(
                "No se pudo obtener el desafío de seguridad"
            )

        } else if (!isSigningIn.value) {

            isSigningIn.value = true

            coroutineScope.launch {

                try {

                    val googleIdOption =
                        GetSignInWithGoogleOption
                            .Builder(
                                serverClientId = webClientId
                            )
                            .setNonce(
                                nonce
                            )
                            .build()

                    val request =
                        GetCredentialRequest
                            .Builder()
                            .addCredentialOption(
                                googleIdOption
                            )
                            .build()

                    val result =
                        credentialManager.getCredential(
                            context = context,
                            request = request
                        )

                    val credential =
                        result.credential

                    if (
                        credential is CustomCredential &&
                        credential.type ==
                        GoogleIdTokenCredential
                            .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {

                        val googleCredential =
                            GoogleIdTokenCredential
                                .createFrom(
                                    credential.data
                                )

                        val idToken =
                            googleCredential.idToken

                        successCallback.value(
                            idToken
                        )

                    } else {

                        errorCallback.value(
                            "No se pudo obtener la cuenta de Google"
                        )
                    }

                } catch (
                    e: GetCredentialCancellationException
                ) {

                    errorCallback.value(
                        "Inicio de sesión cancelado"
                    )

                } catch (
                    e: GoogleIdTokenParsingException
                ) {

                    errorCallback.value(
                        "No se pudo procesar la cuenta de Google"
                    )

                } catch (
                    e: GetCredentialException
                ) {

                    errorCallback.value(
                        "No se pudo iniciar sesión con Google"
                    )

                } catch (
                    e: CancellationException
                ) {

                    throw e

                } catch (
                    e: Exception
                ) {

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
