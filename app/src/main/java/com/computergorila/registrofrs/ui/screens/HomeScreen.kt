package com.computergorila.registrofrs.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.computergorila.registrofrs.data.model.Parada
import com.computergorila.registrofrs.ui.main.HomeContent
import com.computergorila.registrofrs.ui.viewmodel.IncidenciaViewModel
import com.computergorila.registrofrs.utils.DateUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: IncidenciaViewModel) {

    val paradas = listOf(
        Parada("P20", "CIUTADELLA"),
        Parada("P21", "WELLINGTON")
    )

    val fecha = DateUtils.today()

    HomeContent(
        paradas = paradas,
        onAddIncidencia = { paradaId, numero ->
            viewModel.addIncidencia(paradaId, fecha, numero)
        },
        onDeleteIncidencia = { paradaId, numero ->
            viewModel.deleteIncidencia(paradaId, fecha, numero)
        }
    )
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