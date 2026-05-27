package com.example.gs.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.gs.data.ShipmentStatus
import com.example.gs.ui.components.SpaceBackground
import com.example.gs.ui.components.SpaceTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShipmentDetailScreen(
    shipmentId: String,
    onBack: () -> Unit
) {
    val shipment = MockLogisticsRepository.findById(shipmentId)
    var localStatus by remember(shipmentId) { mutableStateOf(shipment?.status) }

    SpaceBackground(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { SpaceTopBar("Detalhes do envio") }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.92f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = shipment?.id ?: "Envio nao encontrado")
                        if (shipment != null) {
                            Text(text = shipment.destination, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "ETA: ${shipment.etaDays} dias",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Carga: ${shipment.cargoTons}t",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Prioridade: ${shipment.priority}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Status atual: ${localStatus?.name}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
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
}


