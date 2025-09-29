package com.alarm.ui.cryptoScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alarm.net.CryptoApiClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class CryptoViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = CryptoApiClient.create(application)

    private val _uiState = MutableStateFlow(CriptoPriceUIState())
    val uiState: StateFlow<CriptoPriceUIState> = _uiState.asStateFlow()

    private var btcJob: Job? = null

    private suspend fun fetchBitcoinPrice() {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false)

            val response = apiService.getBitcoinPrice()
            val bitcoinData = response["bitcoin"]

            android.util.Log.v("TAGGG", "Precio de bitcoin: $bitcoinData")

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isError = false,
                bitcoinData = bitcoinData
            )

        } catch (e: CancellationException) { // No hacer nada, es una cancelación normal
            throw e // Re-lanzar para propagar la cancelación
        } catch (e: Exception) {
            android.util.Log.e("TAGGG", "Error buscando el precio de bitcoin", e)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = e.message ?: "Error desconocido"
            )
        }
        delay(5000)
    }

    fun fetchForEverBitcoinPrice() {
        stopFetchingBitcoinPrice()
        btcJob = viewModelScope.launch {
            try {
                while (true) {
                    fetchBitcoinPrice()
                }
            } catch (e: CancellationException) {    // Job fue cancelado, salir silenciosamente
                android.util.Log.d("TAGGG", "Fetching cancelled")
            }
        }
    }

    private fun stopFetchingBitcoinPrice() {
        btcJob?.cancel("Fetching stopped by user")
        btcJob = null
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    override fun onCleared() {
        super.onCleared()
        stopFetchingBitcoinPrice()
    }
}