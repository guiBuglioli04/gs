package com.example.gs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gs.data.MockLogisticsRepository
import com.example.gs.data.ShipmentStatus

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TrackingScreen(
    onBack: () -> Unit,
    onOpenShipments: () -> Unit
) {
    val shipments = MockLogisticsRepository.shipments
    var index by remember { mutableIntStateOf(0) }
    var statusOverride by remember { mutableStateOf<ShipmentStatus?>(null) }
    val current = shipments.getOrNull(index)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Central de rastreio") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (current == null) {
                        Text("Nenhum envio disponivel")
                    } else {
                        val status = statusOverride ?: current.status
                        Text(text = current.id, style = MaterialTheme.typography.titleMedium)
                        Text(text = current.destination)
                        Text(text = "Status: ${status.name}")
                        Text(text = "ETA: ${current.etaDays} dias")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        index = if (index <= 0) shipments.lastIndex else index - 1
                        statusOverride = null
                    }
                ) {
                    Text("Anterior")
                }
                Button(
                    onClick = {
                        index = if (index >= shipments.lastIndex) 0 else index + 1
                        statusOverride = null
                    }
                ) {
                    Text("Proximo")
                }
                Button(
                    onClick = {
                        statusOverride = when (statusOverride ?: current?.status) {
                            ShipmentStatus.PLANNED -> ShipmentStatus.IN_TRANSIT
                            ShipmentStatus.IN_TRANSIT -> ShipmentStatus.DOCKING
                            ShipmentStatus.DOCKING -> ShipmentStatus.DELIVERED
                            ShipmentStatus.DELIVERED -> ShipmentStatus.DELAYED
                            ShipmentStatus.DELAYED -> ShipmentStatus.PLANNED
                            null -> null
                        }
                    },
                    enabled = current != null
                ) {
                    Text("Atualizar status")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onOpenShipments) {
                    Text("Lista de envios")
                }
                Button(onClick = onBack) {
                    Text("Voltar")
                }
            }
        }
    }
}


