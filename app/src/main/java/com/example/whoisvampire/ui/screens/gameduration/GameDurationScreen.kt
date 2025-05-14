package com.example.whoisvampire.ui.screens.gameduration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.textstyles.Prociono

@Composable
fun GameDurationScreen(
    navController: NavController
){
    val viewModel: GameDurationVM = hiltViewModel()
    val isLoaded by viewModel.isLoaded.collectAsState()
    if(isLoaded){
        Box() {
            BackGroundGradinet()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BackAppButton(icon = Icons.Default.Clear, title = "1. Gün") {
                        viewModel.clearRoom()
                    }
                }
                Spacer(modifier = Modifier.padding(top = 64.dp))
                Text(
                    text = "Sabah sisin içinden usulca doğarken, köy halkı gözlerini açar. Gecenin karanlığı ardında bir sır bırakmıştır. Kalpler kuşku dolu, bakışlar tetiktedir. Herkes masum görünür… Şimdi gerçekler konuşacak, kader ilk kez şekillenecek. Halk, kaderini belirleyecek seçimin kıyısında.",
                    fontFamily = Prociono,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color.White
                )
                Image(
                    painter = painterResource(R.drawable.day_time),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.padding(top = 32.dp))
                NextButton(buttonText = "Hazır") {
                    navController.navigate(Routes.GAME.name)
                }
            }
        }
    }
}