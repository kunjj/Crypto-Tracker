package com.example.cryptotracker.crpto.presentation.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val selectedColor: Color,
    val unselectedColor: Color,
    val helperLineThickness: Float,
    val axisLinesThickness: Float,
    val labelFontSize: TextUnit,
    val minYLabelSpacing: Dp,
    val minXLabelSpacing: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
)
