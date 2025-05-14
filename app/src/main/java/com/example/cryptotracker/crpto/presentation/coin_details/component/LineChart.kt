package com.example.cryptotracker.crpto.presentation.coin_details.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.crpto.domain.CoinPrice
import com.example.cryptotracker.crpto.presentation.models.ChartStyle
import com.example.cryptotracker.crpto.presentation.models.DataPoint
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    chartStyle: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoints: DataPoint? = null,
    onSelectDatePoint: (DataPoint) -> Unit = {},
    onXLabelWidthChanges: (Float) -> Unit = {},
) {
    val textStyle = LocalTextStyle.current.copy(fontSize = chartStyle.labelFontSize)
    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)
    }

    val maxYvalue = remember(visibleDataPoints) { visibleDataPoints.maxOfOrNull { it.y } ?: 0f }
    val minYvalue = remember(visibleDataPoints) { visibleDataPoints.minByOrNull { it.y } ?: 0f }

    val measurer = rememberTextMeasurer()

    val xLabelWidth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(xLabelWidth) { onXLabelWidthChanges(xLabelWidth) }

    val selectedDataPointsIndexes =
        remember(selectedDataPoints) { dataPoints.indexOf(selectedDataPoints) }

    val isShowingDataPoints by remember(selectedDataPoints) { mutableStateOf(selectedDataPoints != null) }

    Canvas(modifier = modifier.fillMaxSize()) {
        val minYLabelSpacing = chartStyle.minYLabelSpacing.roundToPx()
        val minXLabelSpacing = chartStyle.minXLabelSpacing.roundToPx()
        val verticalPadding = chartStyle.verticalPadding.roundToPx()
        val horizontalPadding = chartStyle.horizontalPadding.roundToPx()


        val xLabelTextLayoutResult = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }

        val maxXLabelWidth = xLabelTextLayoutResult.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResult.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResult.maxOfOrNull { it.lineCount } ?: 0

        val xLabelLineHeight = maxXLabelHeight / maxXLabelLineCount

        val viewPortHeightPx =
            size.height - (2 * verticalPadding + maxXLabelHeight + xLabelLineHeight + minXLabelSpacing)

        val viewPortTopY = verticalPadding + xLabelLineHeight + 10f
        val viewPortRight = size.width - horizontalPadding
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeft = 2f * horizontalPadding

        val graph = Rect(
            top = viewPortTopY,
            bottom = viewPortBottomY,
            right = viewPortRight,
            left = viewPortLeft
        )

        drawRect(color = Color.Green, topLeft = graph.topLeft, size = graph.size)

        xLabelTextLayoutResult.forEachIndexed { index, textLayoutResult ->
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x = viewPortLeft  + (3.5f * minXLabelSpacing * index), y = viewPortBottomY + minXLabelSpacing)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
private fun LineChartPreview() {
    CryptoTrackerTheme {
        val coinHistory = remember {
            (1..20).map {
                CoinPrice(
                    price = (Random.nextFloat() * 1000f).toString(),
                    dateTime = ZonedDateTime.now().plusHours(it.toLong())
                )
            }
        }
        val chartStyle = ChartStyle(
            chartLineColor = Color.Green,
            selectedColor = Color(0xFF7C7C7C),
            unselectedColor = Color.LightGray,
            helperLineThickness = 5f,
            axisLinesThickness = 5f,
            labelFontSize = 14.sp,
            minXLabelSpacing = 8.dp,
            minYLabelSpacing = 8.dp,
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
        )

        val dataPoints = remember {
            coinHistory.map {
                DataPoint(
                    x = it.dateTime.hour.toFloat(),
                    y = it.price,
                    xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(it.dateTime)
                )
            }
        }

        LineChart(
            dataPoints = dataPoints,
            chartStyle = chartStyle, visibleDataPointsIndices = 1..19, unit = "$",
            modifier = Modifier
                .width(700.dp)
                .height(300.dp)
                .background(Color.White),
            selectedDataPoints = dataPoints[1]
        )
    }
}
