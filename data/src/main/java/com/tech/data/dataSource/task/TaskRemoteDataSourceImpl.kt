package com.tech.data.dataSource.task

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tech.data.model.TaskDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRemoteDataSource {

    private val collection = firestore.collection("tasks")

    override suspend fun createTask(task: TaskDTO): String {
        // Si no tiene ID, generamos uno en Firestore
        val docRef = if (task.id.isBlank()) collection.document() else collection.document(task.id)
        task.id = docRef.id
        docRef.set(task).await()
        return docRef.id
    }

    override suspend fun updateTask(task: TaskDTO) {
        require(task.id.isNotBlank()) { "Task ID is required for update" }
        collection.document(task.id).set(task).await()
    }

    override suspend fun getTasks(): List<TaskDTO> {
        val snapshot = collection
            .orderBy("title", Query.Direction.ASCENDING)
            .get()
            .await()
        return snapshot.toObjects(TaskDTO::class.java)
    }
}





