package com.tech.core.network

import com.tech.core.utils.coroutines.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)

    try {
        val data = withContext(dispatcher) { apiCall() }
        emit(NetworkResult.Success(data))
    } catch (e: Exception) {
        emit(NetworkResult.Error(e.message ?: "Unknown error", e))
    }
}