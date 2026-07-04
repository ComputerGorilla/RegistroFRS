package com.computergorila.registrofrs.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MonthItem(
    nombre: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onExport: () -> Unit
) {
    var confirmDelete by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.DateRange, contentDescription = null)
            Spacer(Modifier.width(16.dp))
            Text(text = nombre, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            IconButton(onClick = { onExport() }) {
                Icon(Icons.Default.Share, contentDescription = "Enviar", tint = Color.Blue)
            }
            IconButton(onClick = { confirmDelete = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
            }
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("¿Borrar $nombre?") },
            text = { Text("Se perderán todos los datos registrados para este mes.") },
            confirmButton = {
                TextButton(onClick = { onDelete(); confirmDelete = false }) {
                    Text("BORRAR", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text("CANCELAR") }
            }
        )
    }
}

@Preview(showBackground = true, name = "Item Individual")
@Composable
fun MonthItemPreview() {
    MaterialTheme {
        MonthItem(
            nombre = "ABRIL",
            onClick = { /* No hace nada en preview */ },
            onDelete = { /* nada de nada */ },
            onExport = { /* No hace nada en preview */ }
        )
    }
}