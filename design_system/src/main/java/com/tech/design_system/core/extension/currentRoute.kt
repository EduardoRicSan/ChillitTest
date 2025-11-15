package com.tech.design_system.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState

/** Devuelve la ruta actual del NavController */
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

/** Navega de manera segura evitando duplicados y restaurando estado */
fun NavController.safeNavigate(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(this@safeNavigate.graph.startDestinationId) {
            saveState = true
        }
        builder?.invoke(this)
    }
}
