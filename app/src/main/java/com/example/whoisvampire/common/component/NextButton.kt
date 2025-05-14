package com.example.whoisvampire.common.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whoisvampire.R

@Composable
fun NextButton(
    buttonText: String = "Next",
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)
            .border(width = 1.dp,
            color = colorResource(R.color.button_color),
            shape = RoundedCornerShape(50)
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 24.sp,
            color = colorResource(R.color.button_color)
        )
    }
}