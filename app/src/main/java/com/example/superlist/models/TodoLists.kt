package com.example.superlist.models

import java.io.Serializable

data class TodoLists (
    val todoLists:MutableList<TodoList>
) : Serializable