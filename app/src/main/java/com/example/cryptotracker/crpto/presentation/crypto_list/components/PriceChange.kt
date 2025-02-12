package com.example.cryptotracker.crpto.presentation.crypto_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cryptotracker.crpto.presentation.models.DisplayableNumber
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import com.example.cryptotracker.core.presentation.util.Dimens

@Composable
fun PriceChange(displayableNumber: DisplayableNumber, modifier: Modifier = Modifier) {
    val backgroundColor = if (displayableNumber.value < 0) Color.Red else Color.Green
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.roundedCornerHundred))
            .background(backgroundColor)
            .padding(horizontal = Dimens.paddingFour)
    ) {
        Icon(
            imageVector = if (displayableNumber.value < 0) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            modifier = modifier.size(Dimens.iconSizeTwenty),
            contentDescription = null
        )
        Text(text = "${displayableNumber.formatted}%", fontSize = Dimens.fontSizeFourteen)
    }
}

@PreviewLightDark
@Composable
private fun PreviewPriceChange() {
    CryptoTrackerTheme {
        PriceChange(
            modifier = Modifier,
            displayableNumber = DisplayableNumber(value = 2.343, formatted = "2.343")
        )
    }
}
