package com.alarm.ui.cryptoScreen

import com.alarm.net.data.BitcoinData

data class CriptoPriceUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String ="",
    val bitcoinData: BitcoinData? = null,
)
