package com.example.cryptotracker.crpto.presentation.coin_details.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.R
import com.example.cryptotracker.core.presentation.util.Dimens
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    formattedValue: String,
    icon: ImageVector,
    contentColor: Color =
        if (isSystemInDarkTheme()) Color.Green else MaterialTheme.colorScheme.onSurface,
    formattedTextStyle: TextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center, color = contentColor, fontSize = Dimens.fontSizeEighteen
    ),
) {
    Card(
        modifier = modifier
            .padding(Dimens.paddingEight)
            .shadow(
                elevation = Dimens.elevationFourteen,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            ),
        shape = RectangleShape,
        border = BorderStroke(width = 1.dp, color = contentColor),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = contentColor
        )
    ) {
        AnimatedContent(
            targetState = icon,
            label = "IconAnimation",
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) { icon ->
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = modifier
                    .size(75.dp)
                    .padding(top = Dimens.paddingEight),
                tint = contentColor
            )
        }
        Spacer(modifier = modifier.height(Dimens.paddingEight))
        AnimatedContent(
            targetState = formattedValue,
            label = "ValueAnimation",
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) { text ->
            Text(
                text = text,
                style = formattedTextStyle,
                modifier = modifier
                    .padding(horizontal = Dimens.paddingSixteen)
            )
        }
        Spacer(modifier = modifier.height(Dimens.paddingFour))
        AnimatedContent(targetState = title, label = "TitleAnimation",modifier = modifier.align(Alignment.CenterHorizontally)) { text ->
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = Dimens.fontSizeTwelve,
                fontWeight = FontWeight.Light,
                color = contentColor,
                modifier = modifier
                    .padding(horizontal = Dimens.paddingSixteen)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun InfoCardPreview() {
    CryptoTrackerTheme {
        InfoCard(
            title = "Bitcoin", formattedValue = "$ 12,223.45", icon = ImageVector.vectorResource(
                R.drawable.dollar
            )
        )
    }
}
