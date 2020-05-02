package com.kharismarizqii.mvvmtutorial.data.repository

import com.kharismarizqii.mvvmtutorial.data.network.MyApi
import com.kharismarizqii.mvvmtutorial.data.network.SafeApiRequest
import com.kharismarizqii.mvvmtutorial.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository : SafeApiRequest(){
    suspend fun userLogin(email: String, password: String) : AuthResponse{
        return apiRequest {MyApi().userLogin(email, password)}
    }
}