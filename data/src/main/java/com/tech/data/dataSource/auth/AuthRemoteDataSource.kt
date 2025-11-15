package com.tech.data.dataSource.auth

import com.tech.data.model.UserDTO

interface AuthRemoteDataSource {

    suspend fun login(email: String, password: String): UserDTO?

    suspend fun register(name: String, email: String, password: String): UserDTO?

    suspend fun logout(): Boolean

    fun isUserLogged(): Boolean
}