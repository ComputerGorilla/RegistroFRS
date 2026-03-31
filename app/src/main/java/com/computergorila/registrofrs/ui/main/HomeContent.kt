package com.computergorila.registrofrs.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.computergorila.registrofrs.data.model.Parada

@Composable
fun HomeContent(
    paradas: List<Parada>,
    onAddIncidencia: (paradaId: String, numero: Int) -> Unit,
    onDeleteIncidencia: (paradaId: String, numero: Int) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        paradas.forEach { parada ->
            var numero by remember { mutableStateOf("") }

            Text(
                text = parada.nombre,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    modifier = Modifier.weight(3f),
                    label = { Text("Nº Incidencia") },
                    singleLine = true
                )

                IconButton(
                    onClick = {
                        if (numero.isNotEmpty()) {
                            onAddIncidencia(parada.id, numero.toInt())
                            numero = ""
                        }
                    },
                    modifier = Modifier.weight(1.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = {
                        if (numero.isNotEmpty()) {
                            onDeleteIncidencia(parada.id, numero.toInt())
                            numero = ""
                        }
                    },
                    modifier = Modifier.weight(1.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeContent(
        paradas = listOf(
            Parada("P20", "CIUTADELLA"),
            Parada("P21", "WELLINGTON")
        ),
        onAddIncidencia = { _, _ -> },
        onDeleteIncidencia = { _, _ -> }
    )
}