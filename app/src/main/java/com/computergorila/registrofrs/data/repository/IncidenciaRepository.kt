package com.computergorila.registrofrs.data.repository

import com.computergorila.registrofrs.data.local.IncidenciaDao
import com.computergorila.registrofrs.data.local.IncidenciaEntity
import com.computergorila.registrofrs.data.model.Parada

class IncidenciaRepository(private val dao: IncidenciaDao) {
    fun addIncidencia(
        paradaId: String,
        fecha: String,
        numero: Int
    ){
        dao.insert(IncidenciaEntity(paradaId = paradaId, fecha = fecha, numero = numero))
    }

    suspend fun deleteIncidencia(
        paradaId: String,
        fecha: String,
        numero: Int
    ){
        dao.deleteByParams(paradaId = paradaId, fecha = fecha, numero = numero)
    }
    suspend fun getIncidenciasDia(fecha: String) = dao.getByFecha(fecha)
}