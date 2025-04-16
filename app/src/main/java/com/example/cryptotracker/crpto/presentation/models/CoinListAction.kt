package com.example.cryptotracker.crpto.presentation.models

sealed interface CoinListAction {
    data class OnCoinClick(val coinUI: CoinUI) : CoinListAction
    data object OnSwipeRefresh : CoinListAction
}
