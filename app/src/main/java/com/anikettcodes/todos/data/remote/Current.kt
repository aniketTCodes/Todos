package com.anikettcodes.todos.data.remote

import com.squareup.moshi.Json

data class Current(
    @field:Json(name="temp_c")
    val temperature:Double,
    val condition: Condition
)
