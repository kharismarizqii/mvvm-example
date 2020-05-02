package com.kharismarizqii.mvvmtutorial.ui.auth

import androidx.lifecycle.LiveData
import com.kharismarizqii.mvvmtutorial.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}