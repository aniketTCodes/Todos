package com.anikettcodes.todos.ui.todo_list

import com.anikettcodes.todos.data.local.Todo

sealed class TodoListEvent {
    data class DeleteTodo(val todo: Todo):TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone:Boolean):TodoListEvent()
    object OnUndoDeleteTodo:TodoListEvent()
    data class OnTodoCLick(val todo: Todo):TodoListEvent()
    object OnAddTodoClick:TodoListEvent()
    data class OnPriorityChange(val todo: Todo):TodoListEvent()

}