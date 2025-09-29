package com.alarm.ui.cryptoScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CryptoPriceScreen(
    modifier: Modifier = Modifier,
    viewModel: CryptoViewModel,
    onEnterPip: () -> Unit,
    isInPip: MutableState<Boolean>
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.fetchForEverBitcoinPrice()
    }

    when {

        isInPip.value && uiState.bitcoinData != null -> {
            val bitcoinData = uiState.bitcoinData!!
            BitcoinPriceCard(
                btcPrice = bitcoinData.usd.toString(),
                usd24hChange = bitcoinData.usd24hChange,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        uiState.bitcoinData != null -> {
            val bitcoinData = uiState.bitcoinData!!
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "Precio de Bitcoin",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                BitcoinPriceCard(
                    btcPrice = bitcoinData.usd.toString(),
                    usd24hChange = bitcoinData.usd24hChange,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = onEnterPip,
                ) {
                    Text(text = "PIP")
                }
            }
        }

        uiState.isLoading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cargando...")
            }
        }

        uiState.isError -> {
            Text("Error al cargar los datos")
        }
    }
}

@Composable
fun BitcoinPriceCard(
    btcPrice: String?,
    usd24hChange: Double?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (btcPrice != null) {
                // Precio
                Text(
                    text = btcPrice,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Cambio porcentual
                val changeColor = if (usd24hChange!! >= 0) {
                    Color(0xFF4CAF50) // Verde
                } else {
                    Color(0xFFF44336) // Rojo
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "%.2f".format(usd24hChange) + "%",
                        style = MaterialTheme.typography.titleMedium,
                        color = changeColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


            } else {
                // Estado cuando no hay datos
                Text(
                    text = "No hay datos disponibles",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }
    }
}
