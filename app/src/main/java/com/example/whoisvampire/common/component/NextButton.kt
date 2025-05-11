package com.example.whoisvampire.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NextButton(
    buttonText: String = "Next",
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 24.sp
        )
    }
}