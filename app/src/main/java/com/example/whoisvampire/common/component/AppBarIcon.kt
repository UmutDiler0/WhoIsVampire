package com.example.whoisvampire.common.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBarIcon(

){
    Icon(
        imageVector = Icons.Default.Person,
        contentDescription = "",
        modifier = Modifier.size(48.dp)
    )
}