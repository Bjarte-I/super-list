package com.example.superlist.models

import java.io.Serializable

data class ListItem(
    val title:String,
    var listOfTodos:MutableList<DetailsTodoItem>
) : Serializable