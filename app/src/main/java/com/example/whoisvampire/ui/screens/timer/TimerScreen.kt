package com.example.whoisvampire.ui.screens.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.textstyles.Prociono
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    navController: NavController
){
    val id = 1
    val viewModel: TimerViewModel = hiltViewModel()
    Box{
        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BackAppButton(icon = Icons.Default.Clear, title = "Mahkeme") {
                    viewModel.clearRoom()
                    navController.navigate(Routes.DASHBOARD.name)
                }
            }
            Text(
                "Sessizlik bir anda bozuldu, köy meydanında fısıldanan şüpheler bir araya geldi. Gözler birbirine yöneldi, parmaklar suçlamalarla işaretlendi. Artık sessiz kalmak, bir suç sayılırdı. Tartışma zamanıydı.",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = Prociono
                )
            CountdownTimerScreen(navController)
        }
    }
}

@Composable
fun CountdownTimerScreen(
    navController: NavController
) {
    val id = 1
    var timeLeftInMillis by remember { mutableStateOf(2 * 60 * 1000L) } // 2 dakika
    val isRunning = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isRunning.value) {
        if (isRunning.value) {
            while (timeLeftInMillis > 0) {
                delay(1000L)
                timeLeftInMillis -= 1000L
            }
            isRunning.value = false

            navController.navigate(Routes.VOTE.name)
        }
    }

    val minutes = (timeLeftInMillis / 1000) / 60
    val seconds = (timeLeftInMillis / 1000) % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 32.dp))
        Text(text = formattedTime, fontSize = 64.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isRunning.value) {
                timeLeftInMillis = 2 * 60 * 1000L // Reset timer
                isRunning.value = true
            }
        },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text("Mahkemeyi Başlat", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.padding(32.dp))
        Button(
            onClick = {navController.navigate(Routes.VOTE.name +  "/$id")},
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text(
                "Mahkemeyi Bitir",
                fontSize = 18.sp,
                color = colorResource(R.color.button_color)
            )
        }
    }
}


