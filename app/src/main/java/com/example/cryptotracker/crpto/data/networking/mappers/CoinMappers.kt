package com.example.cryptotracker.crpto.data.networking.mappers

import com.example.cryptotracker.crpto.data.networking.dto.CoinDto
import com.example.cryptotracker.crpto.domain.Coin

fun CoinDto.toCoin() = Coin(
    id = this.id,
    rank = this.rank.toInt(),
    name = this.name,
    symbol = this.symbol,
    marketCapUsd = this.marketCapUsd.toDouble(),
    priceUsd = this.priceUsd.toDouble(),
    changeIn24Hrs = this.changePercent24Hr.toDouble()
)
