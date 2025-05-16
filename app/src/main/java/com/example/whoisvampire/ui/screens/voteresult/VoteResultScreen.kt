package com.example.whoisvampire.ui.screens.voteresult

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
fun VoteResult(
    navController: NavController
){
    val viewModel: VoteResultVM = hiltViewModel()
    val isListLoaded by viewModel.isListLoaded.collectAsState()

    if (isListLoaded) {
        val player by viewModel.player.collectAsState()
        val playerList by viewModel.playerList.collectAsState()

        val vampireCount = playerList.count { it.isAlive && it.role == "Vampir" }
        val villagerCount = playerList.count { it.isAlive && it.role != "Vampir" }

        Box {
            BackGroundGradinet()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackAppButton(
                        icon = Icons.Default.Clear,
                        title = "Oylama Sonucu"
                    ) {
                        navController.navigate(Routes.DASHBOARD.name)
                    }
                }
                Text("Öldün Çık", fontSize = 20.sp, color = Color.White)
                PlayerInfo(player)
                NextButton("Sonraki Gün") {
                    if (vampireCount >= villagerCount || vampireCount == 0) {
                        navController.navigate(Routes.ENDING.name)
                    } else {
                        viewModel.updateRoom()
                        navController.navigate(Routes.GAMEDURATION.name)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PlayerInfo(
    player: Player
){
    if(player.name.isNotBlank()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(player.image),
                contentDescription = ""
            )
            Text(player.name, fontSize = 24.sp, color = Color.White)
        }
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Bu Gece Kimse Asılmadı",
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = Prociono,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
        }
    }
}
