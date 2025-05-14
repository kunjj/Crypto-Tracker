package com.example.cryptotracker.crpto.presentation.models

import java.text.NumberFormat
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unit: String,
) {
    fun formatted(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractions = when {
                value > 1000 -> 0
                value in 2f..999f -> 2
                else -> 3
            }
            maximumFractionDigits = fractions
            minimumFractionDigits = 0
        }
        return "$unit ${formatter.format(value)}"
    }
}
