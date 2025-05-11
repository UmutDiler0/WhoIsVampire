package com.example.whoisvampire.ui.screens.gamesetting

import android.adservices.topics.Topic
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.data.model.Settings
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun GameSettingScreen(
    navController: NavController
) {
    val viewModel: GameSettingVM = hiltViewModel()
    val listOfSettings by viewModel.settings.collectAsState()
    val players by viewModel.playerList.collectAsState()
    val isLoaded by viewModel.isLoaded.collectAsState()
    if(isLoaded){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackAppButton() {
                viewModel.deleteAllRoles()
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            TopicText("Oyun Modu")
            GameDescText()
            Spacer(modifier = Modifier.padding(top = 32.dp))
            TopicText("Genel")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            GameSettings(listOfSettings, viewModel)
            NextButton {
                viewModel.insertSetting()
                navController.navigate(Routes.GAMEDURATION.name) {
                    popUpTo(Routes.GAMESETTINGS.name) {
                        inclusive = true
                    }
                }
            }
            LazyRow {
                items(players) { player ->
                    Text(player.role)
                }
            }
        }
    }
}

@Composable
fun GameSettings(
    listOfSetting: List<Settings>,
    viewModel: GameSettingVM
){
    LazyColumn {
        items(listOfSetting){ setting ->
            SettingItem(setting,viewModel)
        }
    }
}

@Composable
fun SettingItem(
    settings: Settings,
    viewModel: GameSettingVM
){
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = settings.name,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = {
                viewModel.changeSettingToList(settings.copy(isChecked = it))
                isChecked = it
            }
        )
    }
}

@Composable
fun TopicText(
    topic: String
) {
    Text(
        text = topic,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun GameDescText(

) {
    Text(
        "Bu auygulamda, oyun her oyuncuya rollerini göstererek, gece eylemlerinni izin vererek ve gün içindeki oyları takip ederek oyunu yönetir. Yeni oyuncular için önerilir",
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}