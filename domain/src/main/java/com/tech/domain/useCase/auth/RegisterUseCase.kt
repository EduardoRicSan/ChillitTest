package com.tech.domain.useCase.auth

import com.tech.domain.repository.remote.auth.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String) =
        repository.register(name, email, password)
}