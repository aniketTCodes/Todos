package com.anikettcodes.todos.data.repository

import com.anikettcodes.todos.data.local.Todo
import com.anikettcodes.todos.data.local.TodoDao
import com.anikettcodes.todos.data.remote.WeatherApi
import com.anikettcodes.todos.data.remote.WeatherDto
import com.anikettcodes.todos.data.repository.TodoRepository
import com.anikettcodes.todos.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val dao: TodoDao,private val weatherApi: WeatherApi): TodoRepository {
    override suspend fun getWeatherData(location:String): Resource<WeatherDto> {
            return try {
                Resource.Success(
                    data=weatherApi.getWeatherData(location)
                )
            }catch (e:Exception){
                e.printStackTrace()
                Resource.Error(e.message?:"An unknown error occurred.")
            }
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? =
        dao.getTodoById(id)


    override fun getTodos(): Flow<List<Todo>> = dao.getTodos()


}