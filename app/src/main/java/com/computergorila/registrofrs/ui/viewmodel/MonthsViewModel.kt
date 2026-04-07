package com.computergorila.registrofrs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.computergorila.registrofrs.data.local.MesEntity
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
}