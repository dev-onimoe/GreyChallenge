package com.example.greychallenge.mvvm.views.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greychallenge.helpers.AppFont
import com.example.greychallenge.helpers.data_helpers.DataClass
import com.example.greychallenge.helpers.data_helpers.PathLocalDataSourceImpl
import com.example.greychallenge.helpers.data_helpers.fallbackStops
import com.example.greychallenge.mvvm.models.ConnectDirection
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.models.StopState
import com.example.greychallenge.mvvm.view_models.PathViewModel
import com.example.greychallenge.mvvm.view_models.PathViewModelFactory
import com.example.greychallenge.mvvm.views.StopItem
import com.example.greychallenge.ui.theme.GreyChallengeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StageScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var referenceStop by remember { mutableStateOf<PathStop?>(null) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val viewModel: PathViewModel = viewModel(
        factory = remember {

            val storage = PathLocalDataSourceImpl(context)
            val container = DataClass(storage)

            PathViewModelFactory(container)
        }
    )

    val stops by viewModel.stops.collectAsState()
    val currentStop = stops.firstOrNull { it.state == StopState.IN_PROGRESS }

    Column(

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 50.dp)
        ) {
            Icon(
                modifier = Modifier.clickable{
                    onBack()
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Stage ${currentStop?.id?.plus(1)} of ${fallbackStops.size}",
                fontFamily = AppFont,
                fontSize = 12.sp,
                letterSpacing = 0.1.sp,
                color = Color.Gray
            )

            Text(
                text = "Fullstack Mobile Engineer Path",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1E1E1E),
                modifier = Modifier.widthIn(max = 200.dp),
                textAlign = TextAlign.Start,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            itemsIndexed(stops.chunked(2)) { index, stopPair ->
                val stop = stopPair.getOrNull(0)
                val nextStop = stopPair.getOrNull(1)
                val direction = if (index % 2 == 0) ConnectDirection.LEFT else ConnectDirection.RIGHT
                val globalIndexOfNextStop = index * 2 + 1
                val nextNextStop = stops.getOrNull(globalIndexOfNextStop + 1)

                StopItem(
                    onClick = { stop ->
                        referenceStop = stop
                        showSheet = true
                        scope.launch {
                            sheetState.expand()
                        }
                    },
                    isFirst = index == 0,
                    stop = stop!!,
                    nextStop = nextStop,
                    direction = direction,
                    isLast = index == (stops.chunked(2).lastIndex),
                    nextNextStop = nextNextStop
                )
            }
        }
    }

    if (showSheet) {
        StageSheet(
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                    showSheet = false
                }
            },
            onShare = {
                val text = "Hey! I just got a ${referenceStop?.title} success badge on my Fullstack Mobile Engineer Path Course."
                shareText(context, text)
            },
            onFlip = {},
            modifier = Modifier
                .fillMaxWidth(),
            stop = referenceStop,
            sheetState = sheetState
        )
    }
}

fun shareText(context: Context, text: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share via")
    context.startActivity(shareIntent)
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    GreyChallengeTheme {
        StageScreen(
            onBack = {}
        )
    }
}