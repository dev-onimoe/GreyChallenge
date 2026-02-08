package com.example.greychallenge.mvvm.views.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greychallenge.AppNav
import com.example.greychallenge.R
import com.example.greychallenge.helpers.AppFont
import com.example.greychallenge.helpers.data_helpers.DataClass
import com.example.greychallenge.helpers.data_helpers.PathLocalDataSourceImpl
import com.example.greychallenge.helpers.data_helpers.fallbackStops
import com.example.greychallenge.helpers.view_helpers.BadgeItem
import com.example.greychallenge.helpers.view_helpers.CircularImageProgress
import com.example.greychallenge.mvvm.models.BadgeState
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.models.StopState
import com.example.greychallenge.mvvm.view_models.PathViewModel
import com.example.greychallenge.mvvm.view_models.PathViewModelFactory
import com.example.greychallenge.ui.theme.GreyChallengeTheme
import java.util.Calendar


@Composable
fun HomeView(
    onCheckProgress: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: PathViewModel = viewModel(
        factory = remember {

            val storage = PathLocalDataSourceImpl(context)
            val container = DataClass(storage)

            PathViewModelFactory(container)
        }
    )

    val stops by viewModel.stops.collectAsState()
    val currentStop = stops.firstOrNull { it.state == StopState.IN_PROGRESS }
    var todayCardCenterY by remember { mutableStateOf<Float?>(null) }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val headerHeight = this.maxHeight * 0.55f

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            todayCardCenterY?.let { centery ->
                Image(
                    painter = painterResource(R.drawable.home_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) { centery.toDp() })
                        .align(Alignment.TopCenter),
                    contentScale = ContentScale.Crop
                )
            }

            HomeScreen(
                onTodayCardPositioned = { centrey ->
                    todayCardCenterY = centrey
                },
                onCheckProgress = onCheckProgress,
                currentStop = currentStop,
                stops = stops
            )
        }
    }
}

@Composable
fun HomeScreen(
    onTodayCardPositioned: (Float) -> Unit,
    onCheckProgress: () -> Unit,
    currentStop: PathStop?,
    stops: List<PathStop>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 55.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            TopBar()
        }

        item {
            GreetingSection()
        }

        item {
            TodayCard(
                onPositioned = onTodayCardPositioned,
                stop = currentStop
            )
        }

        item {
            ActiveLearningPath(
                onCheckProgress = onCheckProgress,
                stop = currentStop
            )
        }

        item {
            BadgesSection(
                stops = stops.filter {
                    it.badgeState == BadgeState.EARNED
                }
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TA",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Box(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, Color(0xFFCFD3FB)),
                    shape = RoundedCornerShape(20.dp)
                )
                .background(Color.Transparent, RoundedCornerShape(20.dp))
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.yellow_fire),
                    contentDescription = "Yellow fire",
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "3 days",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.chat_ic),
            contentDescription = "Contact",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun GreetingSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.mascot),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${getGreeting()} Alex!",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF1E1E1E)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You're closer than you think \uD83D\uDCAA\uD83C\uDFFE",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun TodayCard(
    onPositioned: (Float) -> Unit,
    stop: PathStop?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val positionY = coordinates.positionInRoot().y
                val height = coordinates.size.height
                onPositioned(positionY + height / 2f)
            }
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(all = 16.dp)
    ) {
        Column {
            Text(
                text = "For today",
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CircularImageProgress(
                        resource = R.drawable.black_badge,
                        progress = stop?.progress ?: 0f
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stop?.progressText ?: "",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                tint = Color.LightGray,
                                contentDescription = null
                            )
                            Text(
                                text = stop?.subtitle ?: "",
                                fontFamily = AppFont,
                                fontSize = 12.sp,
                                letterSpacing = 0.1.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun ActiveLearningPath(
    onCheckProgress: () -> Unit,
    stop: PathStop?
) {
    Column {
        Text(
            text = "Active Learning path",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    Text(
                        text = "Fullstack mobile Engineer",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Stage ${stop?.id?.plus(1)} of ${fallbackStops.size}",
                            fontFamily = AppFont,
                            fontSize = 12.sp,
                            letterSpacing = 0.1.sp,
                            color = Color.Gray
                        )

                        LinearProgressIndicator(
                            progress = ((stop?.id?.toFloat() ?: 1f) / fallbackStops.size.toFloat()),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .width(160.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 16.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.blue_badge),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stop?.title ?: "",
                                style = MaterialTheme.typography.labelMedium
                            )

                            Text(
                                text = stop?.subtitle ?: "",
                                fontFamily = AppFont,
                                fontSize = 12.sp,
                                letterSpacing = 0.1.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        onCheckProgress()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "View full path →",
                        fontFamily = AppFont,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun BadgesSection(
    stops: List<PathStop>
) {
    Column {
        Text(
            text = "Badges",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
            items(
                stops
            ) { badge ->
                BadgeItem(badge)
            }
        }
    }
}

fun getGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        in 18..23 -> "Good evening"
        else -> "Hello"
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreyChallengeTheme {
        HomeView {  }
    }
}