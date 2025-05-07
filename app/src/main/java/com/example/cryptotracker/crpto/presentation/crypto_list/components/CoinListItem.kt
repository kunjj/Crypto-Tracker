package com.example.cryptotracker.crpto.presentation.crypto_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cryptotracker.core.presentation.util.Dimens
import com.example.cryptotracker.crpto.domain.Coin
import com.example.cryptotracker.crpto.presentation.models.CoinUI
import com.example.cryptotracker.crpto.presentation.models.toCoinUi
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(coin: CoinUI, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(Dimens.paddingTwelve),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingFifteen)
    ) {
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        val backGroundColor =
            if (isSystemInDarkTheme()) Color.Green.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary
        Icon(
            modifier = modifier.size(Dimens.iconSizeEightyFive),
            imageVector = ImageVector.vectorResource(id = coin.iconRes),
            contentDescription = coin.name,
            tint = backGroundColor
        )
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = coin.symbol,
                fontSize = Dimens.fontSizeTwenty,
                color = textColor, fontWeight = FontWeight.Bold
            )
            Text(
                text = coin.name,
                fontSize = Dimens.fontSizeFourteen,
                color = textColor, fontWeight = FontWeight.Light
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$ ${coin.priceUsd.formatted}",
                fontSize = Dimens.fontSizeSixteen,
                color = textColor, fontWeight = FontWeight.Bold
            )

            PriceChange(displayableNumber = coin.changeIn24Hrs)
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinItemLitPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coin = previewCoin,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 1241273958896.75,
    priceUsd = 62828.15,
    changeIn24Hrs = -0.17657
).toCoinUi()
