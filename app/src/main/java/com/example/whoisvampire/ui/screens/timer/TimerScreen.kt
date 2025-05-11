package com.example.whoisvampire.ui.screens.timer

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.ui.routes.Routes
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    navController: NavController
){
    val viewModel: TimerViewModel = hiltViewModel()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            BackAppButton(icon = Icons.Default.Clear) {
                viewModel.clearRoom()
                navController.navigate(Routes.DASHBOARD.name)
            }
        }
        CountdownTimerScreen(navController)
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

            // Süre bittiğinde yönlendirme
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formattedTime, fontSize = 48.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isRunning.value) {
                timeLeftInMillis = 2 * 60 * 1000L // Reset timer
                isRunning.value = true
            }
        }) {
            Text("Start Timer")
        }
        Button(
            onClick = {navController.navigate(Routes.VOTE.name +  "/$id")}
        ) {
            Text(
                "End Vote"
            )
        }
    }
}


