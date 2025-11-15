package com.tech.domain.useCase.task

import com.tech.domain.repository.remote.task.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getTasks()
}