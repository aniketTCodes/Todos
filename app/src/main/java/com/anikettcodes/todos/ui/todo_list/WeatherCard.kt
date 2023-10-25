package com.anikettcodes.todos.ui.todo_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.anikettcodes.todos.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anikettcodes.todos.ui.theme.DarkBlue
import com.anikettcodes.todos.ui.theme.TodosTheme
import com.anikettcodes.todos.util.WeatherInfo
import com.anikettcodes.todos.util.codeToImage


@Composable
fun WeatherCard(
   weatherInfo: WeatherInfo
){

    Card (
        colors = CardDefaults.cardColors(containerColor = DarkBlue),
        modifier = Modifier.padding(8.dp)
    ){
        Row(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(0.75f)
            ){
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ){
                    Text(
                        text = weatherInfo.temp,
                        fontSize = 32.sp,
                        color = Color.White,
                    )
                    Text(
                        text = "°C",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom=5.dp)
                    )
                }

                Text(
                    text = weatherInfo.description,
                    fontSize = 20.sp,
                    color = Color.White,

                )
                Text(
                    text = weatherInfo.location,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = weatherInfo.icon!!),
                contentDescription = "weather information",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}
@Preview(showBackground = true)
@Composable
fun WeatherCardPreview(){
    TodosTheme {
        WeatherCard(
            weatherInfo = WeatherInfo(
                temp="30",
                description = "Mist",
                icon = R.drawable.ic_cloudy,
                location = "Ghaziabad,Uttar Pradesh"
            )
        )
    }
}