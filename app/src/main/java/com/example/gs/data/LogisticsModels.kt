package com.example.gs.data

enum class ShipmentStatus {
    PLANNED,
    IN_TRANSIT,
    DOCKING,
    DELIVERED,
    DELAYED
}

data class Shipment(
    val id: String,
    val destination: String,
    val etaDays: Int,
    val status: ShipmentStatus,
    val cargoTons: Int,
    val priority: Int
)

object MockLogisticsRepository {
    val shipments: List<Shipment> = listOf(
        Shipment("OR-1042", "Orbital Station A", 2, ShipmentStatus.IN_TRANSIT, 12, 1),
        Shipment("LU-2201", "Lunar Base Beta", 5, ShipmentStatus.PLANNED, 8, 2),
        Shipment("MA-3307", "Mars Relay", 18, ShipmentStatus.DOCKING, 15, 1),
        Shipment("EU-4412", "Europa Lab", 27, ShipmentStatus.DELAYED, 6, 3),
        Shipment("TI-5509", "Titan Outpost", 42, ShipmentStatus.IN_TRANSIT, 9, 2),
        Shipment("OR-6603", "Orbital Station C", 1, ShipmentStatus.DELIVERED, 4, 1)
    )

    fun findById(id: String): Shipment? = shipments.firstOrNull { it.id == id }
}

