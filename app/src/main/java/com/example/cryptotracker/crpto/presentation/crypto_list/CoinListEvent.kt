package com.example.cryptotracker.crpto.presentation.crypto_list

import com.example.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}
