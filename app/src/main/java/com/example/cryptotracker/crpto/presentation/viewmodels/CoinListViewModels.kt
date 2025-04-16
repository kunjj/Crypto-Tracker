package com.example.cryptotracker.crpto.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.domain.util.onError
import com.example.cryptotracker.core.domain.util.onSuccess
import com.example.cryptotracker.crpto.domain.CoinDataSource
import com.example.cryptotracker.crpto.presentation.models.CoinListAction
import com.example.cryptotracker.crpto.presentation.models.CoinListState
import com.example.cryptotracker.crpto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModels(private val coinDataSource: CoinDataSource) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())

    val state: StateFlow<CoinListState>
        get() = _state.onStart { getCoins() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CoinListState())

    private fun getCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update {
                    it.copy(isLoading = false, coins = coins.map { coin -> coin.toCoinUi() })
                }
            }
                .onError { _state.update { it.copy(isLoading = false) } }
        }
    }

    fun onAction(action: CoinListAction){
        when(action){
            is CoinListAction.OnCoinClick -> TODO()
            CoinListAction.OnSwipeRefresh -> getCoins()
        }
    }
}
