package com.tech.domain.useCase.task

import com.tech.domain.model.Task
import com.tech.domain.repository.remote.task.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)
}