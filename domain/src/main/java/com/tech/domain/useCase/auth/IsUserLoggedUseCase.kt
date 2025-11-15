package com.tech.domain.useCase.auth

import com.tech.domain.repository.remote.auth.AuthRepository
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.isUserLogged()
}