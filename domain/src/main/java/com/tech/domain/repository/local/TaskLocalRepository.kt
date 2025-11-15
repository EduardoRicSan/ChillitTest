package com.tech.domain.repository.local

import com.tech.domain.model.entities.TaskEntity
import javax.inject.Inject

interface TaskLocalDataSource {
    suspend fun getTasks(): List<TaskEntity>
    suspend fun saveTasks(tasks: List<TaskEntity>)
    suspend fun saveTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
}

class TaskLocalDataSourceImpl @Inject constructor(
    private val dao: TaskDao
) : TaskLocalDataSource {
    override suspend fun getTasks(): List<TaskEntity> = dao.getAllTasks()
    override suspend fun saveTasks(tasks: List<TaskEntity>) = dao.insertTasks(tasks)
    override suspend fun saveTask(task: TaskEntity) = dao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)
}