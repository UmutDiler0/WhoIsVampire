package com.example.whoisvampire.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whoisvampire.R
import com.example.whoisvampire.common.component.BackGroundGradinet
import com.example.whoisvampire.ui.routes.Routes

@Composable
fun DashBoardScreen(
    navController: NavController
){
    Box(modifier = Modifier.fillMaxSize()){

        BackGroundGradinet()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(top = 48.dp))
            Box(
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.app_icon2),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(240.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "Logo",
                    modifier = Modifier.size(180.dp)
                )
                Image(
                    painter = painterResource(R.drawable.app_imaage),
                    contentDescription = "",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 32.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ImageText(R.drawable.g)
                ImageText(R.drawable.o)
                ImageText(R.drawable.z)
                ImageText(R.drawable.c)
                ImageText(R.drawable.u)
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Button(
                onClick = {
                    navController.navigate(Routes.NEWGAME.name)
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .border(width = 1.dp,
                        color = colorResource(R.color.button_color),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Text(
                    text = "New Game",
                    fontSize = 32.sp,
                    color = colorResource(R.color.button_color),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
fun ImageText(
    image: Int
){
    Image(
        painter = painterResource(image),
        contentDescription = "",
        modifier = Modifier.size(75.dp)
    )
}

@Composable
@Preview(
    showBackground = true,
)
fun DashboardUi(){
    val navController = rememberNavController()
    DashBoardScreen(navController)
}