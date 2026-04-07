package com.computergorila.registrofrs.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import kotlinx.coroutines.launch

class IncidenciaViewModel(private val repository: IncidenciaRepository) : ViewModel() {

    // Mapa para rastrear las incidencias en memoria: Key = paradaId, Value = cantidad
    private val _incidenciasDia = mutableStateMapOf<String, Int>()
    val incidenciasDia: Map<String, Int> get() = _incidenciasDia

    // Cargar datos al entrar en la pantalla del día
    fun cargarDatosDelDia(mes: String, dia: Int) {
        viewModelScope.launch {
            val lista = repository.getIncidenciasDelDia(mes, dia)
            _incidenciasDia.clear()
            lista.forEach {
                _incidenciasDia[it.paradaId] = it.numero
            }
        }
    }

    fun modificarIncidencia(mes: String, dia: Int, paradaId: String, incremento: Int) {
        val actual = _incidenciasDia[paradaId] ?: 0
        val nuevoValor = (actual + incremento).coerceAtLeast(0)

        _incidenciasDia[paradaId] = nuevoValor

        viewModelScope.launch {
            if (nuevoValor > 0) {
                repository.addIncidencia(mes, dia, paradaId, nuevoValor)
            } else {
                repository.deleteIncidencia(mes, dia, paradaId, actual)
            }
        }
    }
}