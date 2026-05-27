package com.example.gs.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gs.data.MockLogisticsRepository
import com.example.gs.data.Shipment
import com.example.gs.data.ShipmentStatus
import com.example.gs.ui.components.SpaceBackground
import com.example.gs.ui.components.SpaceTopBar
import com.example.gs.ui.components.StatusDot
import com.example.gs.ui.theme.NeonCyan
import com.example.gs.ui.theme.StatusBlue
import com.example.gs.ui.theme.StatusGreen
import com.example.gs.ui.theme.StatusOrange
import com.example.gs.ui.theme.StatusRed

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShipmentsScreen(
    onBack: () -> Unit,
    onShipmentSelected: (String) -> Unit,
    onOpenTracking: () -> Unit
) {
    var statusFilter by remember { mutableStateOf<ShipmentStatus?>(null) }
    val shipments = remember(statusFilter) {
        MockLogisticsRepository.shipments.filter { statusFilter == null || it.status == statusFilter }
    }

    SpaceBackground(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { SpaceTopBar("Envios ativos") }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = statusFilter == null,
                        onClick = { statusFilter = null },
                        label = { Text("Todos") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = statusFilter == ShipmentStatus.IN_TRANSIT,
                        onClick = { statusFilter = ShipmentStatus.IN_TRANSIT },
                        label = { Text("Em transito") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StatusOrange,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = statusFilter == ShipmentStatus.DELAYED,
                        onClick = { statusFilter = ShipmentStatus.DELAYED },
                        label = { Text("Atrasado") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StatusRed,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    items(shipments) { shipment ->
                        ShipmentCard(shipment = shipment, onClick = { onShipmentSelected(shipment.id) })
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = onBack) {
                        Text("Voltar")
                    }
                    Button(onClick = onOpenTracking) {
                        Text("Rastreio")
                    }
                }
            }
        }
    }
}

@Composable
private fun ShipmentCard(shipment: Shipment, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.92f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusDot(
                    color = statusColor(shipment.status),
                    modifier = Modifier.size(10.dp)
                )
                Text(text = shipment.id, style = MaterialTheme.typography.titleMedium)
            }
            Text(text = shipment.destination, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "ETA: ${shipment.etaDays} dias | Status: ${shipment.status.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Carga: ${shipment.cargoTons}t | Prioridade ${shipment.priority}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun statusColor(status: ShipmentStatus): Color = when (status) {
    ShipmentStatus.PLANNED -> StatusBlue
    ShipmentStatus.IN_TRANSIT -> StatusOrange
    ShipmentStatus.DOCKING -> NeonCyan
    ShipmentStatus.DELIVERED -> StatusGreen
    ShipmentStatus.DELAYED -> StatusRed
}


