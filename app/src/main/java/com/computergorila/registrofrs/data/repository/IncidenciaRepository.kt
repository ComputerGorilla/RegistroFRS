package com.computergorila.registrofrs.data.repository

import com.computergorila.registrofrs.data.local.IncidenciaDao
import com.computergorila.registrofrs.data.local.IncidenciaEntity
import com.computergorila.registrofrs.data.local.MesEntity
import kotlinx.coroutines.flow.Flow

class IncidenciaRepository(private val dao: IncidenciaDao) {
    fun getAllMeses(): Flow<List<MesEntity>> = dao.getMeses()
    suspend fun addMes(mes: MesEntity) {
        dao.insertMes(mes)
    }
    suspend fun deleteMes(mes: MesEntity) {
        dao.deleteMes(mes)
    }

    suspend fun addIncidencia(
        mesNombre: String,
        dia: Int,
        paradaId: String,
        numero: Int
    ) {
        val nuevaIncidencia = IncidenciaEntity(
            mesNombre = mesNombre,
            dia = dia,
            paradaId = paradaId,
            numero = numero
        )
        dao.insertIncidencial(nuevaIncidencia)
    }

    suspend fun deleteIncidencia(
        mesNombre: String,
        dia: Int,
        paradaId: String,
        numero: Int
    ) {
        dao.deleteIncidenciaEspecifica(mesNombre, dia, numero, paradaId)
    }

    suspend fun getIncidenciasDelDia(mesNombre: String, dia: Int): List<IncidenciaEntity> {
        return dao.getIncidenciasByDia(mesNombre, dia)
    }

    suspend fun getIncidenciasDeTodoElMes(mesNombre: String): List<IncidenciaEntity> {
        return dao.getIncidenciasByMes(mesNombre)
    }
}