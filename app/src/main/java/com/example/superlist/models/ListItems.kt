package com.example.superlist.models

import java.io.Serializable

data class ListItems (
    val ListItems:MutableList<ListItem>
) : Serializable