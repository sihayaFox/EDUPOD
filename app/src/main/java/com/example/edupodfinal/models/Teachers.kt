package com.example.edupodfinal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teachers(
    val userId:String? = null,
    val subjectName:String? = null,
    val extraCaricular:String? = null,
    val educationZone:String? = null
): Parcelable
