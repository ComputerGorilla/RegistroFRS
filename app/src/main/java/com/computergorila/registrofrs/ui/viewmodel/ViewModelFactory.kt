package com.computergorila.registrofrs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.computergorila.registrofrs.data.repository.IncidenciaRepository

class ViewModelFactory(private val repository: IncidenciaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonthsViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(IncidenciaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IncidenciaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}