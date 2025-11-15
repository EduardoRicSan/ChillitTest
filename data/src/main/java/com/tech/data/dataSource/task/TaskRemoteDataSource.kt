package com.tech.data.dataSource.task

import com.google.firebase.firestore.DocumentReference
import com.tech.data.model.TaskDTO

interface TaskRemoteDataSource {
    suspend fun createTask(task: TaskDTO): String // devuelve el ID generado
    suspend fun updateTask(task: TaskDTO)
    suspend fun getTasks(): List<TaskDTO>
}