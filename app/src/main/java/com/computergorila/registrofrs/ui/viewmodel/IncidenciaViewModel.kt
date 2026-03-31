package com.computergorila.registrofrs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import kotlinx.coroutines.launch

class IncidenciaViewModel(private val repository: IncidenciaRepository) : ViewModel() {

    fun addIncidencia(paradaId: String, fecha: String, numero: Int) {
        viewModelScope.launch {
            repository.addIncidencia(paradaId, fecha, numero)
        }
    }
    fun deleteIncidencia(paradaId: String, fecha: String, numero: Int) {
        viewModelScope.launch {
            repository.deleteIncidencia(paradaId, fecha, numero)
        }
    }
}