package com.example.whoisvampire.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.screens.addplayer.SelectImage

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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            BackAppButton(){
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.padding(top = 32.dp))
        SelectImage()
        Spacer(modifier = Modifier.padding(top = 32.dp))
        Text("Player Name")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isEditEnabled){
                OutlinedTextField(
                    value = playerName,
                    onValueChange = {playerName = it},
                    )
            }else{
                Text(player.name)
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)
        ) {
            Text("Save Changes")
        }
        Spacer(modifier = Modifier.padding(top = 32.dp))
        OutlinedButton(
            onClick = {
                viewModel.deletePlayer(player)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)
        ) {
            Text("Delete Player")
        }
    }
}