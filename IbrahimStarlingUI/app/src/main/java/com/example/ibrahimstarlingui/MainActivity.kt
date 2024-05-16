package com.example.ibrahimstarlingui

import android.graphics.fonts.FontStyle
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibrahimstarlingui.ui.theme.IbrahimStarlingUITheme
import com.example.ibrahimstarlingui.ui.theme.StarlingBackground
import com.example.ibrahimstarlingui.ui.theme.StarlingBlue
import com.example.ibrahimstarlingui.ui.theme.StarlingGoodRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingLowRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingMedRisk
import com.example.ibrahimstarlingui.ui.theme.StarlingPurp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IbrahimStarlingUITheme {
                Homepage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview(showBackground = true)
fun Homepage() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Hey Wassim!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        tint = StarlingPurp,
                        contentDescription = "Profile",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }, scrollBehavior = scrollBehavior
            )

        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = StarlingBackground)
                .padding(values)
        ) {
            ScoreCard(riskLevel = null)
            DaysLogger(daysActive = daily_dots)
            AppointmentCard()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreCard(riskLevel: String?){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(50f))
            .background(color = Color.White)
    ) {
        Row(modifier = Modifier
            .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(){
                if (riskLevel == null) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(text = "No Score Today",
                            fontSize = 20.sp)
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(5.dp))
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Score: ${riskLevel.lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                java.util.Locale.ROOT
                            ) else it.toString()
                        }} Risk",
                            fontSize = 20.sp)
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(5.dp))
                    }
                }
                Text(text =
                "Last updated: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, HH:mm"))}",
                    fontSize = 15.sp,
                    color = Color.Gray)
            }
            if (riskLevel != null && riskLevel == "GOOD"){
                Box(modifier = Modifier
                    .width(130.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50f))
                    .background(color = StarlingGoodRisk), contentAlignment = Alignment.Center){

                    Text(text = "GOOD",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)

                }
            } else if (riskLevel != null && riskLevel == "LOW") {
                Box(modifier = Modifier
                    .width(130.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50f))
                    .background(color = StarlingLowRisk), contentAlignment = Alignment.Center){

                    Text(text = "LOW RISK",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)

                }
            } else if (riskLevel != null && riskLevel == "MEDIUM") {
                Box(modifier = Modifier
                    .width(130.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50f))
                    .background(color = StarlingMedRisk), contentAlignment = Alignment.Center){

                    Text(text = "MEDIUM RISK",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)

                }
            } else {
                Box(modifier = Modifier
                    .width(130.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50f))
                    .background(color = StarlingBlue), contentAlignment = Alignment.Center){

                    Text(text = "– –",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)

                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCard(){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(50f))
            .background(color = Color.White)
    ) {
        Row(modifier = Modifier
            .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(modifier = Modifier
                .padding(end = 30.dp)){
                Text(text = "Appointments",
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(5.dp))
                Text(text =
                "No Appointments",
                    fontSize = 15.sp,
                    color = Color.Gray)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Request")
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DaysLogger(daysActive: List<DayOfWeek>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "This Month",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 25.dp)
            )
            Circle(3)
            Row(modifier = Modifier.padding(top = 20.dp)) {
                for (day in daysActive) {
                    if (day.dayIndex > getActiveDOW()) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50f))
                                .background(color = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                CircleWithBorder(
                                    radius = 15.dp,
                                    borderWidth = 2.dp,
                                    borderColor = StarlingPurp,
                                    fillColor = Color.White
                                )
                                Text(
                                    text = day.day,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    } else if (day.dayIndex == getActiveDOW()) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50f))
                                .background(color = StarlingBlue)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                CircleWithBorder(
                                    radius = 15.dp,
                                    borderWidth = 2.dp,
                                    borderColor = StarlingPurp,
                                    fillColor = Color.White
                                )
                                Text(
                                    text = day.day,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    } else if (day.isLogged) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50f))
                                .background(color = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Canvas(modifier = Modifier.size(30.dp), onDraw = {
                                    drawCircle(color = StarlingPurp)
                                })
                                Text(
                                    text = day.day,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50f))
                                .background(color = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Canvas(modifier = Modifier.size(30.dp), onDraw = {
                                    drawCircle(color = StarlingBlue)
                                })
                                Text(
                                    text = day.day,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    }
                    if (day.dayIndex < 6) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                }
            }
        }
    }
}

@Composable
fun Circle(days: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 20.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
            drawArc(
                color = StarlingBlue,
                startAngle = 130f,
                sweepAngle = 280f,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        })
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
            drawArc(
                color = StarlingPurp,
                startAngle = 130f,
                sweepAngle = 360f * days / (Calendar.getInstance()
                    .getActualMaximum(Calendar.DAY_OF_MONTH)),
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        })
        Text(
            text = days.toString(),
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(
            text = "Days Logged",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 80.dp)
        )

        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "0",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 40.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH).toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 85.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun getActiveDOW(): Int {
    val dow = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return when (dow) {
        Calendar.SUNDAY -> 0
        Calendar.MONDAY -> 1
        Calendar.TUESDAY -> 2
        Calendar.WEDNESDAY -> 3
        Calendar.THURSDAY -> 4
        Calendar.FRIDAY -> 5
        Calendar.SATURDAY -> 6
        else -> 0
    }
}

data class DayOfWeek(val day: String, val isLogged: Boolean, val dayIndex: Int)

var daily_dots = mutableListOf(
    DayOfWeek("S", false, 0),
    DayOfWeek("M", true, 1),
    DayOfWeek("T", false, 2),
    DayOfWeek("W", false, 3),
    DayOfWeek("R", false, 4),
    DayOfWeek("F", false, 5),
    DayOfWeek("S", false, 6)
)

@Composable
fun CircleWithBorder(
    modifier: Modifier = Modifier,
    radius: Dp,
    borderWidth: Dp,
    borderColor: Color,
    fillColor: Color
) {
    Canvas(modifier = modifier.size(radius * 2)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radiusFloat = radius.toPx()

        // Draw the border circle
        drawCircle(
            color = borderColor,
            center = center,
            radius = radiusFloat,
            style = Stroke(width = borderWidth.toPx())
        )

        // Draw the filled circle inside
        drawCircle(
            color = fillColor,
            center = center,
            radius = radiusFloat - (borderWidth / 2).toPx() // Adjust radius to fit inside the border
        )
    }
}