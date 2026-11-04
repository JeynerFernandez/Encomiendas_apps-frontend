package pe.edu.pe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

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
import pe.edu.pe.presentation.ReportesAdminContent
import pe.edu.pe.presentation.Screen
import pe.edu.pe.presentation.SplashContent

import pe.edu.pe.presentation.movilidad.MovilidadScreen

import pe.edu.pe.presentation.reception.ReceptionScreen
import pe.edu.pe.presentation.reception.delivery.PendientesEntregaScreen
import pe.edu.pe.presentation.reception.register.RegisterShipmentScreen
import pe.edu.pe.presentation.reception.register.ShipmentConfirmationScreen

import pe.edu.pe.presentation.settings.SettingsScreen
import pe.edu.pe.presentation.support.SupportScreen
import pe.edu.pe.presentation.theme.MandaYaTheme
import pe.edu.pe.presentation.trackorder.TrackingScreen
import pe.edu.pe.presentation.viewmodel.AlmacenViewModel
import pe.edu.pe.presentation.warehouse.WarehouseScreen

import pe.edu.pe.presentation.viewmodel.AuthState
import pe.edu.pe.presentation.viewmodel.AuthViewModel
import pe.edu.pe.presentation.viewmodel.RutaViewModel


@Composable
fun App() {

    KoinApplication(
        application = {

            modules(
                appModule
            )
        }
    ) {

        MandaYaApp()
    }
}


@Composable
private fun MandaYaApp() {


    // =====================================================
    // VIEWMODEL DE AUTENTICACIÓN
    // =====================================================

    val authViewModel =
        koinViewModel<AuthViewModel>()


    val authState by
    authViewModel.state
        .collectAsState()


    // =====================================================
    // PANTALLA ACTUAL
    // =====================================================

    var currentScreen by remember {

        mutableStateOf<Screen>(
            Screen.Splash
        )
    }


    // =====================================================
    // USUARIO ACTUAL
    // =====================================================

    // Guardamos al usuario después de iniciar sesión.
    // Esto es importante porque necesitamos su sedeId
    // para el módulo de Recepción.

    var currentUser by remember {

        mutableStateOf<User?>(
            null
        )
    }


    // =====================================================
    // CONFIGURACIÓN DE LA APP
    // =====================================================

    var isDarkMode by remember {

        mutableStateOf(
            false
        )
    }


    var fontSizeScale by remember {

        mutableStateOf(
            1.0f
        )
    }


    var isLoggedIn by remember {

        mutableStateOf(
            false
        )
    }


    // =====================================================
    // OBSERVAMOS EL LOGIN
    // =====================================================

    LaunchedEffect(
        authState
    ) {

        when (
            val state =
                authState
        ) {

            is AuthState.Success -> {


                // Guardamos el usuario antes de limpiar
                // el estado del AuthViewModel.
                currentUser =
                    state.user


                isLoggedIn =
                    true


                // Navegación según el rol.
                currentScreen =
                    when (
                        state.user.role
                            .uppercase()
                    ) {

                        "CLIENTE" -> {

                            Screen.Home
                        }


                        "ADMINISTRADOR" -> {

                            Screen.VistaAdmin
                        }


                        "MOVILIDAD" -> {

                            Screen.Movilidad
                        }


                        "RECEPCION" -> {

                            Screen.Reception
                        }


                        "ALMACEN" -> {

                            Screen.Warehouse
                        }


                        else -> {

                            isLoggedIn =
                                false

                            currentUser =
                                null

                            Screen.Login
                        }
                    }


                // Limpiamos Loading/Success del login,
                // pero el usuario ya quedó guardado
                // en currentUser.
                authViewModel
                    .resetState()
            }


            else -> Unit
        }
    }


    // =====================================================
    // DATOS DE CONFIRMACIÓN DE ENCOMIENDA
    // =====================================================

    // Estos datos reciben la respuesta REAL
    // después del POST /encomienda.


    var trackingCode by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentPassword by remember {

        mutableStateOf(
            ""
        )
    }


    var senderDni by remember {

        mutableStateOf(
            ""
        )
    }


    var recipientDni by remember {

        mutableStateOf(
            ""
        )
    }


    var originBranch by remember {

        mutableStateOf(
            ""
        )
    }


    var destinationBranch by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentWeight by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentHeight by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentWidth by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentLength by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentBillableWeight by remember {

        mutableStateOf(
            ""
        )
    }


    var shipmentCost by remember {

        mutableStateOf(
            ""
        )
    }


    // Modalidad de pago que mostraremos
    // en la confirmación.
    var shipmentPaymentMethod by remember {

        mutableStateOf(
            ""
        )
    }


    // =====================================================
    // REPOSITORIO LOCAL ANTIGUO
    // =====================================================

    // Todavía se mantiene porque ReceptionScreen
    // y DeliverWarehouseScreen actualmente lo utilizan.
    //
    // IMPORTANTE:
    // Las nuevas encomiendas ya NO se agregan aquí
    // después de registrarlas.
    //
    // El registro real se guarda en MySQL mediante:
    // POST /encomienda.

    val shipments =
        ShipmentRepository.shipments


    // =====================================================
    // TEMA PRINCIPAL
    // =====================================================

    MandaYaTheme(

        darkTheme =
            isDarkMode,

        fontSizeScale =
            fontSizeScale

    ) {

        Surface(
            modifier =
                Modifier.fillMaxSize()
        ) {


            // =====================================================
            // NAVEGACIÓN
            // =====================================================

            when (
                currentScreen
            ) {


                // =================================================
                // SPLASH
                // =================================================

                is Screen.Splash -> {

                    SplashContent(

                        onTimeout = {

                            currentScreen =
                                Screen.Login
                        }
                    )
                }


                // =================================================
                // LOGIN
                // =================================================

                is Screen.Login -> {

                    LoginScreen(

                        isLoading =
                            authState is
                                    AuthState.Loading,

                        errorMessage =
                            (
                                    authState as?
                                            AuthState.Error
                                    )
                                ?.message,


                        onLoginClick = {
                                email,
                                password ->

                            authViewModel
                                .login(

                                    email =
                                        email,

                                    password =
                                        password
                                )
                        },


                        onRegisterClick = {

                            // Registro de cliente
                            // todavía no conectado aquí.
                        },


                        onForgotPasswordClick = {

                            // Recuperación de contraseña
                            // todavía no implementada.
                        },


                        onGoogleClick = {

                            // Login con Google
                            // todavía no conectado.
                        }
                    )
                }


                // =================================================
                // CLIENTE
                // =================================================

                is Screen.Home -> {

                    HomeContent(

                        isLoggedIn =
                            isLoggedIn,


                        onLogout = {

                            isLoggedIn =
                                false

                            currentUser =
                                null

                            currentScreen =
                                Screen.Login
                        },


                        onEditProfile = {

                            // Perfil del cliente.
                        },


                        onCartClick = {

                            // Carrito.
                        },


                        onCalcularEncomienda = {

                            currentScreen =
                                Screen.CalcularEncomienda
                        },


                        onSupportClick = {

                            currentScreen =
                                Screen.Support
                        },


                        onTrackingClick = {

                            currentScreen =
                                Screen.Tracking
                        },


                        onSettingsClick = {

                            currentScreen =
                                Screen.Ajustes
                        },


                        cartSize =
                            0
                    )
                }


                // =================================================
                // SOPORTE
                // =================================================

                is Screen.Support -> {

                    SupportScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }


                // =================================================
                // RASTREO
                // =================================================

                is Screen.Tracking -> {

                    TrackingScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }


                // =================================================
                // AJUSTES
                // =================================================

                is Screen.Ajustes -> {

                    SettingsScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        },


                        isDarkMode =
                            isDarkMode,


                        onDarkModeChange = {

                            isDarkMode =
                                it
                        },


                        fontSizeScale =
                            fontSizeScale,


                        onFontSizeScaleChange = {

                            fontSizeScale =
                                it
                        }
                    )
                }


                // =================================================
                // CALCULADORA
                // =================================================

                is Screen.CalcularEncomienda -> {

                    CalculadoraCostoScreen(

                        onSalirClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }


                // =================================================
                // MOVILIDAD
                // =================================================

                is Screen.Movilidad -> {

                    MovilidadScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }


                // =================================================
                // ADMINISTRADOR
                // =================================================

                is Screen.VistaAdmin -> {

                    AdminDashboardContent(

                        onReportesClick = {

                            currentScreen =
                                Screen.VitsaAdminReporte
                        },


                        onEncomiendasClick = {

                            currentScreen =
                                Screen.VistaAdminEncomiendas
                        },


                        onMovilidadClick = {

                            currentScreen =
                                Screen.VistaAdminMovilidad
                        },


                        onSedesClick = {

                            currentScreen =
                                Screen.VistaAdminSedes
                        },


                        onTarifasClick = {

                            currentScreen =
                                Screen.Tarifas
                        },


                        onRolesClick = {

                            currentScreen =
                                Screen.VistaAdminRoles
                        },


                        onRutasClick = {

                            currentScreen =
                                Screen.VistaAdminRutas
                        },


                        onProfileClick = {

                            // Perfil administrador.
                        },


                        onLogout = {

                            isLoggedIn =
                                false

                            currentUser =
                                null

                            currentScreen =
                                Screen.Login
                        }
                    )
                }


                // =================================================
                // REPORTES ADMIN
                // =================================================

                is Screen.VitsaAdminReporte -> {

                    ReportesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // ENCOMIENDAS ADMIN
                // =================================================

                is Screen.VistaAdminEncomiendas -> {

                    GestionEncomiendasAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // SEDES ADMIN
                // =================================================

                is Screen.VistaAdminSedes -> {

                    GestionSedesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // RUTAS ADMIN
                // =================================================

                is Screen.VistaAdminRutas -> {

                    val rutaViewModel: RutaViewModel =
                        koinViewModel()

                    GestionRutasAdminContent(
                        viewModel = rutaViewModel,
                        onSalirClick = {
                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // TARIFAS ADMIN
                // =================================================

                is Screen.Tarifas -> {

                    GestionTarifasAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // ROLES ADMIN
                // =================================================

                is Screen.VistaAdminRoles -> {

                    GestionRolesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // MOVILIDAD ADMIN
                // =================================================

                is Screen.VistaAdminMovilidad -> {

                    GestionMovilidadAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }


                // =================================================
                // RECEPCIÓN
                // =================================================

                is Screen.Reception -> {

                    ReceptionScreen(

                        onRegisterShipment = {

                            currentScreen =
                                Screen.RegisterShipment
                        },


                        onDeliverWarehouse = {

                            currentScreen =
                                Screen.DeliverWarehouse
                        },


                        onHistory = {

                            // Historial de recepción.
                        },


                        onProfile = {

                            // Perfil de recepción.
                        },


                        onLogout = {

                            isLoggedIn =
                                false

                            currentUser =
                                null

                            currentScreen =
                                Screen.Login
                        },


                        // Temporalmente se conserva
                        // hasta conectar el listado de recepción
                        // con GET /encomienda.
                        shipments =
                            shipments
                    )
                }


                // =================================================
                // ENTREGAR A ALMACÉN
                // =================================================

                is Screen.DeliverWarehouse -> {

                    PendientesEntregaScreen(

                        onBack = {

                            currentScreen =
                                Screen.Reception
                        }
                    )
                }

                // =================================================
                // REGISTRAR ENCOMIENDA
                // =================================================

                is Screen.RegisterShipment -> {

                    RegisterShipmentScreen(

                        // Regresa a Recepción sin registrar.
                        onBack = {

                            currentScreen =
                                Screen.Reception
                        },


                        // =================================================
                        // SEDE DEL RECEPCIONISTA
                        // =================================================

                        // El usuario RECEPCION posee sedeId.
                        //
                        // Esta sede será utilizada como
                        // sede de origen de la encomienda.
                        //
                        // El usuario NO podrá seleccionar
                        // otra sede de origen.

                        recepcionSedeId =
                            currentUser?.sedeId,


                        // =================================================
                        // RESULTADO DEL POST /encomienda
                        // =================================================

                        // Este callback solamente se ejecutará
                        // cuando la encomienda haya sido registrada
                        // correctamente en el backend.

                        onShipmentGenerated = {
                                code,
                                password,
                                sender,
                                recipient,
                                origin,
                                destination,
                                weight,
                                height,
                                width,
                                length,
                                billableWeight,
                                cost,
                                payment ->


                            // =============================================
                            // GUARDAMOS DATOS PARA CONFIRMACIÓN
                            // =============================================

                            // Código real generado por NestJS.
                            trackingCode =
                                code


                            // PIN real generado por NestJS.
                            shipmentPassword =
                                password


                            // DNI remitente real.
                            senderDni =
                                sender


                            // DNI destinatario real.
                            recipientDni =
                                recipient


                            // Sede origen real.
                            originBranch =
                                origin


                            // Sede destino real.
                            destinationBranch =
                                destination


                            // Peso registrado.
                            shipmentWeight =
                                weight


                            // Dimensiones registradas.
                            shipmentHeight =
                                height

                            shipmentWidth =
                                width

                            shipmentLength =
                                length


                            // Actualmente el backend calcula
                            // utilizando el peso real.
                            shipmentBillableWeight =
                                billableWeight


                            // Costo REAL calculado por el backend
                            // según la tarifa configurada.
                            shipmentCost =
                                cost


                            // Modalidad:
                            // Pago en origen o pago en destino.
                            shipmentPaymentMethod =
                                payment


                            // =============================================
                            // NO CREAMOS OTRA ENCOMIENDA LOCAL
                            // =============================================

                            // Antes aquí se hacía:
                            //
                            // val shipment = Shipment(...)
                            //
                            // ShipmentRepository.agregarShipment(shipment)
                            //
                            // Eso ya NO se hace porque la encomienda
                            // acaba de guardarse realmente mediante:
                            //
                            // POST /encomienda
                            //
                            // Si volviéramos a crearla localmente,
                            // tendríamos una encomienda en MySQL
                            // y otra diferente en memoria.


                            // =============================================
                            // IR A LA CONFIRMACIÓN
                            // =============================================

                            currentScreen =
                                Screen.ShipmentConfirmation
                        }
                    )
                }


                // =================================================
                // CONFIRMACIÓN DE ENCOMIENDA
                // =================================================

                is Screen.ShipmentConfirmation -> {

                    ShipmentConfirmationScreen(

                        // Código generado por el backend.
                        trackingCode =
                            trackingCode,


                        // PIN de cuatro dígitos.
                        shipmentPassword =
                            shipmentPassword,


                        // Remitente.
                        senderDni =
                            senderDni,


                        // Destinatario.
                        recipientDni =
                            recipientDni,


                        // Sede donde se registró.
                        originBranch =
                            originBranch,


                        // Sede de destino.
                        destinationBranch =
                            destinationBranch,


                        // Peso.
                        weight =
                            shipmentWeight,


                        // Dimensiones.
                        height =
                            shipmentHeight,

                        width =
                            shipmentWidth,

                        length =
                            shipmentLength,


                        // Peso utilizado para mostrar
                        // la cotización.
                        billableWeight =
                            shipmentBillableWeight,


                        // Costo final confirmado por backend.
                        shippingCost =
                            shipmentCost,


                        // Modalidad de pago.
                        paymentMethod =
                            shipmentPaymentMethod,


                        // Regresa a Recepción.
                        onBack = {

                            currentScreen =
                                Screen.Reception
                        }
                    )
                }


                // =================================================
                // ALMACÉN
                // =================================================

                is Screen.Warehouse -> {

                    val almacenViewModel: AlmacenViewModel = koinInject()

                    WarehouseScreen(
                        viewModel = almacenViewModel,
                        onBackClick = {
                            currentScreen = Screen.Login
                        }
                    )
                }
            }
        }
    }
}