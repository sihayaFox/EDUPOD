package com.example.edupodfinal.models

data class DailyRecord(
    var dailyRecId:String? = null,
    val userId:String? = null,
    val date:String? = null,
    val startTime:String? = null,
    val endTime:String? =null,
    val className:String? =null,
    val subjectName:String? =null,
    val activityName:String? = null,
    val schoolConId:String? = "1001"
)
