package com.kharismarizqii.mvvmtutorial.data.network.responses

import com.kharismarizqii.mvvmtutorial.data.db.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)