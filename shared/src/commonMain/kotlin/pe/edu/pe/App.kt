package pe.edu.pe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.module
import pe.edu.pe.Datas.Notification.LocalNotificationPlataform
import pe.edu.pe.Datas.Notification.UnsupportedLocalNotificationPlataform
import pe.edu.pe.Datas.local.SessionManager
import pe.edu.pe.data.repository.ShipmentRepository
import pe.edu.pe.di.appModule
import pe.edu.pe.domain.model.User
import pe.edu.pe.presentation.AdminDashboardContent
import pe.edu.pe.presentation.CalculadoraCostoScreen
import pe.edu.pe.presentation.GestionEncomiendasAdminContent
import pe.edu.pe.presentation.GestionMovilidadAdminContent
import pe.edu.pe.presentation.GestionRolesAdminContent
import pe.edu.pe.presentation.GestionRutasAdminContent
import pe.edu.pe.presentation.GestionSedesAdminContent
import pe.edu.pe.presentation.GestionTarifasAdminContent
import pe.edu.pe.presentation.HomeContent
import pe.edu.pe.presentation.LoginScreen
import pe.edu.pe.presentation.rememberGoogleSignInLauncher
import pe.edu.pe.presentation.NotificationSettingsContent
import pe.edu.pe.presentation.ReportesAdminContent
import pe.edu.pe.presentation.Screen
import pe.edu.pe.presentation.SplashContent
import pe.edu.pe.presentation.movilidad.MovilidadScreen
import pe.edu.pe.presentation.notifications.RemoteNotificationsScreen
import pe.edu.pe.presentation.reception.ReceptionScreen
import pe.edu.pe.presentation.reception.delivery.PendientesEntregaScreen
import pe.edu.pe.presentation.reception.register.RegisterShipmentScreen
import pe.edu.pe.presentation.reception.register.ShipmentConfirmationScreen
import pe.edu.pe.presentation.settings.SettingsScreen
import pe.edu.pe.presentation.support.SupportScreen
import pe.edu.pe.presentation.theme.MandaYaTheme
import pe.edu.pe.presentation.trackorder.TrackingScreen
import pe.edu.pe.presentation.viewmodel.AlmacenViewModel
import pe.edu.pe.presentation.viewmodel.AuthState
import pe.edu.pe.presentation.viewmodel.AuthViewModel
import pe.edu.pe.presentation.viewmodel.NotificationSettingsViewModel
import pe.edu.pe.presentation.viewmodel.RutaViewModel
import pe.edu.pe.presentation.viewmodel.RemoteNotificationsViewModel
import pe.edu.pe.presentation.warehouse.WarehouseScreen

private const val GOOGLE_WEB_CLIENT_ID = "277251979945-l3vt6mmkbevq5sbd2447nvtvqu4rd38b.apps.googleusercontent.com"

@Composable
fun App(
    notificationPlatform: LocalNotificationPlataform = UnsupportedLocalNotificationPlataform
) {
    KoinApplication(
        application = {
            modules(
                appModule,
                module {
                    single<LocalNotificationPlataform> { notificationPlatform }
                }
            )
        }
    ) {
        MandaYaApp()
    }
}

@Composable
private fun MandaYaApp() {
    val authViewModel = koinViewModel<AuthViewModel>()
    val remoteNotificationsViewModel = koinViewModel<RemoteNotificationsViewModel>()
    val sessionManager = koinInject<SessionManager>()
    val authState by authViewModel.state.collectAsState()
    val remoteNotificationsState by remoteNotificationsViewModel.uiState.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
    var currentUser by remember { mutableStateOf<User?>(null) }
    var isDarkMode by remember { mutableStateOf(false) }
    var fontSizeScale by remember { mutableStateOf(1.0f) }
    var isLoggedIn by remember { mutableStateOf(false) }
    val notificationsRoleEnabled =
        isLoggedIn && currentUser?.role?.uppercase() == "CLIENTE"

    LaunchedEffect(Unit) {
        authViewModel.restoreSession()
    }

    LaunchedEffect(isLoggedIn, currentUser?.role) {
        if (notificationsRoleEnabled) {
            remoteNotificationsViewModel.loadNotifications()
        } else {
            remoteNotificationsViewModel.clearNotifications()
        }
    }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                currentUser = state.user
                isLoggedIn = true

                currentScreen = when (state.user.role.uppercase()) {
                    "CLIENTE" -> Screen.Home
                    "ADMINISTRADOR" -> Screen.VistaAdmin
                    "MOVILIDAD" -> Screen.Movilidad
                    "RECEPCION" -> Screen.Reception
                    "ALMACEN" -> Screen.Warehouse
                    else -> {
                        sessionManager.clear()
                        remoteNotificationsViewModel.clearNotifications()
                        isLoggedIn = false
                        currentUser = null
                        Screen.Login
                    }
                }
                authViewModel.resetState()
            }

            AuthState.NoSession,
            is AuthState.SessionExpired -> {
                remoteNotificationsViewModel.clearNotifications()
                currentUser = null
                isLoggedIn = false
                currentScreen = Screen.Login
            }

            is AuthState.SessionRestoreError -> {
                currentScreen = Screen.Splash
            }

            else -> Unit
        }
    }

    var trackingCode by remember { mutableStateOf("") }
    var shipmentPassword by remember { mutableStateOf("") }
    var senderDni by remember { mutableStateOf("") }
    var recipientDni by remember { mutableStateOf("") }
    var originBranch by remember { mutableStateOf("") }
    var destinationBranch by remember { mutableStateOf("") }
    var shipmentWeight by remember { mutableStateOf("") }
    var shipmentHeight by remember { mutableStateOf("") }
    var shipmentWidth by remember { mutableStateOf("") }
    var shipmentLength by remember { mutableStateOf("") }
    var shipmentBillableWeight by remember { mutableStateOf("") }
    var shipmentCost by remember { mutableStateOf("") }
    var shipmentPaymentMethod by remember { mutableStateOf("") }

    val shipments = ShipmentRepository.shipments

    MandaYaTheme(
        darkTheme = isDarkMode,
        fontSizeScale = fontSizeScale
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            when (currentScreen) {
                is Screen.Splash -> {
                    val restoreError = authState as? AuthState.SessionRestoreError

                    if (restoreError == null) {
                        SplashContent(
                            onTimeout = {
                                if (
                                    authState is AuthState.NoSession ||
                                    authState is AuthState.SessionExpired
                                ) {
                                    currentScreen = Screen.Login
                                }
                            }
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No se pudo restaurar la sesión"
                            )
                            Text(
                                text = restoreError.message
                            )
                            TextButton(
                                onClick = {
                                    authViewModel.restoreSession()
                                }
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                is Screen.Login -> {
                    val googleSignInLauncher = rememberGoogleSignInLauncher(
                        webClientId = GOOGLE_WEB_CLIENT_ID,
                        onSuccess = authViewModel::onGoogleToken,
                        onError = authViewModel::onGoogleError
                    )

                    LaunchedEffect(authState) {
                        if (authState is AuthState.GoogleChallengeReady) {
                            val nonce = authViewModel.consumeGoogleChallenge()
                            if (nonce != null) {
                                googleSignInLauncher(nonce)
                            }
                        }
                    }

                    LoginScreen(
                        isLoading = authState.isBusy || authState is AuthState.Success,
                        errorMessage = (authState as? AuthState.Error)?.message,
                        onLoginClick = { email, password ->
                            authViewModel.login(email = email, password = password)
                        },
                        onRegisterClick = {},
                        onForgotPasswordClick = {},
                        onGoogleClick = {
                            authViewModel.requestGoogleChallenge()
                        }
                    )

                    val linkState = authState as? AuthState.GoogleLinkRequired

                    if (linkState != null) {
                        var linkPassword by remember { mutableStateOf("") }

                        AlertDialog(
                            onDismissRequest = {
                                linkPassword = ""
                                authViewModel.resetState()
                            },
                            title = {
                                Text("Vincular cuenta de Google")
                            },
                            text = {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(linkState.message)
                                    Text("Ingresa la contraseña de MANDA YA y vuelve a seleccionar la misma cuenta de Google.")
                                    OutlinedTextField(
                                        value = linkPassword,
                                        onValueChange = { linkPassword = it },
                                        label = { Text("Contraseña de MANDA YA") },
                                        singleLine = true,
                                        visualTransformation = PasswordVisualTransformation(),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Password
                                        )
                                    )
                                }
                            },
                            confirmButton = {
                                TextButton(
                                    enabled = linkPassword.isNotBlank(),
                                    onClick = {
                                        val password = linkPassword
                                        linkPassword = ""
                                        authViewModel.requestGoogleLinkChallenge(password)
                                    }
                                ) {
                                    Text("Vincular")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        linkPassword = ""
                                        authViewModel.resetState()
                                    }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }

                is Screen.Home -> {
                    HomeContent(
                        isLoggedIn = isLoggedIn,
                        onLogout = {
                            sessionManager.clear()
                            remoteNotificationsViewModel.clearNotifications()
                            isLoggedIn = false
                            currentUser = null
                            currentScreen = Screen.Login
                        },
                        onEditProfile = {},
                        onCartClick = {},
                        onCalcularEncomienda = { currentScreen = Screen.CalcularEncomienda },
                        onSupportClick = { currentScreen = Screen.Support },
                        onTrackingClick = { currentScreen = Screen.Tracking },
                        onSettingsClick = { currentScreen = Screen.Ajustes },
                        cartSize = 0,
                        onSettingsClicked = { currentScreen = Screen.NotificationSetting },
                        onNotificationsClick = { currentScreen = Screen.RemoteNotifications },
                        remoteNotificationsState = remoteNotificationsState,
                        currentUser = currentUser
                    )
                }

                is Screen.Support -> {
                    SupportScreen(
                        onBackClick = { currentScreen = Screen.Home }
                    )
                }

                is Screen.Tracking -> {
                    TrackingScreen(
                        onBackClick = { currentScreen = Screen.Home }
                    )
                }

                is Screen.Ajustes -> {
                    SettingsScreen(
                        onBackClick = { currentScreen = Screen.Home },
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { isDarkMode = it },
                        fontSizeScale = fontSizeScale,
                        onFontSizeScaleChange = { fontSizeScale = it }
                    )
                }

                is Screen.NotificationSetting -> {
                    val notificationSettingsViewModel = koinViewModel<NotificationSettingsViewModel>()
                    NotificationSettingsContent(
                        viewModel = notificationSettingsViewModel,
                        onBack = { currentScreen = Screen.Home }
                    )
                }

                is Screen.RemoteNotifications -> {
                    RemoteNotificationsScreen(
                        viewModel = remoteNotificationsViewModel,
                        onBack = { currentScreen = Screen.Home }
                    )
                }

                is Screen.CalcularEncomienda -> {
                    CalculadoraCostoScreen(
                        onSalirClick = { currentScreen = Screen.Home }
                    )
                }

                is Screen.Movilidad -> {
                    MovilidadScreen(
                        onBackClick = { currentScreen = Screen.Home }
                    )
                }

                is Screen.VistaAdmin -> {
                    AdminDashboardContent(
                        onReportesClick = { currentScreen = Screen.VitsaAdminReporte },
                        onEncomiendasClick = { currentScreen = Screen.VistaAdminEncomiendas },
                        onMovilidadClick = { currentScreen = Screen.VistaAdminMovilidad },
                        onSedesClick = { currentScreen = Screen.VistaAdminSedes },
                        onTarifasClick = { currentScreen = Screen.Tarifas },
                        onRolesClick = { currentScreen = Screen.VistaAdminRoles },
                        onRutasClick = { currentScreen = Screen.VistaAdminRutas },
                        onProfileClick = {},
                        onLogout = {
                            sessionManager.clear()
                            remoteNotificationsViewModel.clearNotifications()
                            isLoggedIn = false
                            currentUser = null
                            currentScreen = Screen.Login
                        }
                    )
                }

                is Screen.VitsaAdminReporte -> {
                    ReportesAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.VistaAdminEncomiendas -> {
                    GestionEncomiendasAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.VistaAdminSedes -> {
                    GestionSedesAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.VistaAdminRutas -> {
                    val rutaViewModel: RutaViewModel = koinViewModel()
                    GestionRutasAdminContent(
                        viewModel = rutaViewModel,
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.Tarifas -> {
                    GestionTarifasAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.VistaAdminRoles -> {
                    GestionRolesAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.VistaAdminMovilidad -> {
                    GestionMovilidadAdminContent(
                        onSalirClick = { currentScreen = Screen.VistaAdmin }
                    )
                }

                is Screen.Reception -> {
                    ReceptionScreen(
                        onRegisterShipment = { currentScreen = Screen.RegisterShipment },
                        onDeliverWarehouse = { currentScreen = Screen.DeliverWarehouse },
                        onHistory = {},
                        onProfile = {},
                        onLogout = {
                            sessionManager.clear()
                            remoteNotificationsViewModel.clearNotifications()
                            isLoggedIn = false
                            currentUser = null
                            currentScreen = Screen.Login
                        },
                        shipments = shipments
                    )
                }

                is Screen.DeliverWarehouse -> {
                    PendientesEntregaScreen(
                        onBack = { currentScreen = Screen.Reception }
                    )
                }

                is Screen.RegisterShipment -> {
                    RegisterShipmentScreen(
                        onBack = { currentScreen = Screen.Reception },
                        recepcionSedeId = currentUser?.sedeId,
                        onShipmentGenerated = { code, password, sender, recipient, origin, destination, weight, height, width, length, billableWeight, cost, payment ->
                            trackingCode = code
                            shipmentPassword = password
                            senderDni = sender
                            recipientDni = recipient
                            originBranch = origin
                            destinationBranch = destination
                            shipmentWeight = weight
                            shipmentHeight = height
                            shipmentWidth = width
                            shipmentLength = length
                            shipmentBillableWeight = billableWeight
                            shipmentCost = cost
                            shipmentPaymentMethod = payment
                            currentScreen = Screen.ShipmentConfirmation
                        }
                    )
                }

                is Screen.ShipmentConfirmation -> {
                    ShipmentConfirmationScreen(
                        trackingCode = trackingCode,
                        shipmentPassword = shipmentPassword,
                        senderDni = senderDni,
                        recipientDni = recipientDni,
                        originBranch = originBranch,
                        destinationBranch = destinationBranch,
                        weight = shipmentWeight,
                        height = shipmentHeight,
                        width = shipmentWidth,
                        length = shipmentLength,
                        billableWeight = shipmentBillableWeight,
                        shippingCost = shipmentCost,
                        paymentMethod = shipmentPaymentMethod,
                        onBack = { currentScreen = Screen.Reception }
                    )
                }

                is Screen.Warehouse -> {
                    val almacenViewModel: AlmacenViewModel = koinInject()
                    WarehouseScreen(
                        viewModel = almacenViewModel,
                        onBackClick = {
                            sessionManager.clear()
                            remoteNotificationsViewModel.clearNotifications()
                            isLoggedIn = false
                            currentUser = null
                            currentScreen = Screen.Login
                        }
                    )
                }
            }
        }
    }
}
