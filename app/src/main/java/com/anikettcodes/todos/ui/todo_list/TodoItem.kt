package com.anikettcodes.todos.ui.todo_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anikettcodes.todos.data.local.Todo
import com.anikettcodes.todos.ui.theme.TodosTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent)->Unit,
    modifier:Modifier=Modifier
){
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density= LocalDensity.current
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onEvent(TodoListEvent.OnTodoCLick(todo)) },
                onLongClick = { isContextMenuVisible = true }
            )


            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            },
        colors = CardDefaults.cardColors(contentColor = Color.Black, containerColor = Color.Black)
    ) {
        Box(
            modifier= Modifier
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = {done->
                            onEvent(TodoListEvent.OnDoneChange(todo,done))
                        }
                    )
                    Text(
                        text = todo.title,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible=false }) {
                DropdownMenuItem(
                    text = {
                           Row {
                               Icon(
                                   imageVector = Icons.Default.Star,
                                    contentDescription = "Add to high priority",
                                   modifier = Modifier.padding(end = 5.dp)
                               )
                               Text(
                                   text = if(todo.isHighPriority){
                                       "Remove from high priority"
                                   }
                                   else {
                                       "Add to high priority"
                                   }
                               )
                           }
                    },
                    onClick = {
                        onEvent(TodoListEvent.OnPriorityChange(todo))
                        isContextMenuVisible=false
                    }
                )
                DropdownMenuItem(
                    text = {
                           Row {
                               Icon(
                                   imageVector = Icons.Default.Edit,
                                   contentDescription = "Edit task",
                                   modifier = Modifier.padding(end = 5.dp)
                               )
                               Text(text = "Edit todo")
                           }
                    },
                    onClick = {
                        onEvent(TodoListEvent.OnTodoCLick(todo))
                        isContextMenuVisible=false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Edit task",
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(text = "Delete todo")
                        }
                    },
                    onClick = {
                        onEvent(TodoListEvent.DeleteTodo(todo))
                        isContextMenuVisible=false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview(){
    TodosTheme {
        TodoItem(
            todo = Todo("Study","Random",false,false) ,
            onEvent = {}
        )
    }
}