package com.example.greychallenge.helpers.view_helpers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greychallenge.R

@Composable
fun CircularImageProgress(
    resource: Int,
    progress: Float = 0.2f
) {
    Box(
        modifier = Modifier
            .size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidth = 5.dp.toPx()

            drawArc(
                color = Color(0xFFE1E4EB),
                startAngle = -90f,
                sweepAngle = 360f * 1f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFF2467E3),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Image(
            painter = painterResource(resource),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}