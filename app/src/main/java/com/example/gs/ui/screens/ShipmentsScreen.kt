package com.example.gs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gs.data.MockLogisticsRepository
import com.example.gs.data.Shipment
import com.example.gs.data.ShipmentStatus

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

    Scaffold(
        topBar = { TopAppBar(title = { Text("Envios ativos") }) }
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
                Button(onClick = { statusFilter = null }) {
                    Text("Todos")
                }
                Button(onClick = { statusFilter = ShipmentStatus.IN_TRANSIT }) {
                    Text("Em transito")
                }
                Button(onClick = { statusFilter = ShipmentStatus.DELAYED }) {
                    Text("Atrasado")
                }
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

@Composable
private fun ShipmentCard(shipment: Shipment, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = shipment.id, style = MaterialTheme.typography.titleMedium)
            Text(text = shipment.destination, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "ETA: ${shipment.etaDays} dias | Status: ${shipment.status.name}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Carga: ${shipment.cargoTons}t | Prioridade ${shipment.priority}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


