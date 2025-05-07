package com.example.cryptotracker.crpto.data.networking.dto

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
