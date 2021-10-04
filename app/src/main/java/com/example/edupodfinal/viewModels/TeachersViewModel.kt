package com.example.edupodfinal.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edupodfinal.models.User

class TeachersViewModel: ViewModel() {

    private val userObjectMutable = MutableLiveData<User>()
    val userLive: LiveData<User> get() = userObjectMutable

    fun setUserData(user: User?){
        user?.let {
            userObjectMutable.value = it
        }
    }



}