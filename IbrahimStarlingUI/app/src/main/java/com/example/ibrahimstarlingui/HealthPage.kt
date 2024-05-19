package com.example.ibrahimstarlingui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ibrahimstarlingui.ui.theme.StarlingBackground
import com.example.ibrahimstarlingui.ui.theme.StarlingGoodRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingLowRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingMedRisk
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = true)
fun HealthScreen(navController: NavController){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()

    val viewModel = viewModel<MainViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.loadStuff()
    }){
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = "Health Data",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }, scrollBehavior = scrollBehavior
                )

            },
            bottomBar = {
                BottomAppBar(containerColor = Color.White) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.MainScreen.route)
                            }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Homepage",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Home",
                                color = Color.Gray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Symptoms",
                                tint = Color.Gray
                            )
                            Text(
                                "Symptoms",
                                color = Color.Gray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.HealthScreen.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Health"
                            )
                            Text(
                                text = "Health"
                            )
                        }
                    }
                }
            }
        ){
                values ->
            Column(modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(color = StarlingBackground)
                .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally) {

                ScoreCard(riskLevel = null)
                GraphCard()
                InsightsCard()

            }
        }
    }
}

@Composable
fun GraphCard(){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)
        .clip(RoundedCornerShape(50f))
        .background(color = Color.White)){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
            Text(
                text = "UrinDx Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            TestChips()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestChips(){
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Week", "Month", "6 M")
    MultiChoiceSegmentedButtonRow (modifier = Modifier
        .padding(vertical = 10.dp)) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index, count = options.size),
                onCheckedChange = {
                    selectedIndex = index
                },
                checked = selectedIndex == index
            ) {
                Text(
                    text = label
                )
            }
        }
    }

    when (selectedIndex){
        0 -> Text(
            text = "Apr 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        1 -> Text(
            text = "Apr 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        2 -> Text(
            text = "Feb 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
    }

    Box(modifier = Modifier
        .size(300.dp),
        contentAlignment = Alignment.Center
    ){
        /* Cutoffs for Good (green), Low (yellow), and
        High (red) Risk are:
        0 <= Good <= 0.47
        0.47 < Low <= 0.735
        High > 0.735
         */
        Canvas(modifier = Modifier
            .fillMaxSize()){
            drawRect(
                color = StarlingGoodRisk,
                topLeft = Offset(x = 0f, y = size.height*0.53f),
                size = Size(width = size.width,
                    height = size.height * 0.47f)
            )
            drawRect(
                color = StarlingLowRisk,
                topLeft = Offset(x = 0f, y = size.height*0.265f),
                size = Size(width = size.width,
                    height = size.height*0.265f)
            )
            drawRect(
                color = StarlingMedRisk,
                topLeft = Offset(x = 0f, y = 0f),
                size = Size(width = size.width,
                    height = size.height*0.265f)
            )
            drawLine(
                color = Color.LightGray,
                start = Offset(x = 0f, y = size.height*0.265f),
                end = Offset(x = size.width, y = size.height*0.265f),
                strokeWidth = 4f
            )
            drawLine(
                color = Color.LightGray,
                start = Offset(x = 0f, y = size.height*0.53f),
                end = Offset(x = size.width, y = size.height*0.53f),
                strokeWidth = 4f
            )
        }
        when (selectedIndex){
            0 -> PerformanceChart(
                modifier = Modifier
                .padding(all = 20.dp), sampleWeekData)
            1 -> PerformanceChart(
                modifier = Modifier
                .padding(all = 20.dp), sampleMonthData)
            2 -> PerformanceChart(
                modifier = Modifier
                .padding(all = 20.dp), sampleSixMonthData)
            else -> println("Error with chip selection")
        }

    }
    Row(modifier = Modifier.fillMaxWidth()){
        when (selectedIndex){
            0 -> Text(
                text = "Week",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 40.dp)
            )
            1 -> Text(
                text = "Apr 19",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 40.dp)
            )
            2 -> Text(
                text = "Feb 19",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 40.dp)
            )
            else -> Text(
                text = "Error - Please Refresh",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
                    .padding(start = 40.dp)
            )
        }

    }
}

val sampleWeekData = listOf(0.1f, 0.45f, 0.56f, 0.76f)
val sampleMonthData = listOf(0.2f, 0.3f, 0.45f, 0.8f, 0.3f)
val sampleSixMonthData = listOf(0.76f, 0.54f, 0.53f, 0.3f)

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)

@Composable
fun PerformanceChart(modifier: Modifier = Modifier, list: List<Float> = listOf(10f, 20f, 3f, 1f)) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()

    Row(modifier = modifier) {
        val max = list.max()
        val min = list.min()

        // Cutoff Values
        // val cutoffLevelOne = 0.47f
        // val cutoffLevelTwo = 0.765f

        val lineColor = Color.Black

        for (pair in zipList) {

            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val fromPoint = Offset(x = 0f, y = size.height.times(1 -
                            fromValuePercentage))
                    val toPoint =
                        Offset(x = size.width, y = size.height.times(1 - toValuePercentage))

                    drawLine(
                        color = lineColor,
                        start = fromPoint,
                        end = toPoint,
                        strokeWidth = 5f
                    )

                    // Draw the filled circle inside
                    drawCircle(
                        color = Color.White,
                        center = Offset(x = 0f,
                            y = size.height.times(1 - fromValuePercentage)),
                        radius = 10f
                    )

                    drawCircle(
                        color = Color.White,
                        center = Offset(x = size.width,
                            y = size.height.times(1 - toValuePercentage)),
                        radius = 10f
                    )

                    // Draw the border circle
                    drawCircle(
                        radius = 10f,
                        style = Stroke(width = 2.dp.toPx()),
                        color = Color.Black,
                        center = Offset(x = 0f,
                            y = size.height.times(1 - fromValuePercentage))
                    )

                    // Draw the border circle
                    drawCircle(
                        radius = 10f,
                        style = Stroke(width = 2.dp.toPx()),
                        color = Color.Black,
                        center = Offset(x = size.width,
                            y = size.height.times(1 - toValuePercentage))
                    )
                })
        }
    }
}

@Composable
fun InsightsCard(){
    val insightsText = "Use UrinDx to get today's UrinDx Score!" +
            " Keep using UrinDx to keep your score accurate and personalized to you."
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 15.dp)
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(50f))
            .background(color = Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)){
            Text(
                text = "Insights",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = insightsText,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}