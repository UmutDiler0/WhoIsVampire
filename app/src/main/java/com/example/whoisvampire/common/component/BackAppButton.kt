package com.example.whoisvampire.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whoisvampire.ui.textstyles.PrincessSofia

@Composable
fun BackAppButton(
    icon: ImageVector = Icons.Default.ArrowBack,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        IconButton(
            onClick = onClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Back"
            )
        }
        Text(
            title,
            fontSize = 40.sp,
            fontFamily = PrincessSofia,
            color = Color.Black
        )
        AppBarIcon()
    }

}