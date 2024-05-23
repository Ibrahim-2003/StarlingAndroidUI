/*
Starling Health Page UI
Created by Ibrahim Al-Akash on 2024-05-22.
*/

package com.example.ibrahimstarlingui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.pow
import kotlin.random.Random


val sampleScores = List(300) {
    if ((it*0.2f).pow(2) <= 1f){
        (it*0.2f).pow(2)
    } else {
        Random.nextFloat()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = true)
fun HealthScreen(navController: NavController){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()

    val viewModel = viewModel<MainViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.loadStuff()
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.MainScreen.route)
                                }
                        ) {
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
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
            Column(
                modifier = Modifier
                    .padding(values)
                    .fillMaxSize()
                    .background(color = StarlingBackground)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScoreCard(riskLevel = null)
                GraphCard()
                InsightsCard()

            }
        }
    }
}

@Composable
fun GraphCard(){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(50f))
            .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "UrinDx Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            TestChips()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestChips(){
    var selectedRange by remember { mutableStateOf<TimeRange?>(TimeRange.LAST_7_DAYS) }
    val options = listOf("Week", "Month", "6 M")
    MultiChoiceSegmentedButtonRow (
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index, count = options.size),
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        selectedRange = when (index) {
                            0 -> TimeRange.LAST_7_DAYS
                            1 -> TimeRange.LAST_MONTH
                            2 -> TimeRange.LAST_6_MONTHS
                            else -> TimeRange.LAST_7_DAYS // Default value
                        }
                    }
                },
                checked = selectedRange == when (index) {
                    0 -> TimeRange.LAST_7_DAYS
                    1 -> TimeRange.LAST_MONTH
                    2 -> TimeRange.LAST_6_MONTHS
                    else -> TimeRange.LAST_7_DAYS // Default value
                }
            ) {
                Text(
                    text = label
                )
            }
        }
    }

    when (selectedRange){
        TimeRange.LAST_7_DAYS -> {
            val dataPair = getWeekData(scores = sampleScores)
            val endDate = dataPair.second.first().format(DateTimeFormatter.ofPattern("MMM dd"))
            val startDate = dataPair.second.last().format(DateTimeFormatter.ofPattern("MMM dd"))
            Text(
                text = "$startDate - $endDate",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }
        TimeRange.LAST_MONTH -> {
            val dataPair = getMonthData(scores = sampleScores)
            val endDate = dataPair.second.last().format(DateTimeFormatter.ofPattern("MMM dd"))
            val startDate = dataPair.second.first().format(DateTimeFormatter.ofPattern("MMM dd"))
            Text(
                text = "$startDate - $endDate",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }
        TimeRange.LAST_6_MONTHS -> {
            val dataPair = getSixMonthData(scores = sampleScores)
            val endDate = dataPair.second.last().format(DateTimeFormatter.ofPattern("MMM yyyy"))
            val startDate = dataPair.second.first().format(DateTimeFormatter.ofPattern("MMM yyyy"))
            Text(
                text = "$startDate - $endDate",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }
        null -> Text(
            text = "No data to show",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(300.dp)
    ){
        /* Cutoffs for Good (green), Low (yellow), and
        High (red) Risk are:
        0 <= Good <= 0.47
        0.47 < Low <= 0.735
        High > 0.735
         */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)
        ) {
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

        when (selectedRange){
            TimeRange.LAST_7_DAYS -> PerformanceChart(
                range = TimeRange.LAST_7_DAYS.ordinal,
                scores = sampleScores,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            TimeRange.LAST_MONTH -> PerformanceChart(
                range = TimeRange.LAST_MONTH.ordinal,
                scores = sampleScores,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            TimeRange.LAST_6_MONTHS -> PerformanceChart(
                range = TimeRange.LAST_6_MONTHS.ordinal,
                scores = sampleScores,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            else -> println("Error with chip selection")
        }

    }
    Spacer(modifier = Modifier.height(20.dp))
}

enum class TimeRange {
    LAST_7_DAYS, LAST_MONTH, LAST_6_MONTHS
}

private fun getDateFormatter(timeRange: Int): DateTimeFormatter {
    return when (timeRange) {
        TimeRange.LAST_7_DAYS.ordinal -> DateTimeFormatter.ofPattern("EEE")
        TimeRange.LAST_MONTH.ordinal -> DateTimeFormatter.ofPattern("MMM d")
        TimeRange.LAST_6_MONTHS.ordinal -> DateTimeFormatter.ofPattern("MMM yyyy")
        else -> { DateTimeFormatter.ofPattern("EEE") }
    }
}

private fun getWeekData(scores: List<Float?>): Pair<List<Float?>, List<LocalDate>>{
    val endDate = LocalDate.now()
    val startDate = endDate.minusWeeks(1)
    val datesList = (0..6).map{ startDate.minusDays(it.toLong()) }
    return if (scores.count() > ChronoUnit.DAYS.between(startDate, endDate).toInt()) {
        Pair(scores.subList(0,7), datesList)
    } else {
        Pair(scores, datesList)
    }
}

@Composable
private fun LabelAxisTime(text: String, deltaTimePerc: Float){
    var textWidth by remember { mutableFloatStateOf(0f) }

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                // Capture the width of the Box
                textWidth = layoutCoordinates.size.width.toFloat()
            }
    ) {
        val paddingStart = with(LocalDensity.current) { (textWidth * deltaTimePerc).toDp() }

        Text(
            text = text,
            modifier = Modifier
                .padding(start = paddingStart)
        )
    }
}

private fun getMonthData(scores: List<Float?>): Pair<List<Float?>, List<LocalDate>>{
    val endDate = LocalDate.now()
    val startDate = endDate.minusMonths(1)
    val datesList = (0..ChronoUnit.DAYS.between(startDate, endDate).toInt()).map{ startDate.plusDays(it.toLong()) }
    return if (scores.count() > ChronoUnit.DAYS.between(startDate, endDate).toInt()) {
        Pair(scores.subList(0,endDate.lengthOfMonth()), datesList)
    } else {
        Pair(scores, datesList)
    }
}

fun getFirstOfEachMonthBetween(startDate: LocalDate, endDate: LocalDate) : List<LocalDate> {
    val firstOfEachMonth = mutableListOf<LocalDate>()
    var current = startDate.plusMonths(1).withDayOfMonth(1)
    // Move to the first of the next month
    while (current.isBefore(endDate) || current.isEqual(endDate)) {
        firstOfEachMonth.add(current)
        current = current.plusMonths(1)
    }
    return firstOfEachMonth
}


private fun getSixMonthData(scores: List<Float?>): Pair<List<Float?>, List<LocalDate>> {
    val endDate = LocalDate.now()
    val startDate = endDate.minusMonths(6)
    val datesList = (0..ChronoUnit.DAYS.between(startDate, endDate).toInt()).map { startDate.plusDays(it.toLong()) }

    if (scores.size < ChronoUnit.DAYS.between(startDate, endDate)) {
        val threshold = ChronoUnit.DAYS.between(startDate, endDate.minusMonths(1)).toInt()
        var index = 0

        while (index < threshold) {
            if (scores[index] != null) {
                return trimScores(scores, datesList)
            }
            index++
        }

        return Pair(
            scores.subList(ChronoUnit.DAYS.between(endDate.minusMonths(1), endDate).toInt() - 1, scores.size),
            datesList.subList(ChronoUnit.DAYS.between(endDate.minusMonths(1), endDate).toInt() - 1, datesList.size)
        )
    }

    return if (scores.count() > ChronoUnit.DAYS.between(startDate, endDate).toInt()) {
        Pair(scores.subList(0,180), datesList)
    } else {
        Pair(scores, datesList)
    }
}

@Composable
private fun DrawDemLines(startDate: LocalDate, endDate: LocalDate, datesList: List<LocalDate>,
                         timeRange: Int){
    val deltaTot = ChronoUnit.DAYS.between(startDate, endDate).toFloat()
    when (timeRange){
        TimeRange.LAST_7_DAYS.ordinal -> {
            for (date in datesList){
                val percDist = ChronoUnit.DAYS.between(date, endDate) / deltaTot
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x = size.width*percDist, y = 0f),
                        end = Offset(x = size.width*percDist, y = size.height - 30.dp.toPx()),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
                LabelAxisTime(text = date.format(getDateFormatter(timeRange)), percDist)
            }
        }
        TimeRange.LAST_MONTH.ordinal -> {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = 20.dp.toPx(), y = 0f),
                    end = Offset(x = 20.dp.toPx(), y = size.height - 30.dp.toPx()),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = datesList.first().format(getDateFormatter(timeRange)), 0f)

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = 280.dp.toPx(), y = 0f),
                    end = Offset(x = 280.dp.toPx(), y = size.height - 30.dp.toPx()),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = datesList.last().format(getDateFormatter(timeRange)), 0.75f)
        }
        TimeRange.LAST_6_MONTHS.ordinal -> {
            val endDateLabel = datesList.last().format(DateTimeFormatter.ofPattern("MMM yyyy"))
            val startDateLabel = datesList.first().format(DateTimeFormatter.ofPattern("MMM yyyy"))
            val lastMonthLabels = getLastMonthLabels(datesList)
            val x1: Float = ChronoUnit.DAYS.between(lastMonthLabels.last(), datesList.last()).toFloat() /
                    ChronoUnit.DAYS.between(datesList.first(), datesList.last()).toFloat()
            val x2: Float = ChronoUnit.DAYS.between(lastMonthLabels.first(), datesList.last()).toFloat() /
                    ChronoUnit.DAYS.between(datesList.first(), datesList.last()).toFloat()
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = size.width*x1, y = 0f),
                    end = Offset(x = size.width*x1, y = size.height - 30.dp.toPx()),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = startDateLabel, x1)
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = size.width*x2, y = 0f),
                    end = Offset(x = size.width*x2, y = size.height - 30.dp.toPx()),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = endDateLabel,
                deltaTimePerc = x2 - 0.3f)
        }
    }
}

private fun getLastMonthLabels(dateList: List<LocalDate>) : List<LocalDate>{
    val firstsList = getFirstOfEachMonthBetween(dateList.first(), dateList.last())
    return firstsList
}

@Composable
private fun DrawCirclePoints(scores: List<Float?>, datesList: List<LocalDate>, modifier: Modifier,
                             radius: Dp, borderWidth: Dp){

    val deltaTot: Float = ChronoUnit.DAYS.between(datesList.first(), datesList.last()).toFloat()
    val minScore = 0f
    val maxScore = 1f

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        val radiusFloat = radius.toPx()
        scores.forEachIndexed { index, score ->
            if (score != null) {
                val daysFromStart = ChronoUnit.DAYS.between(datesList.first(), datesList[index]).toFloat()
                val x = (daysFromStart / deltaTot) * size.width
                val y = ((maxScore - score) / (maxScore - minScore)) * size.height

                // Draw the border circle
                drawCircle(
                    color = Color.Black,
                    center = Offset(x, y),
                    radius = radiusFloat,
                    style = Stroke(width = borderWidth.toPx())
                )

                // Draw the filled circle inside
                val fillColor: Color = if (score < 129/280f){
                    StarlingGoodRisk
                } else if (score < 200.5/280f){
                    StarlingLowRisk
                } else {
                    StarlingMedRisk
                }

                drawCircle(
                    color = fillColor,
                    center = Offset(x, y),
                    radius = radiusFloat - (borderWidth / 2).toPx()
                )
            }
        }
    }
}

@Composable
private fun DrawLinesScores(scores: List<Float?>, datesList: List<LocalDate>, modifier: Modifier){

    val deltaTot: Float = ChronoUnit.DAYS.between(datesList.first(), datesList.last()).toFloat()
    val minScore = 0f
    val maxScore = 1f

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        val path = Path()
        var isFirstPoint = true

        scores.forEachIndexed { index, score ->
            if (score != null) {
                val daysFromStart = ChronoUnit.DAYS.between(datesList.first(), datesList[index]).toFloat()
                val x = (daysFromStart / deltaTot) * size.width
                val y = ((maxScore - score) / (maxScore - minScore)) * size.height

                if (isFirstPoint) {
                    path.moveTo(x, y)
                    isFirstPoint = false
                } else {
                    path.lineTo(x, y)
                }
            }
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

fun trimScores(scores: List<Float?>, dates: List<LocalDate>): Pair<List<Float>, List<LocalDate>> {
    for (i in dates.indices) {
        if (scores[i] != null) {
            val trimmedScores = scores.drop(i).mapNotNull { it }
            val trimmedDates = dates.drop(i)
            return Pair(trimmedScores, trimmedDates)
        }
    }
    return Pair(emptyList(), emptyList())
}

@Composable
fun PerformanceChart(modifier: Modifier = Modifier, range: Int, scores: List<Float?>){
    var dataPair: Pair<List<Float?>, List<LocalDate>>? = null
    when (range){
        TimeRange.LAST_7_DAYS.ordinal -> {
            dataPair = getWeekData(scores)
        }
        TimeRange.LAST_MONTH.ordinal -> {
            dataPair = getMonthData(scores)
        }
        TimeRange.LAST_6_MONTHS.ordinal -> {
            dataPair = getSixMonthData(scores)
        }
        else -> {
            Text(
                text = "Error, please refresh and try again"
            )
        }
    }
    if (dataPair != null){
        DrawDemLines(startDate = dataPair.second.first(), endDate = dataPair.second.last(),
            datesList = dataPair.second, timeRange = range)
        DrawLinesScores(scores = dataPair.first, datesList = dataPair.second, modifier)
        DrawCirclePoints(scores = dataPair.first, datesList = dataPair.second, modifier,
            radius = 3.dp, borderWidth = 2.dp)
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
        ) {
            Text(
                text = "Insights",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
            Text(
                text = insightsText,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
        }
    }
}