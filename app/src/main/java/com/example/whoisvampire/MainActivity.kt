package com.example.whoisvampire

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.whoisvampire.ui.routes.Routes
import com.example.whoisvampire.ui.screens.detail.PlayerDetailScreen
import com.example.whoisvampire.ui.screens.addplayer.AddPlayerScreen
import com.example.whoisvampire.ui.screens.chooseplayer.ChoosePlayerScreen
import com.example.whoisvampire.ui.screens.dashboard.DashBoardScreen
import com.example.whoisvampire.ui.screens.game.GameScreen
import com.example.whoisvampire.ui.screens.gamesetting.GameSettingScreen
import com.example.whoisvampire.ui.screens.newgame.NewGameScreen
import com.example.whoisvampire.ui.screens.roles.RolesScreen
import com.example.whoisvampire.ui.theme.WhoIsVampireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            WhoIsVampireTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(modifier = Modifier.padding(innerPadding),navController)
                }
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.DASHBOARD.name
    ) {
        composable(route = Routes.DASHBOARD.name){
            DashBoardScreen(navController)
        }
        composable(route = Routes.NEWGAME.name){
            NewGameScreen(navController)
        }
        composable(route = Routes.CHOOSEPLAYER.name){
            ChoosePlayerScreen(navController)
        }
        composable(route = Routes.ADDPLAYER.name){
            AddPlayerScreen(navController)
        }
        composable(route = Routes.GAME.name){
            GameScreen(navController)
        }
        composable(route = Routes.ROLES.name){
            RolesScreen(navController)
        }
        composable(route = Routes.GAMESETTINGS.name){
            GameSettingScreen(navController)
        }
        composable(
            route = Routes.PLAYERDETAIL.name + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getInt("id")
            Log.i("PlayerDetailScreen", "Player ID: $playerId")
            PlayerDetailScreen(navController, id = playerId ?: 0)
        }
    }
}
