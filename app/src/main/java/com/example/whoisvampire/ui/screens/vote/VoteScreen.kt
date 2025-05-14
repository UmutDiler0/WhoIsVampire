package com.example.whoisvampire.ui.screens.vote

import android.util.Log.v
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.screens.gameplay.GamePlayViewModel
import com.example.whoisvampire.ui.screens.gameplay.OtherPlayersView
import com.example.whoisvampire.ui.screens.gameplay.PlayerActionButton
import com.example.whoisvampire.ui.screens.gameplay.PlayerInfos
import com.example.whoisvampire.ui.screens.newgame.PlayerAvatar

@Composable
fun VoteScreen(
    navController: NavController,
    id: Int,
) {
    val viewModel: VoteViewModel = hiltViewModel()
    val playerNumber by viewModel.playerNumber.collectAsState()
    val isLoaded by viewModel.isListLoaded.collectAsState()
    val playerList by viewModel.newPlayerList.collectAsState()
    val player by viewModel.player.collectAsState()
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()
    val voteList = playerList.filter { it.isAlive && it.id != player.id }
    Box{
        BackGroundGradinet()
        if (isLoaded) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    BackAppButton(icon = Icons.Default.Clear) {
                        viewModel.clearRoom()
                        navController.navigate(Routes.DASHBOARD.name)
                    }
                }
                if (player.isAlive) {
                    VoteInfos(
                        player,
                        voteList,
                        viewModel
                    )
                }

                NextButton(
                    buttonText = "Vote"
                ) {
                    viewModel.addVote(selectedPlayer)

                    if (playerNumber >= playerList.size - 1) {
                        viewModel.resultOfVote()
                        navController.navigate(Routes.VOTERESULT.name)
                    } else {
                        viewModel.addPlayerNumber()
                    }
                }
            }
        }
    }
}


@Composable
fun VoteInfos(
    player: Player,
    playerList: List<Player>,
    viewModel: VoteViewModel
) {

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
            .size(200.dp)
            .padding(16.dp)
    )
    PlayerList(
        playerList, viewModel
    )


}

@Composable
fun PlayerList(
    list: List<Player>,
    viewModel: VoteViewModel
) {
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(list) { player ->
            PlayerAvatar(player, selectedPlayer) { player ->
                viewModel.selectPlayer(player)
            }
        }
    }
}

@Composable
fun PlayerAvatar(
    player: Player,
    selectedPlayer: Player?,
    onSelect: (Player) -> Unit
) {
    val isSelected = player.id == selectedPlayer?.id
    Box(
        modifier = Modifier
            .then(
                if (isSelected) Modifier.border(2.dp, Color.Red)
                else Modifier
            )
            .padding(8.dp)
            .clickable {
                onSelect(player)
            }
    ) {
        Column {
            Image(
                painter = painterResource(player.image),
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                player.name
            )
            Text(
                player.role
            )
        }
    }
}
