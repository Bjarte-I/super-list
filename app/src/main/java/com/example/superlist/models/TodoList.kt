package com.example.superlist.models

import java.io.Serializable

data class TodoList(
    val title:String,
    var progress:Int,
    var listOfTodos:MutableList<Todo>
) : Serializable