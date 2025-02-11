package com.example.cryptotracker.crpto.presentation.models

data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber
)

data class DisplayableNumber(val value: Double, val formatted: String)
