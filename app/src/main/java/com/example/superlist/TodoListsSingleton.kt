package com.example.superlist

import com.example.superlist.models.TodoLists

object TodoListsSingleton {
    val SINGLETON_TODO_LISTS: TodoLists by lazy { //lazy will only initialize when called the first time. So it will keep its information on multiple calls.
        TodoLists(mutableListOf())
    }
}