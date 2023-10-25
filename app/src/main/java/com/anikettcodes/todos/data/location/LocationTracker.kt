package com.anikettcodes.todos.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}