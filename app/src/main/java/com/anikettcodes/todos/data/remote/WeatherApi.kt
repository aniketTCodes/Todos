package com.anikettcodes.todos.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/current.json?key=8e68223bff194e03bb453429231804")
    suspend fun getWeatherData(
        @Query("q")location:String
    ): WeatherDto
}