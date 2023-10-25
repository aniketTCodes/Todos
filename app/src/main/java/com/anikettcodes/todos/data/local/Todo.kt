package com.anikettcodes.todos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title:String,
    val description:String?,
    val isDone:Boolean,
    val isHighPriority:Boolean,
    @PrimaryKey val id:Int?=null
)
