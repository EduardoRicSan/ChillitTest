package com.tech.domain.useCase.auth

import com.tech.domain.repository.remote.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.logout()
}