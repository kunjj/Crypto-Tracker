package com.example.cryptotracker.crpto.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.crypto.cryptotracker.R
import com.example.cryptotracker.core.presentation.util.Dimens
import com.example.cryptotracker.crpto.presentation.coin_details.component.InfoCard
import com.example.cryptotracker.crpto.presentation.crypto_list.components.previewCoin
import com.example.cryptotracker.crpto.presentation.models.CoinListState
import com.example.cryptotracker.crpto.presentation.models.toDisplayableNumber
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import com.example.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailsScreen(modifier: Modifier = Modifier, state: CoinListState) {
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    if (state.isLoading) Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
    else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Dimens.paddingSixteen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingTwelve)
        ) {
            Icon(
                modifier = modifier.size(Dimens.iconSizeHundred),
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = coin.name,
                fontSize = Dimens.fontSizeForty,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = contentColor
            )

            Text(
                text = coin.symbol,
                fontSize = Dimens.fontSizeTwenty,
                textAlign = TextAlign.Center,
                color = contentColor
            )

            FlowRow(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedValue = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedValue = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )

                val absoluteChangeFormated =
                    (coin.priceUsd.value * (coin.changeIn24Hrs.value / 100)).toDisplayableNumber()

                val contentColor = if (coin.changeIn24Hrs.value > 0.0) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }

                InfoCard(
                    title = stringResource(R.string.change24hrs),
                    formattedValue = absoluteChangeFormated.formatted,
                    icon = if (coin.changeIn24Hrs.value > 0.0)
                        ImageVector.vectorResource(R.drawable.trending)
                    else
                        ImageVector.vectorResource(R.drawable.trending_down),
                    contentColor = contentColor
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailPreview() {
    CryptoTrackerTheme {
        CoinDetailsScreen(
            state = CoinListState(selectedCoin = previewCoin),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
