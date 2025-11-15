package com.tech.core.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface CoroutineDispatchersProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}

suspend fun <T> awaitTask(taskProvider: () -> com.google.android.gms.tasks.Task<T>): T =
    suspendCoroutine { cont ->
        val task = taskProvider()
        task.addOnSuccessListener { cont.resume(it) }
        task.addOnFailureListener { cont.resumeWithException(it) }
    }