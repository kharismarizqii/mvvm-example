package com.kharismarizqii.mvvmtutorial.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.kharismarizqii.mvvmtutorial.data.repository.UserRepository
import com.kharismarizqii.mvvmtutorial.util.ApiException
import com.kharismarizqii.mvvmtutorial.util.Coroutines
import com.kharismarizqii.mvvmtutorial.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener : AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Email Or Password")
            return
        }
        //success
        Coroutines.main{
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }

        }
    }
}