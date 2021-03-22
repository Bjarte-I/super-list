package com.example.superlist

import com.example.superlist.models.ListItems

object ListItemsSingleton {
    val singletonListItems: ListItems by lazy { //lazy will only initialize when called the first time. So it will keep its information on multiple calls.
        ListItems(mutableListOf())
    }
}