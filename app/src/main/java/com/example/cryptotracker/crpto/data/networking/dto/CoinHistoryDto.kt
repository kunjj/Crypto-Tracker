package com.example.cryptotracker.crpto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val data: List<CoinPriceDto>,
    val timestamp: Long,
)
