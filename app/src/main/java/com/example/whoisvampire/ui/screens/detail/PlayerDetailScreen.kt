package com.example.whoisvampire.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.screens.addplayer.SelectImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    navController: NavController,
    id: Int
){
    val viewModel: DetailViewModel = hiltViewModel()
    viewModel.getPlayerById(id)
    val player by viewModel.currentPlayer.collectAsState()
    var playerName by remember { mutableStateOf(player.name)}
    var isEditEnabled by remember { mutableStateOf(false)}
    Box(){
        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BackAppButton(title = player.name) {
                    navController.popBackStack()
                }
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            SelectImage(randomImage = player.image)
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text("Oyuncu Adı", color = Color.White)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditEnabled) {
                    OutlinedTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        placeholder = {
                            Text(text = "Oyuncu Adı")
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
                } else {
                    Text(player.name, color = Color.White)
                }
                IconButton(
                    onClick = {
                        isEditEnabled = !isEditEnabled
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                }
            }
            Button(
                onClick = {
                    viewModel.updatePlayer(player.copy(name = playerName))
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color))
            ) {
                Text("Değişiklikleri Kaydet",color = Color.White)
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            OutlinedButton(
                onClick = {
                    viewModel.deletePlayer(player)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.white))
            ) {
                Text("Oyuncuyu Sil",color = colorResource(R.color.button_color))
            }
        }
    }
}