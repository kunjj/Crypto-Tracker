package com.example.cryptotracker.crpto.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.domain.util.onError
import com.example.cryptotracker.core.domain.util.onSuccess
import com.example.cryptotracker.crpto.domain.CoinDataSource
import com.example.cryptotracker.crpto.presentation.crypto_list.CoinListEvent
import com.example.cryptotracker.crpto.presentation.models.CoinListAction
import com.example.cryptotracker.crpto.presentation.models.CoinListState
import com.example.cryptotracker.crpto.presentation.models.CoinUI
import com.example.cryptotracker.crpto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModels(private val coinDataSource: CoinDataSource) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    private val _event = Channel<CoinListEvent>()
    val event = _event.receiveAsFlow()

    val state = _state.onStart { getCoins() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CoinListState())

//    val state: StateFlow<CoinListState>
//        get() = _state.asStateFlow()
//
//    init {
//        getCoins()
//    }

    private fun getCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update {
                    it.copy(isLoading = false, coins = coins.map { coin -> coin.toCoinUi() })
                }
            }
                .onError { event ->
                    _state.update { it.copy(isLoading = false) }
                    _event.send(CoinListEvent.Error(event))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> onSelectCoin(action.coinUI)
            is CoinListAction.OnSwipeRefresh -> getCoins()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSelectCoin(coinUI: CoinUI) {
        _state.update { coin ->
            coin.copy(selectedCoin = coinUI)
        }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUI.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
            }.onError { error ->
                _event.send(CoinListEvent.Error(error))
            }
        }
    }
}
