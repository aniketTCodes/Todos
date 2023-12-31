package com.anikettcodes.todos.ui.add_edit_todo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anikettcodes.todos.util.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack:()->Unit,
    viewModel: AddEditTodoViewModel= hiltViewModel()
){
    val snackBarHostState = remember {SnackbarHostState()}
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.PopBackStack->{
                    onPopBackStack()
                }
                is UiEvent.ShowSnackBar->{
                    snackBarHostState.showSnackbar(message = it.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick) }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        containerColor = Color.Black

    ) {
            Column(modifier= Modifier
                .padding(it)
                .fillMaxSize()) {
                TextField(value = viewModel.title, onValueChange = { title->
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(title))
                },
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth()

                    )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(value = viewModel.description, onValueChange = { description->
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(description))
                },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 5
                )
            }
    }
}