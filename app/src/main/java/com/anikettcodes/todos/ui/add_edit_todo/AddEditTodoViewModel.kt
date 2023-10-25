package com.anikettcodes.todos.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anikettcodes.todos.data.local.Todo
import com.anikettcodes.todos.data.repository.TodoRepository
import com.anikettcodes.todos.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle:SavedStateHandle
):ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var isHighPriority by mutableStateOf(false)
    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    init {
        val todoId=savedStateHandle.get<Int>("todoId")!!
        if(todoId!=-1){
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let {
                    title=it.title
                    description=it.description?:""
                    isHighPriority=it.isHighPriority
                    this@AddEditTodoViewModel.todo=it
                }

            }
        }
    }

    fun onEvent(event:AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isNotBlank()){
                        repository.insertTodo(Todo(title,description,todo?.isDone?:false,todo?.isHighPriority?:false,id=todo?.id))
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                    else{
                        sendUiEvent(UiEvent.ShowSnackBar(message = "Title cannot be empty"))
                        return@launch
                    }
                }
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description=event.description
            }
            is AddEditTodoEvent.OnTitleChange->{
                title=event.title
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}

