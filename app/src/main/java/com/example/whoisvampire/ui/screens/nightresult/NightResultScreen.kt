package com.example.whoisvampire.ui.screens.nightresult

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.textstyles.Prociono

@Composable
fun NightResult(
    navController: NavController
){
    val viewModel: NightResultViewModel = hiltViewModel()
    val isListLoaded by viewModel.isListLoaded.collectAsState()
    val playerList by viewModel.playerList.collectAsState()
    val mostBited by viewModel.mostBitedPlayer.collectAsState()
    var vampireCount = 0
    var villagerCount = 0
    playerList.forEach {
        if(it.isAlive){
            if(it.role == "Vampire") vampireCount++
            else villagerCount++
        }
    }
    Box{
        BackGroundGradinet()
        if (isListLoaded) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BackAppButton(
                        icon = Icons.Default.Clear,
                        title = "Gece Sonu"
                    ) {
                        viewModel.clearRoom()
                        navController.navigate(Routes.DASHBOARD.name)
                    }
                }
                Text(
                    "Öldün Çık",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = Prociono,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                    )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                PlayerItem(mostBited)
                NextButton(
                    "Sonraki Gün"
                ) {
                    if (villagerCount == vampireCount) {
                        navController.navigate(Routes.ENDING.name)
                    } else {
                        navController.navigate(Routes.TIMERSCREEN.name)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(
    player: Player
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(player.image),
            contentDescription = "",
            modifier = Modifier.padding(8.dp)
        )
        Text(player.name, fontSize = 18.sp, color = Color.White, fontFamily = Prociono)
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun PlayerItemPreview(

){
    PlayerItem(Player.empty().copy(name = "Umut"))
}