package com.anikettcodes.todos.di

import android.app.Application
import androidx.room.Room
import com.anikettcodes.todos.data.local.TodoDatabase
import com.anikettcodes.todos.data.repository.TodoRepository
import com.anikettcodes.todos.data.repository.TodoRepositoryImpl
import com.anikettcodes.todos.data.remote.WeatherApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app:Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
             TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase,api:WeatherApi): TodoRepository = TodoRepositoryImpl(db.dao,api)


    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}