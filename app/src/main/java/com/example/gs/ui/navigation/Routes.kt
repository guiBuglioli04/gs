package com.example.gs.ui.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Shipments : Routes("shipments")
    data object Tracking : Routes("tracking")
    data object ShipmentDetail : Routes("shipment/{shipmentId}") {
        fun createRoute(shipmentId: String) = "shipment/$shipmentId"
    }
}

