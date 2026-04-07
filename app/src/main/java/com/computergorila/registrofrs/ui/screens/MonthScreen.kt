package com.computergorila.registrofrs.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // IMPORTANTE: Asegúrate de importar este 'items'
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.computergorila.registrofrs.ui.item.MonthItem
import com.computergorila.registrofrs.ui.viewmodel.MonthsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthsScreen(
    viewModel: MonthsViewModel,
    onMonthClick: (String) -> Unit
) {
    val meses by viewModel.meses.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var nuevoMesNombre by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Registros Mensuales") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Mes")
            }
        }
    ) { padding ->
        if (meses.isEmpty()) {
            Text(
                "Pulsa + para crear tu primer mes",
                modifier = Modifier.padding(padding).fillMaxSize()
            )
        }

        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(meses) { mes ->
                MonthItem(
                    nombre = mes.nombre,
                    onClick = { onMonthClick(mes.nombre) },
                    onDelete = { viewModel.borrarMes(mes) }
                )
            }
        }

        // Diálogo para crear un mes (ej. ABRIL)
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nuevo Registro Mensual") },
                text = {
                    OutlinedTextField(
                        value = nuevoMesNombre,
                        onValueChange = { nuevoMesNombre = it },
                        label = { Text("Nombre del mes (ej. ABRIL)") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        if (nuevoMesNombre.isNotBlank()) {
                            viewModel.insertarMes(nuevoMesNombre)
                            nuevoMesNombre = ""
                            showDialog = false
                        }
                    }) { Text("Crear") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}