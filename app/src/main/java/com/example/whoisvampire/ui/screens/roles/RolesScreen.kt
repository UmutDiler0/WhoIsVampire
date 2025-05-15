package com.example.whoisvampire.ui.screens.roles

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.AppBarIcon
import com.example.whoisvampire.common.component.AppScreenName
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.common.util.roles
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.ui.routes.Routes
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("ContextCastToActivity")
@Composable
fun RolesScreen(
    navController: NavController
) {
    val viewModel: RolesScreenViewModel = hiltViewModel()
    val localContext = LocalContext.current
    val isNavAvailable by viewModel.isNavAvailable.collectAsState()
    val roleBound by viewModel.roleBound.collectAsState()

    Box {
        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackAppButton(title = "Roller") {
                    navController.popBackStack()
                }
                AppScreenName(Routes.ROLES.name)
                AppBarIcon()
            }

            RolesListView(viewModel)

            Spacer(modifier = Modifier.padding(top = 16.dp))

            NextButton {
                if (isNavAvailable) {
                    viewModel.saveRolesToDatabase {
                        viewModel.matchPlayerWithRoles()
                        navController.navigate(Routes.GAMEDURATION.name) {
                            popUpTo(Routes.ROLES.name) { inclusive = true }
                        }
                    }
                } else {
                    Toast.makeText(
                        localContext,
                        "Lütfen $roleBound Rol Seçiniz ve Vampir sayısı diğerlerinden az olsun",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Spacer(Modifier.padding(top = 32.dp))
            Button(
                onClick = {
                    viewModel.deleteAllPlayers()
                    navController.navigate(Routes.DASHBOARD.name)
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Oyunu sıfırla", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun RolesListView(
    viewModel: RolesScreenViewModel
) {
    val rolesList = roles.rolesList
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(rolesList) { role ->
            RolesItem(role, viewModel)
        }
    }
}

@Composable
fun RolesItem(
    role: Roles,
    viewModel: RolesScreenViewModel
) {
    val gameRoleList by viewModel.gameRoleList.collectAsState()
    val totalNumber by viewModel.totalRoleNumber.collectAsState()

    val currentCount = gameRoleList.find { it.name == role.name }?.count ?: 0

    LaunchedEffect(totalNumber) {
        viewModel.checkNavConditions()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(role.image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
                Text(role.name, color = Color.White)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AddOrSubButton(icon = Icons.Default.KeyboardArrowDown) {
                        if (currentCount > 0) {
                            viewModel.removeFromRolesList(role)
                        }
                    }
                    Text("$currentCount", color = Color.White)
                    AddOrSubButton(icon = Icons.Default.KeyboardArrowUp) {
                        viewModel.addRoleToRolesList(role)
                    }
                }
            }
        }
    }
}

@Composable
fun AddOrSubButton(
    icon: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(24.dp)
    ) {
        Icon(imageVector = icon, contentDescription = "")
    }
}
