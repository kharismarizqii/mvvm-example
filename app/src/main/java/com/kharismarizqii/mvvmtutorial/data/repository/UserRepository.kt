package com.kharismarizqii.mvvmtutorial.data.repository

import com.kharismarizqii.mvvmtutorial.data.network.MyApi
import com.kharismarizqii.mvvmtutorial.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository {
    suspend fun userLogin(email: String, password: String) : Response<AuthResponse>{
        return MyApi().userLogin(email, password)
    }
}