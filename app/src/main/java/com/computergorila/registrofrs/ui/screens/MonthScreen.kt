package com.computergorila.registrofrs.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.computergorila.registrofrs.ui.item.MonthItem
import com.computergorila.registrofrs.ui.viewmodel.MonthsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthsScreen(
    viewModel: MonthsViewModel,
    onMonthClick: (String) -> Unit
) {
    val context = LocalContext.current
    val meses by viewModel.meses.collectAsState(initial = emptyList())

    MonthsScreenContent(
        meses = meses,
        onMonthClick = onMonthClick,
        onInsertarMes = { nombre -> viewModel.insertarMes(nombre) },
        onBorrarMes = { mesNombre ->
            val mes = meses.find { it.nombre == mesNombre }
            mes?.let { viewModel.borrarMes(it) }
        },
        onExportarMes = { mesNombre ->
            viewModel.exportarDatosMes(context, mesNombre)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthsScreenContent(
    meses: List<com.computergorila.registrofrs.data.local.MesEntity>,
    onMonthClick: (String) -> Unit,
    onInsertarMes: (String) -> Unit,
    onBorrarMes: (String) -> Unit,
    onExportarMes: (String) -> Unit // Nueva función para manejar la exportación
) {
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
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Pulsa + para crear tu primer mes")
            }
        }

        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(meses) { mes ->
                MonthItem(
                    nombre = mes.nombre,
                    onClick = { onMonthClick(mes.nombre) },
                    onDelete = { onBorrarMes(mes.nombre) },
                    onExport = { onExportarMes(mes.nombre) } // Pasamos la acción al Item
                )
            }
        }

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
                            onInsertarMes(nuevoMesNombre)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MonthsScreenPreview() {
    val fakeMonths = listOf(
        com.computergorila.registrofrs.data.local.MesEntity(nombre = "ENERO"),
        com.computergorila.registrofrs.data.local.MesEntity(nombre = "FEBRERO"),
        com.computergorila.registrofrs.data.local.MesEntity(nombre = "MARZO")
    )

    MaterialTheme {
        MonthsScreenContent(
            meses = fakeMonths,
            onMonthClick = {},
            onInsertarMes = {},
            onBorrarMes = {},
            onExportarMes = {}
        )
    }
}