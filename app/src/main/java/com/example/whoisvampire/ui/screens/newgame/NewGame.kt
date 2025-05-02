package com.example.whoisvampire.ui.screens.newgame

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun NewGameScreen(
    navController: NavController
){
    val viewModel: NewGameViewModel = hiltViewModel()
    viewModel.getPlayerList()
    val playerList by viewModel.playerList.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackAppButton(){
            navController.popBackStack()
        }
        PlayerAvatarList(playerList,navController)
        Spacer(modifier = Modifier.padding(top = 32.dp))
        AddPlayerButton(navController)
        Spacer(modifier = Modifier.padding(top = 32.dp))
        NextButton {
            if(playerList.size < 4) Toast.makeText(context,"Game can play with at least 4 player",Toast.LENGTH_SHORT).show()
            else navController.navigate(Routes.ROLES.name)
        }
    }
}

@Composable
fun PlayerAvatarList(
    playerList: List<Player>,
    navController: NavController
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        items(playerList){ player ->
            PlayerAvatar(player = player,navController = navController)
        }
    }
}

@Composable
fun PlayerAvatar(
    player: Player,
    navController: NavController
){
    val id = player.id
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = player.image),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.padding(8.dp)
                .clickable {
                    navController.navigate(Routes.PLAYERDETAIL.name + "/$id")
                    Log.i("PlayerId", player.id.toString())
                }
        )
        Text(
            player.name,
            fontSize = 16.sp
        )
    }
}

@Composable
fun AddPlayerButton(
    navController: NavController
){
    Button(
        onClick = {
            navController.navigate(Routes.ADDPLAYER.name)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
    ) {
        Text(
            text = "Add Player",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}