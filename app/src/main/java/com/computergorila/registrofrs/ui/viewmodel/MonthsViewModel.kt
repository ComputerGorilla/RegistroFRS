package com.computergorila.registrofrs.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.computergorila.registrofrs.data.local.MesEntity
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import com.computergorila.registrofrs.main.ExcelExporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MonthsViewModel(private val repository: IncidenciaRepository) : ViewModel() {

    val meses: Flow<List<MesEntity>> = repository.getAllMeses()

    fun insertarMes(nombre: String) {
        viewModelScope.launch {
            repository.addMes(MesEntity(nombre = nombre.uppercase()))
        }
    }

    fun borrarMes(mes: MesEntity) {
        viewModelScope.launch {
            repository.deleteMes(mes)
        }
    }

    fun exportarDatosMes(context: Context, mesNombre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Buscamos los datos en la BD
            val listaIncidencias = repository.getIncidenciasDeTodoElMes(mesNombre)

            withContext(Dispatchers.Main) {
                ExcelExporter.exportarMes(context, mesNombre, listaIncidencias)
            }
        }
    }

}