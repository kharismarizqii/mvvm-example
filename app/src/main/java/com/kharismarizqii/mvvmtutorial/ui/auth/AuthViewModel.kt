package com.kharismarizqii.mvvmtutorial.ui.auth

import android.content.Intent
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
    var name : String? = null
    var passwordConfirm: String? = null

    var authListener : AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUp(view: View){
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

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

    fun onSignUpButtonClick(view: View){
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()){
            authListener?.onFailure("email is required")
            return
        }
        if (password.isNullOrEmpty()){
            authListener?.onFailure("password is required")
            return
        }
        if (passwordConfirm != password){
            authListener?.onFailure("password did not match")
            return
        }

        Coroutines.main{
            try {
                val authResponse = repository.userSignUp(name!!,email!!,password!!)
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