package com.tech.data.dataSource.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import com.tech.core.utils.coroutines.awaitTask
import com.tech.data.model.UserDTO
import com.tech.data.model.toEntity
import javax.inject.Inject



class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String): UserDTO? {
        val result = awaitTask { firebaseAuth.signInWithEmailAndPassword(email, password) }
        return result.user?.toEntity()
    }

    override suspend fun register(name: String, email: String, password: String): UserDTO? {
        val result = awaitTask { firebaseAuth.createUserWithEmailAndPassword(email, password) }

        // Actualizar displayName correctamente
        result.user?.updateProfile(
            userProfileChangeRequest {
                displayName = name
            }
        )

        return result.user?.toEntity()
    }

    override suspend fun logout(): Boolean {
        firebaseAuth.signOut()
        return true
    }

    override fun isUserLogged(): Boolean = firebaseAuth.currentUser != null
}