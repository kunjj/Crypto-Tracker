package com.example.cryptotracker.crpto.presentation.crypto_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.crpto.presentation.crypto_list.components.CoinListItem
import com.example.cryptotracker.crpto.presentation.crypto_list.components.previewCoin
import com.example.cryptotracker.crpto.presentation.models.CoinListAction
import com.example.cryptotracker.crpto.presentation.models.CoinListState
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    state: CoinListState,
    onCoinClick: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
    else LazyColumn(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.coins) { coin ->
            CoinListItem(coin = coin, onClick = { onCoinClick(CoinListAction.OnCoinClick(coin)) })
            HorizontalDivider()
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(state = CoinListState(coins = (1..100).map {
            previewCoin.copy(id = it.toString())
        }), onCoinClick = {}, modifier = Modifier.background(MaterialTheme.colorScheme.background))
    }
}
