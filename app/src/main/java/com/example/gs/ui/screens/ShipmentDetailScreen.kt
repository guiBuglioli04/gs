package com.example.gs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gs.data.MockLogisticsRepository
import com.example.gs.data.ShipmentStatus

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShipmentDetailScreen(
    shipmentId: String,
    onBack: () -> Unit
) {
    val shipment = MockLogisticsRepository.findById(shipmentId)
    var localStatus by remember(shipmentId) { mutableStateOf(shipment?.status) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalhes do envio") }) }
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
                    Text(text = shipment?.id ?: "Envio nao encontrado")
                    if (shipment != null) {
                        Text(text = shipment.destination, style = MaterialTheme.typography.titleMedium)
                        Text(text = "ETA: ${shipment.etaDays} dias")
                        Text(text = "Carga: ${shipment.cargoTons}t")
                        Text(text = "Prioridade: ${shipment.priority}")
                        Text(text = "Status atual: ${localStatus?.name}")
                    }
                }
            }

            if (shipment != null) {
                Button(onClick = { localStatus = ShipmentStatus.DELIVERED }) {
                    Text("Marcar como entregue")
                }
            }

            Button(onClick = onBack) {
                Text("Voltar")
            }
        }
    }
}


