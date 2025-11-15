package com.tech.chillittasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.core.network.NetworkResult
import com.tech.core.utils.coroutines.IoDispatcher
import com.tech.domain.model.Task
import com.tech.domain.model.TaskStatus
import com.tech.domain.useCase.task.CreateTaskUseCase
import com.tech.domain.useCase.task.GetTasksUseCase
import com.tech.domain.useCase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _tasksState = MutableStateFlow<NetworkResult<List<Task>>>(NetworkResult.Idle)
    val tasksState: StateFlow<NetworkResult<List<Task>>> = _tasksState.asStateFlow()

    private val _createTaskState = MutableStateFlow<NetworkResult<Boolean>>(NetworkResult.Idle)
    val createTaskState: StateFlow<NetworkResult<Boolean>> = _createTaskState.asStateFlow()

    private val _updateTaskState = MutableStateFlow<NetworkResult<Boolean>>(NetworkResult.Idle)
    val updateTaskState: StateFlow<NetworkResult<Boolean>> = _updateTaskState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch(ioDispatcher) {
            getTasksUseCase().collect { result ->
                _tasksState.value = result
            }
        }
    }

    fun createTask(title: String, description: String) {
        viewModelScope.launch(ioDispatcher) {
            val task = Task( title = title, description = description, status = TaskStatus.PENDING)
            createTaskUseCase(task).collect { result ->
                _createTaskState.value = result
                if (result is NetworkResult.Success) loadTasks()
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            updateTaskUseCase(task).collect { result ->
                _updateTaskState.value = result
                if (result is NetworkResult.Success) loadTasks()
            }
        }
    }
}
