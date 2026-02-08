package com.example.greychallenge.helpers.view_helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greychallenge.R
import com.example.greychallenge.helpers.AppFont
import com.example.greychallenge.mvvm.models.PathStop

@Composable
fun BadgeItem(
    stop: PathStop
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(getDrawableFromId(stop.id)),
            contentDescription = null,
            Modifier.size(50.dp)
        )
        Text(
            text = "Genius",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = "3/3 perfect scores",
            fontFamily = AppFont,
            fontSize = 12.sp,
            letterSpacing = 0.1.sp,
            modifier = Modifier.widthIn(max = 100.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

fun getDrawableFromId(id: Int): Int {
    return when ((id - 1) % 3) {
        0 -> R.drawable.blue_badge
        1 -> R.drawable.red_badge
        else -> R.drawable.purple_badge
    }
}