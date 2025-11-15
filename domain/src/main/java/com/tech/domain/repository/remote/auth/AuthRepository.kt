package com.tech.domain.repository.remote.auth

import com.tech.core.network.NetworkResult
import com.tech.core.network.safeApiCall
import com.tech.core.utils.coroutines.IoDispatcher
import com.tech.domain.model.User
import com.tech.domain.model.toUser
import com.tech.data.dataSource.auth.AuthRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remote: AuthRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
)  {

    suspend fun login(email: String, password: String): Flow<NetworkResult<User?>> =
        safeApiCall(ioDispatcher) {
            remote.login(email, password)?.toUser()
        }

    suspend fun register(name: String, email: String, password: String): Flow<NetworkResult<User?>> =
        safeApiCall(ioDispatcher) {
            remote.register(name, email, password)?.toUser()
        }

    suspend fun logout(): Flow<NetworkResult<Boolean>> =
        safeApiCall(ioDispatcher) {
            remote.logout()
        }

     fun isUserLogged(): Boolean = remote.isUserLogged()
}