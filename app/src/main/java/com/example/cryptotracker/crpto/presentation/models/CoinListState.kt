package com.example.cryptotracker.crpto.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUI> = emptyList(),
    val selectedCoin: CoinUI? = null
)
