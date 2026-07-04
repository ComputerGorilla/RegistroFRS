package com.computergorila.registrofrs.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.computergorila.registrofrs.data.model.Parada
import com.computergorila.registrofrs.ui.viewmodel.IncidenciaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    mesNombre: String,
    dia: Int,
    viewModel: IncidenciaViewModel?,
    onBack: () -> Unit
) {
    // Cargamos los datos del día al iniciar
    LaunchedEffect(mesNombre, dia) {
        viewModel?.cargarDatosDelDia(mesNombre, dia)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Registro de Incidencias", fontSize = 18.sp)
                        Text("$mesNombre - Día $dia", fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        val listaParadas = getListaParadas()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaParadas) { parada ->
                // Obtenemos la cantidad del ViewModel o 0 si es Preview
                val cantidad = viewModel?.incidenciasDia?.get(parada.id) ?: 0

                ParadaRow(
                    parada = parada,
                    cantidad = cantidad,
                    onIncrement = { viewModel?.modificarIncidencia(mesNombre, dia, parada.id, 1) },
                    onDecrement = { viewModel?.modificarIncidencia(mesNombre, dia, parada.id, -1) }
                )
            }
        }
    }
}

@Composable
fun ParadaRow(
    parada: Parada,
    cantidad: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = parada.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "ID: ${parada.id}", color = Color.Gray, fontSize = 12.sp)
            }

            // Controles de contador
            Row(verticalAlignment = Alignment.CenterVertically) {

                FilledIconButton(
                    onClick = onDecrement,
                    modifier = Modifier.size(35.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE57373))
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Restar", tint = Color.White)
                }

                Text(
                    text = cantidad.toString(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (cantidad > 0) MaterialTheme.colorScheme.primary else Color.Black
                )

                FilledIconButton(
                    onClick = onIncrement,
                    modifier = Modifier.size(35.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF81C784))
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Sumar", tint = Color.White)
                }
            }
        }
    }
}
fun getListaParadas(): List<Parada> {
    return listOf(
        Parada("P30", "EST DE ST ADRIÀ", 0),
        Parada("P29", "PORT FÒRUM", 0),
        Parada("P10", "CAMPUS DIAGONAL", 0),
        Parada("P09", "FÒRUM", 0),
        Parada("P08", "EL MARESME", 0),
        Parada("P07", "SELVA DE MAR", 0),
        Parada("P06", "FLUVIÀ", 0),
        Parada("P05", "PERE IV", 0),
        Parada("P04", "CA L'ARANYÓ", 0),
        Parada("P34", "VERDAGUER", 0),
        Parada("P33", "SICILIA", 0),
        Parada("P32", "MONUMENTAL", 0),
        Parada("P31", "GLORIES", 0),
        Parada("P02", "AUDITORI-TNC", 0),
        Parada("P22", "MARINA", 0),
        Parada("P21", "WELLINGTON", 0),
        Parada("P20", "CIUTADELLA-VO", 0),
        Parada("P27", "ALFONS EL MAGNÀNIM", 0),
        Parada("P28", "PARC DE BESÒS", 0),
        Parada("P11", "LA MINA", 0),
        Parada("P13", "LA CATALANA", 0),
        Parada("P14", "ST JOAN BAPTISTA", 0),
        Parada("P16", "ENCANTS DE ST ADRIÀ", 0),
        Parada("P17", "ST ROC", 0),
        Parada("P19", "GORG", 0),
        Parada("P23", "CAN JAUMANDREU", 0),
        Parada("P24", "ESPRONCEDA", 0),
        Parada("P25", "ST MARTÍ DE PROVENÇALS", 0),
        Parada("P26", "BESÒS", 0)
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun HomeContentPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeContent(
                mesNombre = "ABRIL",
                dia = 12,
                viewModel = null, // En la preview pasamos null
                onBack = {  }
            )
        }
    }
}