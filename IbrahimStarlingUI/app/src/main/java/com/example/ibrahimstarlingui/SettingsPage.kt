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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ibrahimstarlingui.ui.theme.StarlingBackground
import com.example.ibrahimstarlingui.ui.theme.StarlingCheckedTrack
import com.example.ibrahimstarlingui.ui.theme.StarlingPurp
import com.example.ibrahimstarlingui.ui.theme.StarlingSettingBackground
import com.example.ibrahimstarlingui.ui.theme.StarlingUncheckedTrack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = false)
fun ProfileScreen(navController: NavController){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomAppBar(containerColor = Color.White) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Homepage",
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.MainScreen.route)
                            }
                        )
                        Text(
                            text = "Home"
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
                            contentDescription = "Health",
                            tint = Color.Gray
                        )
                        Text(
                            text = "Health",
                            color = Color.Gray
                        )
                    }
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold
                )
            },
                navigationIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Return Home",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    navController.navigate(Screen.MainScreen.route)
                                }
                        )
                        Text(
                            text = "Hey Ibrahim!",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                })
        }) {values ->
        Column(modifier = Modifier
            .padding(values)
            .background(color = StarlingSettingBackground)
            .fillMaxSize()
            .verticalScroll(scrollState)) {


            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(top = 40.dp)
                .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.Center){
                    Canvas(modifier = Modifier.size(250.dp)) {
                        drawCircle(
                            color = Color.Gray,
                            center = center,
                            radius = 250f
                        )
                    }
                    Text(
                        text = "IS",
                        fontSize = 60.sp,
                        color = Color.White
                    )
                }
                Text(
                    text = "Ibrahim Starling",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()) {
                OtaSection()
                Spacer(modifier = Modifier.height(30.dp))
                BtTestingSection()
                Spacer(modifier = Modifier.height(30.dp))
                DetailsSection()
                Spacer(modifier = Modifier.height(30.dp))
                StarlingIDSection()
                Spacer(modifier = Modifier.height(30.dp))
                AvailabilitySection()
                Spacer(modifier = Modifier.height(30.dp))
                LogsSection()
                Spacer(modifier = Modifier.height(30.dp))
                PrivacySection()
                Spacer(modifier = Modifier.height(30.dp))
                VersionSection()
                Spacer(modifier = Modifier.height(30.dp))
                DeleteDataSection()
                Spacer(modifier = Modifier.height(10.dp))

            }

        }
    }
}

@Composable
fun OtaSection(){
    var checked by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "OVER THE AIR UPDATE",
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 5.dp)
                .padding(start = 20.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 10.dp)){
                Text(
                    text = "Toggle Active Mode",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = StarlingPurp,
                        checkedTrackColor = StarlingCheckedTrack,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = StarlingUncheckedTrack,
                    )
                )
            }
        }
    }
}

@Composable
fun BtTestingSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "BLUETOOTH TESTING",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Status: Initializing",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Disconnect",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Write Patient ID",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }

        }

    }
}

@Composable
fun StarlingIDSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "STARLING ID",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "FFFFFFFF-FFFF-FFFF-0000-000000000001",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "DETAILS",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "UrinDx First Time Use",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to First Time",
                        tint = Color.Gray)
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Installation Help Article",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to Installation Help",
                        tint = Color.Gray)
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "UrinDx Installation Video",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to Installation Video",
                        tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun AvailabilitySection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "AVAILABILITY",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Available Times",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to Available Times",
                        tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun LogsSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "LOGS",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Clear File Logs",
                        fontSize = 20.sp,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Icon(imageVector = Icons.Default.Share,
                        contentDescription = "Share Log File",
                        tint = Color.Gray)
                    Text(
                        text = "Share Log File",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PrivacySection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "PRIVACY",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Privacy Policy",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to Privacy Policy",
                        tint = Color.Gray)
                }
                HorizontalDivider(color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Terms of Use",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go to Terms of Use",
                        tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun VersionSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "APP VERSION",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Starling App, D-Version 1.2.5",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteDataSection(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)){
        Text(
            text = "APP VERSION",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(bottom = 5.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .background(StarlingBackground)
            .clip(RoundedCornerShape(40f))) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 12.dp)){
                    Text(
                        text = "Delete My Data",
                        color = Color.Red,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}
