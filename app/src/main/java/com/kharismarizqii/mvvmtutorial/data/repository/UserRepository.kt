package com.kharismarizqii.mvvmtutorial.data.repository

import com.kharismarizqii.mvvmtutorial.data.db.AppDatabase
import com.kharismarizqii.mvvmtutorial.data.db.entities.User
import com.kharismarizqii.mvvmtutorial.data.network.MyApi
import com.kharismarizqii.mvvmtutorial.data.network.SafeApiRequest
import com.kharismarizqii.mvvmtutorial.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository(private val api: MyApi,
                     private val db: AppDatabase) : SafeApiRequest(){
    suspend fun userLogin(email: String, password: String) : AuthResponse{
        return apiRequest {api.userLogin(email, password)}
    }

    suspend fun userSignUp(
        name: String,
        email: String,
        password: String
    ) : AuthResponse{
        return apiRequest{api.userSignUp(name, email, password)}
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}