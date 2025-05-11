package com.example.whoisvampire.ui.screens.voteresult

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun VoteResult(
    navController: NavController
){
    val viewModel: VoteResultVM = hiltViewModel()
    val player by viewModel.player.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackAppButton(
                icon = Icons.Default.Clear
            ) {
                navController.navigate(Routes.DASHBOARD.name)
            }
        }
        Text("Death")
        PlayerInfo(player)
        NextButton(
            "Next Day"
        ) {
            navController.navigate(Routes.GAMEDURATION.name)
        }
    }
}

@Composable
fun PlayerInfo(
    player: Player
){
    Column {
        Image(
            painter = painterResource(player.image),
            contentDescription = ""
        )
        Text(player.name)
    }
}
