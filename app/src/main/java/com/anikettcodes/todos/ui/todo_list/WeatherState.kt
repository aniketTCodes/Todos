package com.anikettcodes.todos.ui.todo_list

import com.anikettcodes.todos.data.remote.WeatherDto

data class WeatherState(
    val weatherData:WeatherDto?=null,
    val isLoading:Boolean=false,
    val error:String?=null
)
