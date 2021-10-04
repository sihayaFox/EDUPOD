package com.example.edupodfinal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class School(
    val schoolConId:String? = null,
    val schoolName:String? = null
):Parcelable {
    override fun toString(): String {
        return schoolName.toString()
    }
}
