package pe.edu.pe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import pe.edu.pe.data.model.Shipment
import pe.edu.pe.data.model.ShipmentStatus
import pe.edu.pe.data.repository.ShipmentRepository

import pe.edu.pe.presentation.AdminDashboardContent
import pe.edu.pe.presentation.CalculadoraCostoScreen
import pe.edu.pe.presentation.GestionEncomiendasAdminContent
import pe.edu.pe.presentation.GestionMovilidadAdminContent
import pe.edu.pe.presentation.GestionRolesAdminContent
import pe.edu.pe.presentation.GestionRutasAdminContent
import pe.edu.pe.presentation.GestionSedesAdminContent
import pe.edu.pe.presentation.HomeContent
import pe.edu.pe.presentation.LoginScreen
import pe.edu.pe.presentation.ReportesAdminContent
import pe.edu.pe.presentation.Screen
import pe.edu.pe.presentation.SplashContent

import pe.edu.pe.presentation.movilidad.MovilidadScreen

import pe.edu.pe.presentation.reception.ReceptionScreen
import pe.edu.pe.presentation.reception.delivery.DeliverWarehouseScreen
import pe.edu.pe.presentation.reception.register.RegisterShipmentScreen
import pe.edu.pe.presentation.reception.register.ShipmentConfirmationScreen

import pe.edu.pe.presentation.settings.SettingsScreen
import pe.edu.pe.presentation.support.SupportScreen
import pe.edu.pe.presentation.theme.MandaYaTheme
import pe.edu.pe.presentation.trackorder.TrackingScreen

import pe.edu.pe.presentation.warehouse.WarehouseScreen


@Composable
fun App() {

    var currentScreen by remember {
        mutableStateOf<Screen>(
            Screen.Splash
        )
    }

    // =========================
    // CONFIGURACIÓN
    // =========================

    var isDarkMode by remember {
        mutableStateOf(false)
    }

    var fontSizeScale by remember {
        mutableStateOf(1.0f)
    }

    // =========================
    // LOGIN
    // =========================

    var isLoggedIn by remember {
        mutableStateOf(false)
    }

    // =========================
    // DATOS DE CONFIRMACIÓN
    // =========================

    var trackingCode by remember {
        mutableStateOf("")
    }

    var shipmentPassword by remember {
        mutableStateOf("")
    }

    var senderDni by remember {
        mutableStateOf("")
    }

    var recipientDni by remember {
        mutableStateOf("")
    }

    var originBranch by remember {
        mutableStateOf("")
    }

    var destinationBranch by remember {
        mutableStateOf("")
    }

    var shipmentWeight by remember {
        mutableStateOf("")
    }

    var shipmentHeight by remember {
        mutableStateOf("")
    }

    var shipmentWidth by remember {
        mutableStateOf("")
    }

    var shipmentLength by remember {
        mutableStateOf("")
    }

    var shipmentBillableWeight by remember {
        mutableStateOf("")
    }

    var shipmentCost by remember {
        mutableStateOf("")
    }

    // =====================================================
    // IMPORTANTE:
    // YA NO CREAMOS OTRA LISTA LOCAL.
    //
    // Todas las pantallas utilizan el mismo repositorio.
    // =====================================================

    val shipments = ShipmentRepository.shipments


    MandaYaTheme(
        darkTheme = isDarkMode,
        fontSizeScale = fontSizeScale
    ) {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            when (currentScreen) {

                // =====================================================
                // SPLASH
                // =====================================================

                is Screen.Splash -> {

                    SplashContent(
                        onTimeout = {
                            currentScreen =
                                Screen.Login
                        }
                    )
                }

                // =====================================================
                // LOGIN
                // =====================================================

                is Screen.Login -> {

                    LoginScreen(

                        onLoginClick = {
                                user,
                                pass ->

                            when {

                                user.lowercase() == "cliente" &&
                                        pass == "cliente" -> {

                                    isLoggedIn = true

                                    currentScreen =
                                        Screen.Home
                                }

                                user.lowercase() == "admin" &&
                                        pass == "admin" -> {

                                    isLoggedIn = true

                                    currentScreen =
                                        Screen.VistaAdmin
                                }

                                user.lowercase() == "movilidad" &&
                                        pass == "movilidad" -> {

                                    isLoggedIn = true

                                    currentScreen =
                                        Screen.Movilidad
                                }

                                user.lowercase() == "recepcion" &&
                                        pass == "recepcion" -> {

                                    isLoggedIn = true

                                    currentScreen =
                                        Screen.Reception
                                }
                                user.lowercase() == "almacen" &&
                                        pass == "almacen" -> {

                                    isLoggedIn = true

                                    currentScreen =
                                        Screen.Warehouse
                                }

                                else -> {

                                    isLoggedIn = false
                                }
                            }
                        },

                        onRegisterClick = {

                        },

                        onForgotPasswordClick = {

                        },

                        onGoogleClick = {

                            isLoggedIn = true

                            currentScreen =
                                Screen.Home
                        }
                    )
                }

                // =====================================================
                // CLIENTE
                // =====================================================

                is Screen.Home -> {

                    HomeContent(

                        isLoggedIn = isLoggedIn,

                        onLogout = {

                            isLoggedIn = false

                            currentScreen =
                                Screen.Login
                        },

                        onEditProfile = {

                        },

                        onCartClick = {

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

                        cartSize = 0
                    )
                }

                // =====================================================
                // SOPORTE
                // =====================================================

                is Screen.Support -> {

                    SupportScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }

                // =====================================================
                // RASTREO
                // =====================================================

                is Screen.Tracking -> {

                    TrackingScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }

                // =====================================================
                // AJUSTES
                // =====================================================

                is Screen.Ajustes -> {

                    SettingsScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        },

                        isDarkMode =
                            isDarkMode,

                        onDarkModeChange = {

                            isDarkMode = it
                        },

                        fontSizeScale =
                            fontSizeScale,

                        onFontSizeScaleChange = {

                            fontSizeScale = it
                        }
                    )
                }

                // =====================================================
                // CALCULADORA
                // =====================================================

                is Screen.CalcularEncomienda -> {

                    CalculadoraCostoScreen(

                        onSalirClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }

                // =====================================================
                // MOVILIDAD
                // =====================================================

                is Screen.Movilidad -> {

                    MovilidadScreen(

                        onBackClick = {

                            currentScreen =
                                Screen.Home
                        }
                    )
                }

                // =====================================================
                // ADMIN
                // =====================================================

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

                        onRolesClick = {

                            currentScreen =
                                Screen.VistaAdminRoles
                        },

                        onRutasClick = {

                            currentScreen =
                                Screen.VistaAdminRutas
                        },

                        onProfileClick = {

                        },

                        onLogout = {

                            isLoggedIn = false

                            currentScreen =
                                Screen.Login
                        }
                    )
                }

                is Screen.VitsaAdminReporte -> {

                    ReportesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                is Screen.VistaAdminEncomiendas -> {

                    GestionEncomiendasAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                is Screen.VistaAdminSedes -> {

                    GestionSedesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                is Screen.VistaAdminRutas -> {

                    GestionRutasAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                is Screen.VistaAdminRoles -> {

                    GestionRolesAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                is Screen.VistaAdminMovilidad -> {

                    GestionMovilidadAdminContent(

                        onSalirClick = {

                            currentScreen =
                                Screen.VistaAdmin
                        }
                    )
                }

                // =====================================================
                // RECEPCIÓN
                // =====================================================

                is Screen.Reception -> {

                    ReceptionScreen(

                        onRegisterShipment = {
                            currentScreen = Screen.RegisterShipment
                        },

                        onDeliverWarehouse = {
                            currentScreen = Screen.DeliverWarehouse
                        },

                        onHistory = {

                        },

                        onProfile = {

                        },

                        onLogout = {
                            isLoggedIn = false
                            currentScreen = Screen.Login
                        },

                        shipments = shipments
                    )
                }

                // =====================================================
                // ENTREGAR A ALMACÉN
                // =====================================================

                is Screen.DeliverWarehouse -> {

                    DeliverWarehouseScreen(

                        shipments = shipments,

                        onShipmentDelivered = {
                                shipment ->

                            /*
                             * La pantalla devuelve
                             * el Shipment actualizado.
                             *
                             * En lugar de actualizar
                             * una lista local,
                             * actualizamos el repositorio.
                             */

                            ShipmentRepository
                                .actualizarShipment(
                                    shipment
                                )
                        },

                        onWarehouse = {

                            currentScreen =
                                Screen.Warehouse
                        },

                        onBack = {

                            currentScreen =
                                Screen.Reception
                        }
                    )
                }

                // =====================================================
                // REGISTRAR ENVÍO
                // =====================================================

                is Screen.RegisterShipment -> {

                    RegisterShipmentScreen(

                        onBack = {

                            currentScreen =
                                Screen.Reception
                        },

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


                            // =========================================
                            // GUARDAMOS DATOS PARA CONFIRMACIÓN
                            // =========================================

                            trackingCode =
                                code

                            shipmentPassword =
                                password

                            senderDni =
                                sender

                            recipientDni =
                                recipient

                            originBranch =
                                origin

                            destinationBranch =
                                destination

                            shipmentWeight =
                                weight

                            shipmentHeight =
                                height

                            shipmentWidth =
                                width

                            shipmentLength =
                                length

                            shipmentBillableWeight =
                                billableWeight

                            shipmentCost =
                                cost


                            // =========================================
                            // CREAMOS LA ENCOMIENDA
                            // =========================================

                            val shipment =
                                Shipment(

                                    trackingCode =
                                        code,

                                    password =
                                        password,

                                    senderDni =
                                        sender,

                                    recipientDni =
                                        recipient,

                                    paymentMethod =
                                        payment,

                                    height =
                                        height
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    width =
                                        width
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    length =
                                        length
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    weight =
                                        weight
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    originBranch =
                                        origin,

                                    destinationBranch =
                                        destination,

                                    billableWeight =
                                        billableWeight
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    cost =
                                        cost
                                            .toDoubleOrNull()
                                            ?: 0.0,

                                    /*
                                     * La encomienda acaba
                                     * de ser registrada.
                                     */
                                    status =
                                        ShipmentStatus.REGISTRADO,

                                    warehouseToken =
                                        null,

                                    vehiclePlate =
                                        null,

                                    driver =
                                        null,

                                    /*
                                     * La ubicación inicial
                                     * es la sede de origen.
                                     */
                                    currentBranch =
                                        origin,

                                    /*
                                     * Ruta inicial.
                                     */
                                    routeName =
                                        "$origin → $destination"
                                )


                            // =========================================
                            // GUARDAR EN REPOSITORIO COMPARTIDO
                            // =========================================

                            ShipmentRepository
                                .agregarShipment(
                                    shipment
                                )


                            // =========================================
                            // IR A CONFIRMACIÓN
                            // =========================================

                            currentScreen =
                                Screen.ShipmentConfirmation
                        }
                    )
                }

                // =====================================================
                // CONFIRMACIÓN
                // =====================================================

                is Screen.ShipmentConfirmation -> {

                    ShipmentConfirmationScreen(

                        trackingCode =
                            trackingCode,

                        shipmentPassword =
                            shipmentPassword,

                        senderDni =
                            senderDni,

                        recipientDni =
                            recipientDni,

                        originBranch =
                            originBranch,

                        destinationBranch =
                            destinationBranch,

                        weight =
                            shipmentWeight,

                        height =
                            shipmentHeight,

                        width =
                            shipmentWidth,

                        length =
                            shipmentLength,

                        billableWeight =
                            shipmentBillableWeight,

                        shippingCost =
                            shipmentCost,

                        onBack = {

                            currentScreen =
                                Screen.Reception
                        }
                    )
                }

                // =====================================================
                // ALMACÉN
                // =====================================================

                is Screen.Warehouse -> {

                    WarehouseScreen(

                        onBackClick  = {
                            currentScreen =
                                Screen.Login
                        }
                    )
                }
            }
        }
    }
}