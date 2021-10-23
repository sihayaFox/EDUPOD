package com.example.edupodfinal.models

data class Question(
    val id:String? =null,
    val teacherId:String? = null,
    val studentId:String? = null,
    val date:String? = null,
    val question:String? = null,
    val answer:String? = null,
    val answerStatus:Boolean = false,
    val comments:String? = null,
    val teacherName:String? = null,
    val questionTopic:String? = null,
    val docUrl:String? = null,
    val docName:String? = null
)
