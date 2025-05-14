package com.example.whoisvampire.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.whoisvampire.R

@Composable
fun BackGroundGradinet(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(R.drawable.bg_image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1E343C).copy(alpha = 0.8f),
                            Color(0xFF528DA2).copy(0.8f)
                        ),

                        )
                )
        )
    }
}