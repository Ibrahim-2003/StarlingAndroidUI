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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibrahimstarlingui.ui.theme.IbrahimStarlingUITheme
import com.example.ibrahimstarlingui.ui.theme.StarlingBlue
import com.example.ibrahimstarlingui.ui.theme.StarlingPurp
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IbrahimStarlingUITheme {
                DaysLogger(daily_dots)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DaysLogger(days: List<DayOfWeek>) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .clip(RoundedCornerShape(20.dp))){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()){
            Text(text = "This Month",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 15.dp))
            Circle(3)
            Row(){
                for (i in 1..7){
                    Canvas(modifier = Modifier.size(10.dp), onDraw = {
                        drawCircle(color = Color.Red)
                    })
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = false)
@Composable
fun DaysLoggerPreview() {
    IbrahimStarlingUITheme {
        DaysLogger(daily_dots)
    }
}

@Composable
fun Circle(days: Int){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
            drawArc(color = StarlingBlue,
                startAngle = 130f,
                sweepAngle = 280f,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        })
        Canvas(modifier = Modifier.size(200.dp), onDraw = {
            drawArc(color = StarlingPurp,
                    startAngle = 130f,
                    sweepAngle = 360f*days/(Calendar.getInstance().
                                    getActualMaximum(Calendar.DAY_OF_MONTH)),
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        })
        Text(text = days.toString(),
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp))
        Text(text = "Days Logged",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 80.dp))

        Box(modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.BottomCenter){
            Row(modifier = Modifier.fillMaxWidth()){
                Text(text = "0",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 40.dp),
                    textAlign = TextAlign.Center)
                Text(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH).toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 85.dp),
                    textAlign = TextAlign.Center)
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

var daily_dots = mutableListOf(DayOfWeek("U", false, 0),
                                DayOfWeek("M", false, 1),
                                DayOfWeek("T", false, 2),
                                DayOfWeek("W",false, 3),
                                DayOfWeek("R", false, 4),
                                DayOfWeek("F", false, 5),
                                DayOfWeek("S", false, 6)
)