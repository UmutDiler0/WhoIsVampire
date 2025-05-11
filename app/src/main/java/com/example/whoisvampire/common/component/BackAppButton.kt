package com.example.whoisvampire.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BackAppButton(
    icon: ImageVector = Icons.Default.ArrowBack,
    onClick: () -> Unit,
) {
   IconButton(
       onClick = onClick,
       modifier = Modifier.padding(8.dp)
   ) {
       Icon(
           imageVector = icon,
           contentDescription = "Back"
       )
   }

}