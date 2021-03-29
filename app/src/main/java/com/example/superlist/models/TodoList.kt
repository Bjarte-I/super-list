package com.example.superlist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoList(val title:String, var listOfTodos:MutableList<Todo>) : Parcelable {
    override fun toString(): String {
        var todos = ""
        for(todo in listOfTodos) {
            todos += "$todo, "
        }
        return "$title, $todos"
    }
}