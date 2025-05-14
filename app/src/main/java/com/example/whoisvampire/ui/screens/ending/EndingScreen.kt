package com.example.whoisvampire.ui.screens.ending

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun EndingScreen(
    navController: NavController
){
    val viewModel: EndingViewModel = hiltViewModel()
    val isLoaded by viewModel.isLoaded.collectAsState()
    val winnerList by viewModel.winnerList.collectAsState()
    val winnerRole by viewModel.winnerType.collectAsState()
    Box{
        BackGroundGradinet()
        if (isLoaded) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = if (winnerRole.name == "Vampire") painterResource(R.drawable.vampir_koylu)
                    else painterResource(R.drawable.bg_image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp)
                )
                Text(winnerRole.name, modifier = Modifier.padding(vertical = 24.dp))
                WinnerList(winnerList)
                NextButton("End Game") {
                    viewModel.resetGame()
                    navController.navigate(Routes.DASHBOARD.name)
                }
            }
        }
    }
}

@Composable
fun WinnerList(
    list: List<Player>
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(list){ player ->
            WinnerItem(player)
        }
    }
}

@Composable
fun WinnerItem(
    player: Player
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(player.image),
            contentDescription = ""
        )
        Text(player.name)
    }
}