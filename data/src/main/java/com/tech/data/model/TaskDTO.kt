package com.tech.data.model

import com.google.android.gms.tasks.Task

data class TaskDTO(
    var id: String = "", // mutable para asignar el ID generado
    val title: String = "",
    val description: String = "",
    val status: String = ""
)