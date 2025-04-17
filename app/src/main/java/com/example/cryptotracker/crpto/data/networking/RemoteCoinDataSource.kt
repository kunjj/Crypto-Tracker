package com.example.cryptotracker.crpto.data.networking

import com.example.cryptotracker.BuildConfig
import com.example.cryptotracker.core.data.safeCall
import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.core.domain.util.map
import com.example.cryptotracker.crpto.data.networking.dto.CoinResponseDto
import com.example.cryptotracker.crpto.data.networking.mappers.toCoin
import com.example.cryptotracker.crpto.domain.Coin
import com.example.cryptotracker.crpto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header

class RemoteCoinDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(urlString = BuildConfig.BASE_URL + "assets"){
                header("Authorization", "Bearer ${BuildConfig.API_KEY}")
            }
        }.map { response -> response.data.map { it.toCoin() } }
    }
}
