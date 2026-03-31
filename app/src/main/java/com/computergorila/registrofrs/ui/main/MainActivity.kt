package com.computergorila.registrofrs.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.computergorila.registrofrs.data.local.AppDatabase
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import com.computergorila.registrofrs.ui.screens.HomeScreen
import com.computergorila.registrofrs.ui.viewmodel.IncidenciaViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repository = IncidenciaRepository(db.incidenciaDao())
        val viewModel = IncidenciaViewModel(repository)

        setContent {
            HomeScreen(viewModel)
        }
    }
}