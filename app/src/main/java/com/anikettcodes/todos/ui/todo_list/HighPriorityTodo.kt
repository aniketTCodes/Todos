package com.anikettcodes.todos.ui.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anikettcodes.todos.util.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighPriorityTodoListScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: TodoListViewModel,
    state: WeatherState
){

    val todos=viewModel.todos.collectAsState(initial = emptyList())
    val snackBarHostState = remember{SnackbarHostState()}
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event->
            when(event){
                is UiEvent.ShowSnackBar->{
                    val result=snackBarHostState.showSnackbar(
                        event.message,
                        event.action,
                    )
                    if(result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteTodo)
                    }
                }
                is UiEvent.Navigate->onNavigate(event)
                else -> Unit

            }
        }
    }

    Scaffold (
        snackbarHost={SnackbarHost(hostState = snackBarHostState)},
        modifier = Modifier.padding(16.dp),
        bottomBar = { BottomNavigation(onNavigate = onNavigate)},
        containerColor = Color.Black
    ){

        LazyColumn(
            modifier= Modifier
                .padding(it)
                .fillMaxSize(),
        ){
            items(todos.value.filter { todo->todo.isHighPriority }){ todo->
                TodoItem(todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoCLick(todo))
                        }
                )
            }

        }
    }
}