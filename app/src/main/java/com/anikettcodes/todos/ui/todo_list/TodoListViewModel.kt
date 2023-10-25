package com.anikettcodes.todos.ui.todo_list

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anikettcodes.todos.data.local.Todo
import com.anikettcodes.todos.data.repository.TodoRepository
import com.anikettcodes.todos.data.location.LocationTracker
import com.anikettcodes.todos.data.remote.WeatherDto
import com.anikettcodes.todos.util.Resource
import com.anikettcodes.todos.util.Routes
import com.anikettcodes.todos.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
    val todos=repository.getTodos()
    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    private var deletedTodo: Todo?=null
    fun onEvent(event:TodoListEvent){
        when(event){
            is TodoListEvent.DeleteTodo -> {
                deletedTodo=event.todo
                viewModelScope.launch{
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("Todo deleted", action = "undo"))
                }
            }
            TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(isDone = event.isDone)
                    )
                }
            }
            is TodoListEvent.OnTodoCLick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO+"?todoId=${event.todo.id}"))
            }
            TodoListEvent.OnUndoDeleteTodo -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                    }
                }
            }
            is TodoListEvent.OnPriorityChange -> {

                viewModelScope.launch {
                    var message:String="";
                    val currPriority=event.todo.isHighPriority
                    repository.insertTodo(
                        event.todo.copy(isHighPriority = !currPriority)
                    )
                    message = if(currPriority){
                        "Removed from high priority list"
                    } else{
                        "Added to high priority list"
                    }
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = message
                    ))
                }

            }

        }
    }
    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun loadWeatherInfo(){

        viewModelScope.launch(Dispatchers.IO){

            locationTracker.getCurrentLocation()?.let {
                Log.e("Weather","${it.latitude}")
                when(val result=repository.getWeatherData("${it.latitude},${it.longitude}")){
                    is Resource.Success->{
                        Log.e("weather","success")
                        state=state.copy(
                            weatherData = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error->{
                        Log.e("weather","resource failed messsage=${result.message}")

                    }

                }
            }?:kotlin.run {

            }
        }

    }

}