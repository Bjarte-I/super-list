package com.example.superlist.models

import java.io.Serializable

class Todo (
        val title:String,
        var isChecked: Boolean
        ) : Serializable