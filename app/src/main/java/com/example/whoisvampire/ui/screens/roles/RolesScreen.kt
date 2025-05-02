package com.example.whoisvampire.ui.screens.roles

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackAppButton
import com.example.whoisvampire.common.component.NextButton
import com.example.whoisvampire.common.util.roles
import com.example.whoisvampire.data.model.Roles
import com.example.whoisvampire.ui.routes.Routes
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("ContextCastToActivity")
@Composable
fun RolesScreen(
    navController: NavController
){
    val viewModel: RolesScreenViewModel = hiltViewModel()
    val localContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackAppButton(){
            navController.popBackStack()
        }
        RolesListView(roles.rolesList,viewModel)
        Spacer(modifier = Modifier.padding(top = 16.dp))
        NextButton {
            viewModel.isNavAvailable()
            val currentNavState = viewModel.isNavAvailable.value
            if (currentNavState) {
                viewModel.insertRolesToRoom()
                navController.navigate(Routes.GAMESETTINGS.name)
            } else {
                Toast.makeText(
                    localContext,
                    "Please select ${viewModel.roleBound.value} roles",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun RolesListView(
    list: List<Roles>,
    viewModel: RolesScreenViewModel
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(list){ lokman ->
            RolesItem(lokman,viewModel)
        }
    }
}

@Composable
fun RolesItem(
    role: Roles,
    viewModel: RolesScreenViewModel
){
    val roleList by viewModel.roleList.collectAsState()
    val updatedRole = roleList.find { it.name == role.name }
    var numberOfRole = updatedRole?.countOfRoles ?: 0
    val totalNumber by viewModel.totalRoleNumber.collectAsState()
    LaunchedEffect(totalNumber) { viewModel.isNavAvailable() }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Text(role.name)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AddOrSubButton(icon = Icons.Default.KeyboardArrowDown){

                        if(numberOfRole > 0) {
                            numberOfRole--
                            viewModel.RemoveFromRolesList(role)
                        }
                    }
                    Text("${numberOfRole}")
                    AddOrSubButton(
                        icon = Icons.Default.KeyboardArrowUp
                    ) {
                        numberOfRole++
                        viewModel.AddRoleToRolesList(role)
                    }
                }
            }
        }
    }
}

@Composable
fun AddOrSubButton(
    icon: ImageVector,
    onClick : () -> Unit,
){
    IconButton(
        onClick = onClick,
        Modifier
            .background(color = Color.LightGray)
            .size(24.dp)
    ) {
        Icon(imageVector = icon, contentDescription = "")
    }
}