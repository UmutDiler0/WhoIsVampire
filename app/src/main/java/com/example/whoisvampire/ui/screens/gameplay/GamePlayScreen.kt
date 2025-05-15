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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.textstyles.PrincessSofia
import com.example.whoisvampire.ui.textstyles.Prociono

@Composable
fun GamePlayScreen(
    navController: NavController,
    id: Int,
) {
    val viewModel: GamePlayViewModel = hiltViewModel()
    LaunchedEffect(Unit) { viewModel.getPlayer(id) }
    val player by viewModel.player.collectAsState()
    val playerList by viewModel.playerList.collectAsState()
    val isLoaded by viewModel.isListLoaded.collectAsState()
    Box {
        BackGroundGradinet()
        if (isLoaded) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BackAppButton(
                    icon = Icons.Default.Clear,
                    title = player.name
                ) {
                    viewModel.clearRoom()
                    navController.navigate(Routes.DASHBOARD.name)
                }

                PlayerInfos(player, playerList, navController, viewModel)
            }
        }
    }
}

@Composable
fun PlayerInfos(
    player: Player,
    playerList: List<Player>,
    navController: NavController,
    viewModel: GamePlayViewModel
) {
    Text(
        player.name,
        fontSize = 24.sp,
        color = Color.White,
        fontFamily = Prociono,
        modifier = Modifier.padding(16.dp)
    )
    Image(
        painter = if (player.role == "Vampir") painterResource(R.drawable.vampir)
        else painterResource(R.drawable.villager),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
    Text(
        player.role,
        fontSize = 16.sp,
        color = Color.White,
        fontFamily = PrincessSofia,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = when (player.role) {
            "Köylü" -> {
                "Masum Bir Köylüsün Git Ve Tarlanı Biç"
            }

            "Vampir" -> {
                "Birini Isırmalısın"
            }

            "Şifacı" -> {
                "Birini Koru"
            }

            "Gözcü" -> {
                "Birinin Rolünü Dikizle"
            }

            else -> {
                ""
            }
        },
        fontSize = 18.sp,
        color = Color.White,
        fontFamily = Prociono,
        modifier = Modifier.padding(8.dp)
    )
    when (player.role) {
        "Köylü" -> {
            PlayerActionButton("Geç", viewModel, navController) {
                viewModel.changeSelect()
                navController.popBackStack()
            }
        }

        "Vampir" -> {
            OtherPlayersView(playerList, viewModel)
            PlayerActionButton("Isır", viewModel, navController) {
                navController.popBackStack()
            }
        }

        "Gözcü" -> {
            OtherPlayersView(playerList, viewModel)
            PlayerActionButton("Rolü Gör", viewModel, navController) {
                navController.popBackStack()
            }
        }

        "Şifacı" -> {
            OtherPlayersView(playerList, viewModel)
            PlayerActionButton("Koru", viewModel, navController) {
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
                modifier = Modifier.size(100.dp)
            )
            Text(player.name, color = Color.White, fontSize = 16.sp)
            Text(
                if(player.role == "Vampir") player.role
                else "",
                color = Color.White,
                fontSize = 16.sp
            )
            if (player.role != "Vampir" && player.selectedBy.isNotBlank()) Text(player.biteCount.toString())
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
    val playerList by viewModel.playerList.collectAsState()
    var vampireCount = 0
    var villagerCount = 0
    playerList.forEach {
        if (it.isAlive) {
            if (it.name == "Vampir") vampireCount++
            else villagerCount++
        }
    }
    if (currentPlayer.role == "Köylü") viewModel.selectPlayer(Player.empty())

    Button(
        onClick = {
            if (selectedPlayer == null) {
                Toast.makeText(localContext, "Lütfen Oyuncu Seç", Toast.LENGTH_SHORT).show()
            } else {
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
        },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color)),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)
    ) {
        Text(buttonText,color = Color.White)
    }
}

