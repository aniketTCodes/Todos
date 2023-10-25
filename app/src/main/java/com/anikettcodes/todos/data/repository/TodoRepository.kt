package com.anikettcodes.todos.data.repository


import com.anikettcodes.todos.data.local.Todo
import com.anikettcodes.todos.data.remote.WeatherDto
import com.anikettcodes.todos.util.Resource
import kotlinx.coroutines.flow.Flow

interface TodoRepository  {

    suspend fun getWeatherData(location:String): Resource<WeatherDto>

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id:Int): Todo?

    fun getTodos(): Flow<List<Todo>>
}
