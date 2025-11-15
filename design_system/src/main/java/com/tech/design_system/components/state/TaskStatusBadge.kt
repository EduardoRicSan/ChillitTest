package com.tech.design_system.components.state

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskStatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        "Pendiente" -> MaterialTheme.colorScheme.error
        "En proceso" -> MaterialTheme.colorScheme.tertiary
        "Hecho" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    AssistChip(
        onClick = {},
        modifier = modifier,
        label = { Text(status) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color.copy(alpha = 0.2f),
            labelColor = color
        )
    )
}