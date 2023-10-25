package com.anikettcodes.todos

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anikettcodes.todos.ui.add_edit_todo.AddEditTodoScreen
import com.anikettcodes.todos.ui.theme.TodosTheme
import com.anikettcodes.todos.ui.todo_list.HighPriorityTodoListScreen
import com.anikettcodes.todos.ui.todo_list.TodoListScreen
import com.anikettcodes.todos.ui.todo_list.TodoListViewModel
import com.anikettcodes.todos.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val todoViewModel:TodoListViewModel by viewModels()
    private lateinit var permissionLauncher:ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher=registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            todoViewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
        setContent {
            TodosTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.TODO_LIST){
                        composable(Routes.TODO_LIST){
                            TodoListScreen(onNavigate = {
                                navController.navigate(it.route)
                            },todoViewModel,todoViewModel.state)
                        }
                        composable(
                            Routes.ADD_EDIT_TODO+"?todoId={todoId}",
                            arguments = listOf(navArgument(name="todoId"){
                                type= NavType.IntType
                                defaultValue=-1
                            })
                            ){
                            AddEditTodoScreen(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }

                        composable(Routes.HIGH_PRIORITY_TODO_LIST){
                            HighPriorityTodoListScreen(onNavigate = {
                                navController.navigate(it.route)
                            },todoViewModel,todoViewModel.state)
                        }
                    }
                }
            }
        }
    }
}
