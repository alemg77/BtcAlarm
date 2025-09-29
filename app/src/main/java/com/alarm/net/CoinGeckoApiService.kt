package com.alarm.net

import com.alarm.net.data.BitcoinData
import com.alarm.net.data.CryptoPrice
import com.alarm.net.data.MarketChart
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApiService {

    // Precio simple de Bitcoin
    @GET("api/v3/simple/price")
    suspend fun getBitcoinPrice(
        @Query("ids") ids: String = "bitcoin",
        @Query("vs_currencies") vsCurrencies: String = "usd",
        @Query("include_24hr_change") include24hrChange: Boolean = true,
        @Query("include_last_updated_at") includeLastUpdated: Boolean = true
    ): Map<String, BitcoinData>

    // Precio de múltiples criptomonedas
    @GET("api/v3/coins/markets")
    suspend fun getCryptoMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String = "bitcoin,ethereum,cardano",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String = "24h"
    ): List<CryptoPrice>

    // Historial de precios (últimos 7 días)
    @GET("api/v3/coins/bitcoin/market_chart")
    suspend fun getBitcoinMarketChart(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("days") days: String = "7",
        @Query("interval") interval: String = "daily"
    ): MarketChart
}