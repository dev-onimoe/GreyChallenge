package com.example.greychallenge.mvvm.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.greychallenge.R
import com.example.greychallenge.helpers.view_helpers.CircularImageProgress
import com.example.greychallenge.mvvm.models.ConnectDirection
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.models.StopState
import com.example.greychallenge.helpers.view_helpers.BadgeItem
import com.example.greychallenge.helpers.view_helpers.ConnectingPath
import com.example.greychallenge.helpers.view_helpers.VerticalCurvedPath
import com.example.greychallenge.helpers.view_helpers.createCurve

@Composable
fun StopItem(
    onClick: (PathStop) -> Unit,
    isFirst: Boolean = false,
    stop: PathStop,
    isLast: Boolean,
    direction: ConnectDirection,
    nextStop: PathStop?,
    nextNextStop: PathStop?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (direction == ConnectDirection.LEFT) {
                BadgeItem(stop, Modifier.clickable {
                    if (stop.state == StopState.COMPLETED) {
                        onClick(stop)
                    }
                })
                if (nextStop != null) {
                    ConnectingPath(
                        stop = stop,
                        nextStop = nextStop,
                        direction = direction
                    )
                    BadgeItem(nextStop, Modifier.clickable {
                        if (nextStop.state == StopState.COMPLETED) {
                            onClick(nextStop)
                        }
                    })
                }
            }else {
                if (nextStop != null) {
                    BadgeItem(nextStop, Modifier.clickable {
                        if (nextStop.state == StopState.COMPLETED) {
                            onClick(nextStop)
                        }
                    })
                    ConnectingPath(
                        stop = stop,
                        nextStop = nextStop,
                        direction = direction
                    )
                }
                BadgeItem(stop, Modifier.clickable {
                    if (stop.state == StopState.COMPLETED) {
                        onClick(stop)
                    }
                })
            }
        }
        if (nextNextStop != null) {
            VerticalCurvedPath(direction, nextNextStop)
        }
    }

}

@Composable
fun BadgeItem(
    stop: PathStop,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StopNode(
            stop = stop
        )

        StopText(
            modifier = Modifier
                .padding(top = 12.dp),
            stop = stop
        )
    }
}


@Composable
fun StopNode(
    modifier: Modifier = Modifier,
    stop: PathStop
) {
    Box(
        modifier = modifier
            .size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        when (stop.state) {
            StopState.COMPLETED -> {
                Image(
                    modifier = Modifier.size(70.dp),
                    painter = painterResource(R.drawable.purple_badge),
                    contentDescription = null
                )
            }

            StopState.IN_PROGRESS -> {
                CircularImageProgress(
                    resource = R.drawable.blue_badge,
                    progress = stop.progress
                )
            }

            StopState.LOCKED -> {
                CircularImageProgress(
                    resource = R.drawable.black_badge,
                    progress = 0.0f
                )
            }
        }
    }
}

@Composable
fun StopText(
    modifier: Modifier,
    stop: PathStop
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stop.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.widthIn(max = 150.dp),
            textAlign = TextAlign.Center
        )

        if (stop.state == StopState.IN_PROGRESS) {
            SubtitleText(stop.subtitle)
        }
    }
}