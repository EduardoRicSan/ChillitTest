package com.tech.domain.model

import com.tech.data.model.TaskDTO

data class Task(
    val id: String = "",
    val title: String,
    val description: String,
    val status: TaskStatus = TaskStatus.PENDING
) {
    companion object {
        fun fromDomain(task: Task) = TaskDTO(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status.name
        )
    }
}

enum class TaskStatus {
    PENDING, IN_PROGRESS, DONE
}

fun TaskDTO.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    status = TaskStatus.valueOf(status)
)

fun Task.toDTO() = TaskDTO(
    id = id,
    title = title,
    description = description,
    status = status.name
)

