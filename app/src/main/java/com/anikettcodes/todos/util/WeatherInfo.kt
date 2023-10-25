package com.anikettcodes.todos.util

import com.anikettcodes.todos.R
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import com.anikettcodes.todos.ui.todo_list.WeatherState

val codeToImage= mapOf<Int,Int>(
    1000 to R.drawable.ic_sunny,
    1003 to R.drawable.ic_sunnycloudy,
    1006 to R.drawable.ic_cloudy,
    1009 to R.drawable.ic_very_cloudy,
    1030 to R.drawable.ic_cloudy,
    1063 to R.drawable.ic_sunnyrainy,
    1066 to R.drawable.ic_snowy,
    1069 to R.drawable.ic_snowyrainy,
    1072 to R.drawable.ic_rainshower,
    1087 to R.drawable.ic_rainythunder,
    1114 to R.drawable.ic_snowy,
    1117 to R.drawable.ic_heavysnow,
    1135 to R.drawable.ic_cloudy,
    1147 to R.drawable.ic_cloudy,
    1150 to R.drawable.ic_rainshower,
    1168 to R.drawable.ic_rainshower,
    1171 to R.drawable.ic_rainshower,
    1180 to R.drawable.ic_rainshower,
    1183 to R.drawable.ic_rainythunder
)

data class WeatherInfo(
    val temp:String="-",
    val description:String="-",
    val icon:Int=R.drawable.baseline_error_outline_24,
    val location:String="-"
)

fun stateToWeatherInfo(state:WeatherState):WeatherInfo{
    return if(state.weatherData!=null){
        WeatherInfo(
            temp = state.weatherData.current.temperature.toString(),
            description = state.weatherData.current.condition.text,
            icon = codeToImage[state.weatherData.current.condition.code]!!,
            location = state.weatherData.location.name+", "+state.weatherData.location.region
        )
    }
    else
        WeatherInfo()
}

