package com.kharismarizqii.mvvmtutorial.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.kharismarizqii.mvvmtutorial.data.repository.UserRepository
import com.kharismarizqii.mvvmtutorial.util.Coroutines

class AuthViewModel: ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener : AuthListener? = null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email Or Password")
            return
        }
        //success
        Coroutines.main{
            val response = UserRepository().userLogin(email!!, password!!)
            if (response.isSuccessful){
                authListener?.onSuccess(response.body()?.user!!)
            } else{
                authListener?.onFailure("Error Code: ${response.code()}")
            }
        }
    }
}