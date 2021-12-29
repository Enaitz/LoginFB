package com.example.loginfb

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel :ViewModel(){

    val user = MutableLiveData<String>()

    fun setUser(text: String) {
        user.value = text
    }

}