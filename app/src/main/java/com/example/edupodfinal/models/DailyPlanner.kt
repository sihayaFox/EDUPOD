package com.example.edupodfinal.models

data class DailyPlanner(

    var id:String? = null,
    val userId:String? = null,
    val date:String? = null,
    val startTime:String? = null,
    val endTime:String? =null,
    val className:String? =null,
    val subjectName:String? =null,
    val details:String? = null,
    val dayInText:String? = null,
    val schoolConId:String? = "1001"

)
