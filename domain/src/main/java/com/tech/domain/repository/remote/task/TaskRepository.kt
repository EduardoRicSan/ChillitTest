package com.tech.domain.repository.remote.task

import com.tech.core.network.NetworkResult
import com.tech.core.network.NetworkUtils
import com.tech.core.utils.coroutines.IoDispatcher
import com.tech.data.dataSource.task.TaskRemoteDataSource
import com.tech.domain.model.Task
import com.tech.domain.model.entities.TaskEntity
import com.tech.domain.model.toDomain
import com.tech.domain.repository.local.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val remote: TaskRemoteDataSource,
    private val local: TaskLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkUtils: NetworkUtils
) {

    fun getTasks(): Flow<NetworkResult<List<Task>>> = flow {
        emit(NetworkResult.Loading)
        if (networkUtils.isOnline()) {
            try {
                val tasks = remote.getTasks().map { it.toDomain() }
                // Actualizamos cache local
                local.saveTasks(tasks.map { TaskEntity.fromDomain(it) })
                emit(NetworkResult.Success(tasks))
            } catch (e: Exception) {
                // Si falla, intentamos leer local
                val cached = local.getTasks().map { it.toDomain() }
                if (cached.isNotEmpty()) {
                    emit(NetworkResult.Success(cached))
                } else {
                    emit(NetworkResult.Error(e.localizedMessage ?: "Error desconocido"))
                }
            }
        } else {
            // Offline: solo leemos cache local
            val cached = local.getTasks().map { it.toDomain() }
            emit(NetworkResult.Success(cached))
        }
    }.flowOn(ioDispatcher)

    fun createTask(task: Task): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading)

        val taskWithId = if (task.id.isBlank()) task.copy(id = "") else task
        val taskDTO = Task.fromDomain(taskWithId)
        val taskEntity = TaskEntity.fromDomain(taskWithId)

        try {
            val generatedId = remote.createTask(taskDTO) // devuelve solo el ID
            val localEntity = taskEntity.copy(id = generatedId)
            local.saveTask(localEntity)
            emit(NetworkResult.Success(true))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }.flowOn(ioDispatcher)


    fun updateTask(task: Task): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading)

        if (task.id.isBlank()) {
            emit(NetworkResult.Error("Task ID is required for update"))
            return@flow
        }

        val taskDTO = Task.fromDomain(task)
        val taskEntity = TaskEntity.fromDomain(task)

        try {
            remote.updateTask(taskDTO)
            local.updateTask(taskEntity)
            emit(NetworkResult.Success(true))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }.flowOn(ioDispatcher)




}


