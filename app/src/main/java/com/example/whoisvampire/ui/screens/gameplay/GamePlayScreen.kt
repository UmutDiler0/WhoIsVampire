package com.example.whoisvampire.ui.screens.gameplay

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun GamePlayScreen(
    navController: NavController,
    id: Int,
){
    val viewModel: GamePlayViewModel = hiltViewModel()
    LaunchedEffect(Unit) { viewModel.getPlayer(id) }
    val player by viewModel.player.collectAsState()
    val playerList by viewModel.playerList.collectAsState()
    val isLoaded by viewModel.isListLoaded.collectAsState()
    if(isLoaded){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                BackAppButton(
                    icon = Icons.Default.Clear
                ) {
                    viewModel.clearRoom()
                    navController.navigate(Routes.DASHBOARD.name)
                }
            }
            PlayerInfos(player,playerList,navController,viewModel)
        }
    }
}

@Composable
fun PlayerInfos(
    player: Player,
    playerList: List<Player>,
    navController: NavController,
    viewModel: GamePlayViewModel
){
    Text(
        player.name,
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
    Image(
        painter = painterResource(player.image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    )
    Text(
        player.role,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = when(player.role){
            "Villager" -> {"You Are Innocent Villager"}
            "Vampire" -> {"Bite Someone"}
            "Doctor" -> {"Save Someone"}
            "Seeker" -> {"Look Someone's Role"}
            else ->{""}
        },
        fontSize = 18.sp,
        modifier = Modifier.padding(8.dp)
    )
    when(player.role){
        "Villager" -> {
            PlayerActionButton("Next",viewModel,navController) {
                viewModel.changeSelect()
                navController.popBackStack()
            }
        }
        "Vampire" -> {
            OtherPlayersView(playerList,viewModel)
            PlayerActionButton("Bite",viewModel,navController) {
                navController.popBackStack()
            }
        }
        "Seeker" -> {
            OtherPlayersView(playerList,viewModel)
            PlayerActionButton("See Role",viewModel,navController) {
                navController.popBackStack()
            }
        }
        "Doctor" -> {
            OtherPlayersView(playerList,viewModel)
            PlayerActionButton("Protect",viewModel,navController) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun OtherPlayersView(
    playerList: List<Player>,
    viewModel: GamePlayViewModel
) {
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(16.dp)
    ) {
        items(playerList) { player ->
            OtherPlayersItem(
                player = player,
                selectedPlayer = selectedPlayer,
                onPlayerSelected = { viewModel.selectPlayer(it) }
            )
        }
    }
}

@Composable
fun OtherPlayersItem(
    player: Player,
    selectedPlayer: Player?,
    onPlayerSelected: (Player) -> Unit
) {
    val isSelected = selectedPlayer?.id == player.id

    Box(
        modifier = Modifier
            .then(
                if (isSelected) Modifier.border(2.dp, Color.Red)
                else Modifier
            )
            .padding(8.dp)
            .clickable {
                onPlayerSelected(player)
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(player.image),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(player.name)
            Text(player.role)
            if(player.role != "Vampire" && player.selectedBy.isNotBlank()) Text(player.biteCount.toString())
        }
    }
}

@Composable
fun PlayerActionButton(
    buttonText: String,
    viewModel: GamePlayViewModel,
    navController: NavController,
    onClick: () -> Unit,
) {
    val currentPlayer by viewModel.player.collectAsState()
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()
    val allPlayer by viewModel.allPlayer.collectAsState()
    val isLastPerson = allPlayer.last() == currentPlayer
    val localContext = LocalContext.current
    if(currentPlayer.role == "Villager") viewModel.selectPlayer(Player.empty())

    Button(
        onClick = {
            if(selectedPlayer == null){
                Toast.makeText(localContext,"Please Select player", Toast.LENGTH_SHORT).show()
            }else{
                if (isLastPerson) {
                    viewModel.selectedBy(currentPlayer)
                    viewModel.updateRoom(selectedPlayer!!)
                    navController.navigate(Routes.NIGHTRESULT.name)

                } else {
                    viewModel.selectedBy(currentPlayer)
                    viewModel.updateRoom(selectedPlayer!!)
                    onClick()

                }
            }
        }
    ) {
        Text(buttonText)
    }
}

