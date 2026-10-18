package pe.edu.pe.presentation

sealed class Screen {
    object Splash : Screen()

    object Login: Screen()
    object Home: Screen()
    object Support: Screen()

    object Tracking: Screen()
    object CalcularEncomienda: Screen()

    object Ajustes: Screen ()
    
    object Movilidad: Screen()

    object VistaAdmin: Screen()

    object VitsaAdminReporte: Screen()

    object VistaAdminEncomiendas: Screen()

    object VistaAdminSedes: Screen()

    object VistaAdminRutas: Screen()

    object VistaAdminRoles: Screen()

    object VistaAdminMovilidad: Screen()

    object Reception: Screen()

    object RegisterShipment : Screen()

    object ShipmentConfirmation : Screen()

    object DeliverWarehouse : Screen()

    object Warehouse : Screen()

}