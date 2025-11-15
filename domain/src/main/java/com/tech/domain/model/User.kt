package com.tech.domain.model

import com.tech.data.model.UserDTO


data class User(
    val uid: String? = "",
    val email: String? = "",
    val displayName: String? = ""
)

fun UserDTO.toUser() = User(uid, email, displayName)
