package pe.edu.pe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import org.jetbrains.compose.resources.painterResource
import encomienda_appmovil.shared.generated.resources.Res
import encomienda_appmovil.shared.generated.resources.logo_mandaya


@Composable
fun LoginScreen(

    onLoginClick: (
        String,
        String
    ) -> Unit,

    onRegisterClick: () -> Unit,

    onForgotPasswordClick: () -> Unit,

    onGoogleClick: () -> Unit,

    isLoading: Boolean = false,

    errorMessage: String? = null
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme
                    .colorScheme
                    .background
            )
            .safeDrawingPadding()
            .imePadding()
    ) {

        val availableHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = availableHeight)
                .padding(
                    horizontal = 24.dp
                )
                .padding(
                    vertical = 24.dp
                ),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {






            Image(
                painter =
                    painterResource(
                        Res.drawable.logo_mandaya
                    ),

                contentDescription =
                    "Logo MANDA YA",

                modifier =
                    Modifier
                        .size(130.dp)
                        .clip(CircleShape),

                contentScale =
                    ContentScale.Crop
            )


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            Text(
                text = "MandaYA",

                fontSize =
                    20.sp,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                textAlign =
                    TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(36.dp)
            )






            Text(
                text =
                    "Correo Electrónico",

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            OutlinedTextField(

                value =
                    email,

                onValueChange = {

                    email = it
                },

                placeholder = {

                    Text(
                        "Ej: user@example.com"
                    )
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                shape =
                    RoundedCornerShape(
                        8.dp
                    ),

                colors =
                    OutlinedTextFieldDefaults
                        .colors(

                            focusedBorderColor =
                                MaterialTheme
                                    .colorScheme
                                    .primary,

                            unfocusedBorderColor =
                                MaterialTheme
                                    .colorScheme
                                    .outlineVariant,

                            focusedContainerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface,

                            unfocusedContainerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface
                        ),

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Email,

                        contentDescription =
                            null,

                        tint =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                },

                singleLine =
                    true,

                enabled =
                    !isLoading,

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Email
                    )
            )


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )






            Text(
                text =
                    "Contraseña",

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            OutlinedTextField(

                value =
                    password,

                onValueChange = {

                    password = it
                },

                placeholder = {

                    Text(
                        "Contraseña"
                    )
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                shape =
                    RoundedCornerShape(
                        8.dp
                    ),

                colors =
                    OutlinedTextFieldDefaults
                        .colors(

                            focusedBorderColor =
                                MaterialTheme
                                    .colorScheme
                                    .primary,

                            unfocusedBorderColor =
                                MaterialTheme
                                    .colorScheme
                                    .outlineVariant,

                            focusedContainerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface,

                            unfocusedContainerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface
                        ),

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Lock,

                        contentDescription =
                            null,

                        tint =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                },

                trailingIcon = {

                    IconButton(

                        onClick = {

                            passwordVisible =
                                !passwordVisible
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (passwordVisible) {

                                    Icons.Default.Visibility

                                } else {

                                    Icons.Default.VisibilityOff
                                },

                            contentDescription =
                                if (passwordVisible) {

                                    "Ocultar contraseña"

                                } else {

                                    "Mostrar contraseña"
                                },

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )
                    }
                },

                singleLine =
                    true,

                enabled =
                    !isLoading,

                visualTransformation =
                    if (passwordVisible) {

                        VisualTransformation.None

                    } else {

                        PasswordVisualTransformation()
                    },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Password
                    )
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )






            Text(
                text =
                    "¿Olvidaste tu contraseña?",

                fontSize =
                    14.sp,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                fontWeight =
                    FontWeight.Medium,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable(
                            enabled = !isLoading
                        ) {

                            onForgotPasswordClick()
                        }
                        .padding(
                            vertical = 4.dp
                        ),

                textAlign =
                    TextAlign.End
            )






            if (
                !errorMessage.isNullOrBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Text(
                    text =
                        errorMessage,

                    color =
                        MaterialTheme
                            .colorScheme
                            .error,

                    fontSize =
                        13.sp,

                    modifier =
                        Modifier.fillMaxWidth(),

                    textAlign =
                        TextAlign.Center
                )
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )






            Button(

                onClick = {

                    if (
                        email.isNotBlank() &&
                        password.isNotBlank()
                    ) {

                        onLoginClick(
                            email.trim(),
                            password.trim()
                        )
                    }
                },

                enabled =
                    !isLoading &&
                            email.isNotBlank() &&
                            password.isNotBlank(),

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                shape =
                    RoundedCornerShape(
                        8.dp
                    ),

                colors =
                    ButtonDefaults
                        .buttonColors(

                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )
            ) {

                Text(
                    text =
                        if (isLoading) {
                            "INGRESANDO..."
                        } else {
                            "INICIAR SESIÓN"
                        },

                    fontSize =
                        16.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onPrimary
                )
            }


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )






            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Divider(
                    modifier =
                        Modifier.weight(1f),

                    color =
                        MaterialTheme
                            .colorScheme
                            .outlineVariant
                )

                Text(
                    text =
                        "O registrarse con",

                    fontSize =
                        13.sp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant,

                    modifier =
                        Modifier.padding(
                            horizontal = 16.dp
                        )
                )

                Divider(
                    modifier =
                        Modifier.weight(1f),

                    color =
                        MaterialTheme
                            .colorScheme
                            .outlineVariant
                )
            }


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )






            OutlinedButton(

                onClick =
                    onGoogleClick,

                enabled =
                    !isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                shape =
                    RoundedCornerShape(
                        8.dp
                    ),

                colors =
                    ButtonDefaults
                        .outlinedButtonColors(

                            contentColor =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        ),

                border =
                    ButtonDefaults
                        .outlinedButtonBorder
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically,

                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    Color.White
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(
                                        0xFFE0E0E0
                                    ),
                                    shape =
                                        CircleShape
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                "G",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color(
                                    0xFF4285F4
                                )
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )


                    Text(
                        text =
                            "Continuar con Google",

                        fontSize =
                            16.sp,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurface
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )






            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text =
                        "¿No tienes cuenta? ",

                    fontSize =
                        14.sp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )


                Text(
                    text =
                        "Regístrate",

                    fontSize =
                        14.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    modifier =
                        Modifier
                            .clickable(
                                enabled =
                                    !isLoading
                            ) {

                                onRegisterClick()
                            }
                )
            }

        }
    }
}
