package com.alarm.net.data

import com.google.gson.annotations.SerializedName

// Precio actual de Bitcoin
data class BitcoinPrice(
    @SerializedName("bitcoin") val bitcoin: BitcoinData
)

data class BitcoinData(
    @SerializedName("usd") val usd: Double,
    @SerializedName("usd_24h_change") val usd24hChange: Double,
    @SerializedName("last_updated_at") val lastUpdatedAt: Long
)

// Para el historial de precios
data class MarketChart(
    @SerializedName("prices") val prices: List<List<Double>>
)

// Para múltiples criptomonedas
data class CryptoPrice(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("price_change_percentage_24h") val priceChange24h: Double,
    @SerializedName("last_updated") val lastUpdated: String
)
