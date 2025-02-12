package com.example.cryptotracker.crpto.presentation.models

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.example.cryptotracker.crpto.domain.Coin
import com.example.cryptotracker.core.presentation.util.getDrawableIdForCoin
import java.util.Locale

data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changeIn24Hrs: DisplayableNumber,
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(val value: Double, val formatted: String)

fun Coin.toCoinUi(): CoinUI = CoinUI(
    id = this.id,
    name = this.name,
    rank = this.rank,
    symbol = this.symbol,
    marketCapUsd = this.marketCapUsd.toDisplayableNumber(),
    priceUsd = this.priceUsd.toDisplayableNumber(),
    changeIn24Hrs = this.changeIn24Hrs.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(this.symbol)
)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatted = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(value = this, formatted = formatted.format(this))
}
