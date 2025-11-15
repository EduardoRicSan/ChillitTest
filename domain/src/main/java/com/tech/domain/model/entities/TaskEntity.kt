package com.tech.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tech.domain.model.Task
import com.tech.domain.model.TaskStatus

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val status: String // Pendiente / En proceso / Hecho
) {
    companion object {
        fun fromDomain(task: Task): TaskEntity = TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status.name
        )
    }

    fun toDomain(): Task = Task(
        id = id,
        title = title,
        description = description,
        status = TaskStatus.valueOf(status)
    )
}
