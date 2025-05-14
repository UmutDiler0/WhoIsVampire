package com.example.whoisvampire.common.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun AppScreenName(
    topic: String
){
    Text(
        topic,
        fontSize = 20.sp,
    )
}