package com.tech.data.model

import com.google.firebase.auth.FirebaseUser


data class UserDTO(
    val uid: String,
    val email: String?,
    val displayName: String?
)

fun FirebaseUser.toEntity(): UserDTO =
    UserDTO(
        uid = uid,
        email = email,
        displayName = displayName
    )