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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ibrahimstarlingui.ui.theme.StarlingBackground
import com.example.ibrahimstarlingui.ui.theme.StarlingGoodRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingLowRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingMedRisk
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun HealthScreen(){

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
//                                navController.navigate(Screen.MainScreen.route)
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
//                                navController.navigate(Screen.HealthScreen.route)
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
    var selectedRange by remember { mutableStateOf<TimeRange?>(TimeRange.LAST_MONTH) }
    val options = listOf("Week", "Month", "6 M")
    MultiChoiceSegmentedButtonRow (modifier = Modifier
        .padding(vertical = 10.dp)) {
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
        TimeRange.LAST_7_DAYS -> Text(
            text = "Apr 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        TimeRange.LAST_MONTH -> Text(
            text = "Apr 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        TimeRange.LAST_6_MONTHS -> Text(
            text = "Feb 19 - Apr 22",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        null -> Text(
            text = "Apr 19 - Apr 22",
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
            .fillMaxSize()
            .padding(bottom = 20.dp)){
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
                modifier = Modifier.padding(bottom = 20.dp),
                range = TimeRange.LAST_7_DAYS.ordinal,
                scores = sampleScores
            )
            TimeRange.LAST_MONTH -> PerformanceChart(
                modifier = Modifier.padding(all = 20.dp),
                range = TimeRange.LAST_MONTH.ordinal,
                scores = sampleScores
            )
            TimeRange.LAST_6_MONTHS -> PerformanceChart(
                modifier = Modifier.padding(all = 20.dp),
                range = TimeRange.LAST_6_MONTHS.ordinal,
                scores = sampleScores
            )
            else -> println("Error with chip selection")
        }

    }
    Spacer(modifier = Modifier.height(20.dp))
}

val sampleWeekData = listOf(0.1f, 0.45f, 0.56f, 0.76f)
val sampleMonthData = listOf(0.2f, 0.3f, 0.45f, 0.8f, 0.3f)
val sampleSixMonthData = listOf(0.76f, 0.54f, 0.53f, 0.3f)

val sampleScores = List(180) { Random.nextFloat() }


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

    Box(contentAlignment = Alignment.BottomStart,
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

fun getFirstOfEachMonthBetween(startDate: LocalDate, currentDate: LocalDate) : List<LocalDate> {
    val firstOfEachMonth = mutableListOf<LocalDate>()
    var current = startDate.plusMonths(1).withDayOfMonth(1)
    // Move to the first of the next month
    while (current.isBefore(currentDate) || current.isEqual(currentDate)) {
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

    return Pair(scores, datesList)
}

@Composable
private fun DrawDemLines(startDate: LocalDate, endDate: LocalDate, datesList: List<LocalDate>,
                         timeRange: Int){
    val deltaTot = ChronoUnit.DAYS.between(startDate, endDate).toFloat()
    when (timeRange){
        TimeRange.LAST_7_DAYS.ordinal -> {
            for (date in datesList){
                val percDist = ChronoUnit.DAYS.between(date, endDate) / deltaTot
                Canvas(modifier = Modifier.fillMaxSize()){
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x = size.width*percDist, y = 0f),
                        end = Offset(x = size.width*percDist, y = size.height - 20.dp.toPx()),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
                LabelAxisTime(text = date.format(getDateFormatter(timeRange)), percDist)
            }
        }
        TimeRange.LAST_MONTH.ordinal -> {
            Canvas(modifier = Modifier.fillMaxSize()){
                drawLine(
                        color = Color.Gray,
                        start = Offset(x = 20.dp.toPx(), y = 0f),
                        end = Offset(x = 20.dp.toPx(), y = size.height - 20.dp.toPx()),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = datesList.first().format(getDateFormatter(timeRange)), 0f)

            Canvas(modifier = Modifier.fillMaxSize()){
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = 280.dp.toPx(), y = 0f),
                    end = Offset(x = 280.dp.toPx(), y = size.height - 20.dp.toPx()),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            LabelAxisTime(text = datesList.last().format(getDateFormatter(timeRange)), 0.85f)
        }
        TimeRange.LAST_6_MONTHS.ordinal -> {

        }
    }
}

private fun getLastMonthLabels(dateList: List<LocalDate>) : Pair<LocalDate, LocalDate>{
    val firstsList = getFirstOfEachMonthBetween(dateList.first(), dateList.last())
    return Pair(firstsList.first(), firstsList.last())
}

@Composable
private fun DrawCirclePoints(scores: List<Float?>, datesList: List<LocalDate>, modifier: Modifier,
                             radius: Dp, borderWidth: Dp){

    val deltaTot: Float = ChronoUnit.DAYS.between(datesList.first(), datesList.last()).toFloat()
    val nonNullScores = scores.filterNotNull()
    val minScore = nonNullScores.minOrNull() ?: 0f
    val maxScore = nonNullScores.maxOrNull() ?: 1f

    Canvas(modifier = modifier.fillMaxSize()) {
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
                val fillColor: Color = if (score < 0.47f){
                    StarlingGoodRisk
                } else if (score < 0.765f){
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
    val nonNullScores = scores.filterNotNull()
    val minScore = nonNullScores.minOrNull() ?: 0f
    val maxScore = nonNullScores.maxOrNull() ?: 1f

    Canvas(modifier = modifier.fillMaxSize()) {
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
            println(dataPair)
        }
        TimeRange.LAST_MONTH.ordinal -> {
            dataPair = getMonthData(scores)
            println(dataPair)
        }
        TimeRange.LAST_6_MONTHS.ordinal -> {
            dataPair = getSixMonthData(scores)
            println(dataPair)
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



//@Composable
//fun PerformanceChart(modifier: Modifier = Modifier, list: List<Float> = listOf(10f, 20f, 3f, 1f)) {
//    val zipList: List<Pair<Float, Float>> = list.zipWithNext()
//
//    Row(modifier = modifier) {
//        val max = list.max()
//        val min = list.min()
//
//        // Cutoff Values
//        // val cutoffLevelOne = 0.47f
//        // val cutoffLevelTwo = 0.765f
//
//        val lineColor = Color.Black
//
//        for (pair in zipList) {
//
//            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
//            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)
//
//            Canvas(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .weight(1f),
//                onDraw = {
//                    val fromPoint = Offset(x = 0f, y = size.height.times(1 -
//                            fromValuePercentage))
//                    val toPoint =
//                        Offset(x = size.width, y = size.height.times(1 - toValuePercentage))
//
//                    drawLine(
//                        color = lineColor,
//                        start = fromPoint,
//                        end = toPoint,
//                        strokeWidth = 5f
//                    )
//
//                    // Draw the filled circle inside
//                    drawCircle(
//                        color = Color.White,
//                        center = Offset(x = 0f,
//                            y = size.height.times(1 - fromValuePercentage)),
//                        radius = 10f
//                    )
//
//                    drawCircle(
//                        color = Color.White,
//                        center = Offset(x = size.width,
//                            y = size.height.times(1 - toValuePercentage)),
//                        radius = 10f
//                    )
//
//                    // Draw the border circle
//                    drawCircle(
//                        radius = 10f,
//                        style = Stroke(width = 2.dp.toPx()),
//                        color = Color.Black,
//                        center = Offset(x = 0f,
//                            y = size.height.times(1 - fromValuePercentage))
//                    )
//
//                    // Draw the border circle
//                    drawCircle(
//                        radius = 10f,
//                        style = Stroke(width = 2.dp.toPx()),
//                        color = Color.Black,
//                        center = Offset(x = size.width,
//                            y = size.height.times(1 - toValuePercentage))
//                    )
//                })
//        }
//    }
//}

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