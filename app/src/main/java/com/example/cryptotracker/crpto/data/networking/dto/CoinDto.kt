package com.example.cryptotracker.crpto.data.networking.dto

import com.example.cryptotracker.crpto.domain.Coin
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val changePercent24Hr: String,
    val explorer: String?,
    val id: String,
    val marketCapUsd: String,
    val maxSupply: String?,
    val name: String,
    val priceUsd: String,
    val rank: String,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String?
)

fun CoinDto.toCoin() = Coin(
    id = this.id,
    rank = this.rank.toInt(),
    name = this.name,
    symbol = this.symbol,
    marketCapUsd = this.marketCapUsd.toDouble(),
    priceUsd = this.priceUsd.toDouble(),
    changeIn24Hrs = this.changePercent24Hr.toDouble()
)
