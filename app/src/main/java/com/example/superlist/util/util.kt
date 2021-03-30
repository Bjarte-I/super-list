package com.example.superlist.util

import com.example.superlist.TodoListHolder
import com.example.superlist.TodoListManager
import com.example.superlist.models.TodoList

fun getPickedTodoList(): TodoList {
    val receivedTodoList = TodoListHolder.PickedTodoList
    receivedTodoList?.let { return it }
    return TodoList("error", mutableListOf())
}

fun getPickedTodoListIndex():Int {
    val todoList = getPickedTodoList()
    val listCollection = TodoListManager.instance.getCollection()
    return listCollection.indexOf(todoList)
}