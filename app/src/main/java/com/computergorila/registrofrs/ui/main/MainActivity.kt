package com.computergorila.registrofrs.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.computergorila.registrofrs.data.local.AppDatabase
import com.computergorila.registrofrs.data.repository.IncidenciaRepository
import com.computergorila.registrofrs.nav.Destinos
import com.computergorila.registrofrs.ui.screens.DaysScreen
import com.computergorila.registrofrs.ui.screens.MonthsScreen
import com.computergorila.registrofrs.ui.viewmodel.IncidenciaViewModel
import com.computergorila.registrofrs.ui.viewmodel.MonthsViewModel
import com.computergorila.registrofrs.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repository = IncidenciaRepository(db.incidenciaDao())

        setContent {
            AppNavigation(repository)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(repository: IncidenciaRepository) {
    val navController = rememberNavController()

    // Creamos el factory UNA VEZ para usarlo en todas las pantallas
    val factory = ViewModelFactory(repository)

    NavHost(
        navController = navController,
        startDestination = Destinos.MESES
    ) {
        // --- PANTALLA DE MESES ---
        composable(Destinos.MESES) {
            // ERROR CORREGIDO AQUÍ: Ahora pasamos el factory
            val monthsViewModel: MonthsViewModel = viewModel(factory = factory)
            MonthsScreen(
                viewModel = monthsViewModel,
                onMonthClick = { mesSeleccionado ->
                    navController.navigate("dias_screen/$mesSeleccionado")
                }
            )
        }

        // --- PANTALLA DE DÍAS ---
        composable(
            route = Destinos.DIAS,
            arguments = listOf(navArgument("mesNombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val mesNombre = backStackEntry.arguments?.getString("mesNombre") ?: ""
            DaysScreen(
                mesNombre = mesNombre,
                onDayClick = { diaSeleccionado ->
                    navController.navigate("registro_screen/$mesNombre/$diaSeleccionado")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- PANTALLA DE REGISTRO ---
        composable(
            route = Destinos.REGISTRO,
            arguments = listOf(
                navArgument("mesNombre") { type = NavType.StringType },
                navArgument("dia") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val mes = backStackEntry.arguments?.getString("mesNombre") ?: ""
            val dia = backStackEntry.arguments?.getInt("dia") ?: 1

            // Usamos el mismo factory aquí
            val incidenciaViewModel: IncidenciaViewModel = viewModel(factory = factory)

            HomeContent(
                mesNombre = mes,
                dia = dia,
                viewModel = incidenciaViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}