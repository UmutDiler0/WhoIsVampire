package com.example.whoisvampire.ui.screens.newgame

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.AppBarIcon
import com.example.whoisvampire.common.component.AppScreenName
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
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
    Box(modifier = Modifier.fillMaxSize()){
        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
            ) {
                BackAppButton(
                    title = "Yeni Oyun"
                ) {
                    navController.popBackStack()
                }
            }
                PlayerAvatarList(playerList, navController)
            Spacer(modifier = Modifier.weight(1f))
            Column{
                NextButton {
                    if (playerList.size < 4) Toast.makeText(
                        context,
                        "Oyun En Az 4 Kişi ile Oynanabilir",
                        Toast.LENGTH_SHORT
                    ).show()
                    else navController.navigate(Routes.ROLES.name)
                }
            }
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
        items(playerList.size + 1) { index ->
            if (index < playerList.size) {
                PlayerAvatar(playerList[index], navController)
            } else {
                AddPlayerButton(navController)
            }
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
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clickable {
                    navController.navigate(Routes.PLAYERDETAIL.name + "/$id")
                    Log.i("PlayerId", player.id.toString())
                }
        )
        Text(
            player.name,
            fontSize = 16.sp,
            color = Color.White
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
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {

            Image(
                painter = painterResource(R.drawable.addbtn),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )

    }
}

@Preview(
    showBackground = true
)
@Composable
fun NewGameUi(){
    val navController = rememberNavController()
    AddPlayerButton(navController)
}