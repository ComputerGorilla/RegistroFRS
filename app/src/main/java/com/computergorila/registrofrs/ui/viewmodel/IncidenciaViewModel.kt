package com.computergorila.registrofrs.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncidenciaViewModel(private val repository: IncidenciaRepository) : ViewModel() {

    private val _incidenciasDia = mutableStateMapOf<String, Int>()
    val incidenciasDia: Map<String, Int> get() = _incidenciasDia
    fun cargarDatosDelDia(mes: String, dia: Int) {
        viewModelScope.launch {
            val lista = repository.getIncidenciasDelDia(mes, dia)
            _incidenciasDia.clear()
            lista.forEach {
                _incidenciasDia[it.paradaId] = it.numero
            }
        }
    }

    fun modificarIncidencia(mesNombre: String, dia: Int, paradaId: String, cambio: Int) {
        val cantidadActual = _incidenciasDia[paradaId] ?: 0
        val nuevaCantidad = cantidadActual + cambio

        _incidenciasDia[paradaId] = nuevaCantidad

        viewModelScope.launch(Dispatchers.IO) {
            repository.addIncidencia(
                mesNombre = mesNombre,
                dia = dia,
                paradaId = paradaId,
                numero = nuevaCantidad
            )
        }
    }
}