package com.example.greychallenge.mvvm.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.greychallenge.helpers.AppFont

@Composable
fun SubtitleText(
    text: String
) {
    Text(
        text = text,
        fontFamily = AppFont,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp,
        color = Color.Gray
    )
}