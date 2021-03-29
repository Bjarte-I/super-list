package com.example.superlist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo (val title:String, var isChecked: Boolean) : Parcelable {
        override fun toString(): String {
                return "$title, $isChecked"
                }
        }