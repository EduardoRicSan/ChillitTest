package com.tech.chillittasks.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tech.chillittasks.presentation.ui.auth.AuthScreen
import com.tech.chillittasks.presentation.ui.main.MainScreen
import com.tech.chillittasks.presentation.ui.splash.SplashScreen
import com.tech.chillittasks.presentation.viewmodel.AuthViewModel
import com.tech.design_system.components.topBar.ChillitTopBar
import com.tech.design_system.core.extension.currentRoute
import com.tech.design_system.core.extension.safeNavigate


// Screen.kt
sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "ChillitTasks")
    object Auth : Screen("auth", "Autenticación")
    object Main : Screen("main", "Inicio")
}

// ChillitNav.kt
@Composable
fun ChillitNav() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            val route = currentRoute(navController)
            val title = when(route) {
                Screen.Auth.route -> Screen.Auth.title
                Screen.Main.route -> Screen.Main.title
                else -> Screen.Splash.title
            }
            ChillitTopBar(title = title)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    viewModel = viewModel,
                    onNavigateToAuth = {
                        navController.safeNavigate(Screen.Auth.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToMain = {
                        navController.safeNavigate(Screen.Main.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Auth.route) {
                AuthScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.safeNavigate(Screen.Main.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Main.route) {
                MainScreen(
                    authViewModel = viewModel,
                    onLogout = {
                        navController.safeNavigate(Screen.Auth.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}