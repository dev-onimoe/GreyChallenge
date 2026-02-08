package com.example.greychallenge.helpers.view_helpers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.greychallenge.mvvm.models.ConnectDirection
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.models.StopState


@Composable
fun VerticalCurvedPath(
    direction: ConnectDirection,
    nextNextStop: PathStop
) {
    Canvas(modifier = Modifier.fillMaxWidth()) {
        val path = createCurve(
            startX = if (direction == ConnectDirection.LEFT) size.width-100f else 100f,
            startY = 110f,
            endY = 500f,
            curveWidth = 160f,
            direction = direction
        )
        drawPath(path, Color.LightGray,
            style = if (nextNextStop.state != StopState.LOCKED) Stroke(
                width = 5f
            ) else Stroke(
                width = 5f,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(10f, 10f),
                    0f
                )
            )
        )
    }
}

fun createCurve(
    startX: Float,
    startY: Float,
    endY: Float,
    curveWidth: Float,
    direction: ConnectDirection
): Path {
    val path = Path()
    val midY = (startY + endY) / 2

    path.moveTo(startX, startY) // Start at top
    path.quadraticBezierTo(
        if (direction == ConnectDirection.LEFT) startX + curveWidth else startX - curveWidth, midY, // Control point shifted left
        startX, endY // End at bottom
    )

    return path
}

@Composable
fun ConnectingPath (
    stop: PathStop,
    nextStop: PathStop?,
    direction: ConnectDirection
) {
    Canvas(modifier = Modifier.size(width = 70.dp, height = 100.dp)) {
        val centerX = size.width / 2
        if (nextStop != null) {
            when (nextStop.state) {
                StopState.LOCKED -> {
                    drawDashedLine(centerX)
                }

                StopState.IN_PROGRESS -> {
                    drawProgressPath(centerX, nextStop.progress, direction)
                }

                StopState.COMPLETED -> {
                    drawSolidPath(centerX)
                }
            }
        }
    }
}


fun DrawScope.drawDashedLine(x: Float) {
    drawLine(
        color = Color.LightGray,
        start = Offset(0f, size.height / 2),
        end = Offset(size.width, size.height/2),
        strokeWidth = 6f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
    )
}

fun DrawScope.drawSolidPath(x: Float) {
    drawLine(
        color = Color.LightGray,
        start = Offset(0f, size.height / 2),
        end = Offset(size.width, size.height/2),
        strokeWidth = 6f,
        cap = StrokeCap.Round
    )
}

fun DrawScope.drawProgressPath(x: Float, progress: Float, direction: ConnectDirection) {
    val lineWidth = size.height
    val progressWidth = lineWidth * progress.coerceIn(0f, 1f)

    drawDashedLine(0f)
    drawLine(
        color = Color.LightGray,
        start = Offset(if (direction == ConnectDirection.LEFT) 0f else size.width - progressWidth, size.height / 2),
        end = Offset(if (direction == ConnectDirection.LEFT) progressWidth else size.width, size.height / 2),
        strokeWidth = 6f,
        cap = StrokeCap.Round
    )
}