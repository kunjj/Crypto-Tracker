package com.example.cryptotracker.crpto.data.networking.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cryptotracker.crpto.domain.CoinPrice
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId

@Serializable
data class CoinPriceDto(
    val priceUsd: String,
    val time: Long,
)


@RequiresApi(Build.VERSION_CODES.O)
fun CoinPriceDto.toCoinPrice(): CoinPrice = CoinPrice(
    price = this.priceUsd,
    dateTime = Instant.ofEpochMilli(this.time).atZone(ZoneId.of("UTC"))
)
