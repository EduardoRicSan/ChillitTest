package com.tech.chillittasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.core.network.NetworkResult
import com.tech.core.utils.coroutines.IoDispatcher
import com.tech.domain.model.User
import com.tech.domain.useCase.auth.IsUserLoggedUseCase

import com.tech.domain.useCase.auth.LoginUseCase
import com.tech.domain.useCase.auth.LogoutUseCase
import com.tech.domain.useCase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isUserLoggedUseCase: IsUserLoggedUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _loginState = MutableStateFlow<NetworkResult<User?>>(NetworkResult.Idle)
    val loginState: StateFlow<NetworkResult<User?>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<NetworkResult<User?>>(NetworkResult.Idle)
    val registerState: StateFlow<NetworkResult<User?>> = _registerState.asStateFlow()

    private val _logoutState = MutableStateFlow<NetworkResult<Boolean>>(NetworkResult.Idle)
    val logoutState: StateFlow<NetworkResult<Boolean>> = _logoutState.asStateFlow()

    val isUserLogged: Boolean
        get() = isUserLoggedUseCase()

    fun login(email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            _loginState.value = NetworkResult.Loading
            loginUseCase(email, password).collect { result ->
                _loginState.value = result
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            _registerState.value = NetworkResult.Loading
            registerUseCase(name, email, password).collect { result ->
                _registerState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch(ioDispatcher) {
            _logoutState.value = NetworkResult.Loading
            logoutUseCase().collect { result ->
                _logoutState.value = result
            }
        }
    }

    fun resetLogoutState() {
        _logoutState.value = NetworkResult.Idle
    }
}

