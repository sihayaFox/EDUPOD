package com.example.edupodfinal.models

data class TermRecord(

    val userId:String? = null,
    val term:String? = null,
    val noOfPeriods:String? = null,
    val competency:String? =null,
    val competencyLevel:String? =null,
    val objectives:String? =null,
    val activities:String? = null,
    val qualityInputs:String? = null,
    val targetDate:String? =null,
    val completedDate:String? = null,
    val schoolConId:String? = "1001"

)
