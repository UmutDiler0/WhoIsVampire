package com.example.whoisvampire.ui.screens.addplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.util.PlayerAvatar
import com.example.whoisvampire.data.model.Player
import com.example.whoisvampire.ui.routes.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerScreen(
    navController: NavController
){
    val viewModel : AddPlayerViewModel = hiltViewModel()
    val player by viewModel.player.collectAsState()
    var playerName by remember { mutableStateOf("")}
    val randomImage = remember { PlayerAvatar.playerAvatar.random() }
    Box(modifier = Modifier.fillMaxSize()){
        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackAppButton() {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            SelectImage(randomImage)
            Spacer(modifier = Modifier.padding(top = 16.dp))
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                placeholder = {
                    Text(text = "Player Name")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(36.dp)
                    ),
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Button(
                onClick = {
                    viewModel.addPlayer(
                        Player(
                            name = playerName,
                            image = randomImage,
                            role = "",
                            isAlive = true,
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp)
            ) {
                Text(
                    text = "Save",
                    fontSize = 16.sp
                )
            }

        }
    }
}

@Composable
fun SelectImage(
    randomImage: Int
){

    Image(
        painter = painterResource(randomImage),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    )
}

//@Composable
//@Preview(
//    showBackground = true
//)
//fun AddPlayerUi(){
//    val navController = rememberNavController()
//    AddPlayerScreen(navController)
//}