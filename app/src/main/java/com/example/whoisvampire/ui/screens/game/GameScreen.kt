package com.example.whoisvampire.ui.screens.game

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.screens.gameplay.GamePlayScreen
import com.example.whoisvampire.ui.screens.gameplay.GamePlayViewModel

@Composable
fun GameScreen(
    navController: NavController,
) {
    val viewModel: GameScreenViewModel = hiltViewModel()
    val playerNumber by viewModel.playerNumber.collectAsState()
    val playerList by viewModel.playerList.collectAsState()
    val isRoomCame by viewModel.isListLoaded.collectAsState()


    if (isRoomCame) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackAppButton(icon = Icons.Default.Clear) {
                viewModel.clearAllSetting()
                navController.navigate(Routes.DASHBOARD.name)
            }

            if (playerNumber < playerList.size ) {
                PlayerTurn(
                    player = playerList[playerNumber],
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}


@Composable
fun PlayerTurn(
    player: Player,
    navController: NavController,
    viewModel: GameScreenViewModel
) {
    var isVisible by remember { mutableStateOf(false) }
    val id = player.id
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            player.name,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "If you want to see your role click your image",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(player.image),
            contentDescription = "",
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    isVisible = !isVisible
                },
        )
        Text(
            text = "Please give the phone to player",
            modifier = Modifier.padding(8.dp)
        )
        if (isVisible) {
            Button(
                onClick = {
                    viewModel.addPlayerNumber()
                    navController.navigate(Routes.CHOOSEPLAYER.name + "/${player.id}")
                }
            ) {
                Text(
                    "Show Role"
                )
            }
        }
    }
}


