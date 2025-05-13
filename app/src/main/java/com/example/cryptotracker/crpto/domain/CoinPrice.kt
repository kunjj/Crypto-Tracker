package com.example.cryptotracker.crpto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val price: String,
    val dateTime: ZonedDateTime,
)
