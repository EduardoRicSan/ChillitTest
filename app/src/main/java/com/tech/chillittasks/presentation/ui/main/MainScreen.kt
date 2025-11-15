package com.tech.chillittasks.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tech.chillittasks.presentation.viewmodel.AuthViewModel
import com.tech.chillittasks.presentation.viewmodel.TaskViewModel
import com.tech.core.network.NetworkResult
import com.tech.design_system.components.button.PrimaryButton
import com.tech.design_system.components.loader.ChillitLoading
import com.tech.design_system.components.textField.ChillitTextField
import com.tech.domain.model.Task
import com.tech.domain.model.TaskStatus


@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    viewModel: TaskViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val tasksState by viewModel.tasksState.collectAsState()
    val createState by viewModel.createTaskState.collectAsState()
    val updateState by viewModel.updateTaskState.collectAsState()
    val logoutState by authViewModel.logoutState.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 100.dp) // espacio para el logout
        ) {

            // Inputs para nueva tarea
            ChillitTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = "Título",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChillitTextField(
                value = newDescription,
                onValueChange = { newDescription = it },
                label = "Descripción",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = "Agregar",
                modifier = Modifier.fillMaxWidth()
            ) {
                if (newTitle.isNotBlank()) {
                    viewModel.createTask(newTitle, newDescription)
                    newTitle = ""
                    newDescription = ""
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Loader o error al crear tarea
            when (createState) {
                is NetworkResult.Loading -> ChillitLoading()
                is NetworkResult.Error -> Text(
                    (createState as NetworkResult.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            when (tasksState) {
                is NetworkResult.Loading -> ChillitLoading()
                is NetworkResult.Error -> Text(
                    (tasksState as NetworkResult.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                is NetworkResult.Success -> {
                    val tasks = (tasksState as NetworkResult.Success<List<Task>>).data

                    if (tasks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay tareas disponibles",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tasks) { task ->
                                TaskCard(task = task) { updatedTask ->
                                    viewModel.updateTask(updatedTask)
                                }
                            }
                        }
                    }
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Loader o error al actualizar tarea
            when (updateState) {
                is NetworkResult.Loading -> ChillitLoading()
                is NetworkResult.Error -> Text(
                    (updateState as NetworkResult.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                else -> {}
            }
        }

        // Botón de logout pegado al bottom
        PrimaryButton(
            text = "Cerrar sesión",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            authViewModel.logout()
        }

        // Manejo seguro del logout para evitar loop
        LaunchedEffect(logoutState) {
            if (logoutState is NetworkResult.Success) {
                onLogout()
                authViewModel.resetLogoutState() // vuelve a Idle
            }
        }
    }
}




@Composable
fun TaskCard(
    task: Task,
    onUpdateTask: (Task) -> Unit
) {
    val statusColor = when (task.status) {
        TaskStatus.PENDING -> Color(0xFFFFC107)
        TaskStatus.IN_PROGRESS -> Color(0xFF03A9F4)
        TaskStatus.DONE -> Color(0xFF4CAF50)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val nextStatus = when (task.status) {
                    TaskStatus.PENDING -> TaskStatus.IN_PROGRESS
                    TaskStatus.IN_PROGRESS -> TaskStatus.DONE
                    TaskStatus.DONE -> TaskStatus.PENDING
                }
                onUpdateTask(task.copy(status = nextStatus))
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
            }
            Text(
                text = task.status.name.replace("_", " "),
                style = MaterialTheme.typography.bodySmall,
                color = statusColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}







