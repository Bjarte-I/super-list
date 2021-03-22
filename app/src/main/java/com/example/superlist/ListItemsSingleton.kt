package com.example.superlist

import com.example.superlist.models.ListItems

object ListItemsSingleton {
    val singletonListItems: ListItems by lazy {
        ListItems(mutableListOf())
    }
}