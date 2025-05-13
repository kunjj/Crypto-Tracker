package com.example.cryptotracker.crpto.data.networking


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cryptotracker.BuildConfig
import com.example.cryptotracker.core.data.safeCall
import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.core.domain.util.map
import com.example.cryptotracker.crpto.data.networking.dto.CoinHistoryDto
import com.example.cryptotracker.crpto.data.networking.dto.CoinResponseDto
import com.example.cryptotracker.crpto.data.networking.dto.toCoin
import com.example.cryptotracker.crpto.data.networking.dto.toCoinPrice
import com.example.cryptotracker.crpto.domain.Coin
import com.example.cryptotracker.crpto.domain.CoinDataSource
import com.example.cryptotracker.crpto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(urlString = BuildConfig.BASE_URL + "assets"){
                header("Authorization", "Bearer ${BuildConfig.API_KEY}")
            }
        }.map { response -> response.data.map { it.toCoin() } }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(urlString = BuildConfig.BASE_URL + "assets/${coinId}/history") {
                header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response -> response.data.map { it.toCoinPrice() } }
    }
}
