package com.example.whoisvampire.ui.screens.gameduration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun GameDurationScreen(
    navController: NavController
){
    val viewModel: GameDurationVM = hiltViewModel()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Row(
           modifier = Modifier.fillMaxWidth()
       ) {
            BackAppButton(icon = Icons.Default.Clear) {
                viewModel.clearRoom()
            }
        }
        Text("Day 1")
        Image(
            painter = painterResource(R.drawable.vampir_koylu),
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "asdasdasdasdasdasdasd",
        )
        NextButton(buttonText = "Ready") {
            navController.navigate(Routes.GAME.name)
        }
    }
}