package com.example.superlist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Todo (
        val title:String,
        var isChecked: Boolean
        ) : Parcelable