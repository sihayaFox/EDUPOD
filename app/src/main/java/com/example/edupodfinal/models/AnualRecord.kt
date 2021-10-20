package com.example.edupodfinal.models

data class AnualRecord(
    var id:String? = null,
    val userId:String? = null,
    val term:String? = null,
    val grade:String? = null,
    val competency:String? =null,
    val competencyLevel:String? =null,
    val relevant_activity:String? =null,
    val targetDate:String? = null,
    val qualityInputs:String? = null,
    val assesment:String? = null,
    val caricularActivity:String? = null,
    val proposedActivity:String? = null,
    val schoolConId:String? = "1001"
)
