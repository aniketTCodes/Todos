package com.anikettcodes.todos.ui.todo_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anikettcodes.todos.ui.theme.DarkBlue
import com.anikettcodes.todos.ui.theme.TodosTheme
import com.anikettcodes.todos.util.Routes
import com.anikettcodes.todos.util.UiEvent

@Composable
fun BottomNavigation(
    onNavigate: (UiEvent.Navigate)->Unit
){
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
            Image(
                imageVector = Icons.Default.Home,
                contentDescription = "home",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .size(30.dp)
                    .clickable {

                        onNavigate(UiEvent.Navigate(Routes.TODO_LIST))
                               },
                colorFilter = ColorFilter.tint(Color.White)

            )
            Image(imageVector = Icons.Default.Star,
                contentDescription = "high priority",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(30.dp)
                    .clickable {
                        onNavigate(UiEvent.Navigate(Routes.HIGH_PRIORITY_TODO_LIST))
                               },
                colorFilter = ColorFilter.tint(Color.White)

            )
    }
}


@Preview
@Composable
fun BottomNavigationPreview(){
    TodosTheme {
        BottomNavigation(onNavigate = {{}})
    }
}
