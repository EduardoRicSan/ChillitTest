package com.tech.design_system.components.searchBar

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ChillitSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Buscar..."
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true
    )
}