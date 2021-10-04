package com.example.edupodfinal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId: String? = null,
    val fullName:String? = null,
    val userType:Int = 0,
    val regStatus:Int = 0,
    val schoolName:String? = null,
    val schoolConId:String? = null,
    val address:String? = null,
    val admissionId:String? = null,
    val employeeId:String? =null,
    val nic:String? = null,
    val telephone:String? = null,
    val imageUrl:String? = null
):Parcelable
