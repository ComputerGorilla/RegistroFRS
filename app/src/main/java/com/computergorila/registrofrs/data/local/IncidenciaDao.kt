package com.computergorila.registrofrs.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface IncidenciaDao {
    @Insert
    fun insert(incidencia: IncidenciaEntity)

    @Query("SELECT * FROM incidencias WHERE fecha = :fecha")
    suspend fun getByFecha(fecha: String): List<IncidenciaEntity>

    @Query("SELECT * FROM incidencias WHERE paradaId = :paradaId AND fecha = :fecha")
    suspend fun getByParadaAndFecha(paradaId: String, fecha: String): List<IncidenciaEntity>

    @Query(""" DELETE FROM incidencias WHERE paradaId = :paradaId AND fecha = :fecha AND numero = :numero""")
    suspend fun deleteByParams(paradaId: String, fecha: String, numero: Int)
    @Delete
    suspend fun delete(incidencia: IncidenciaEntity)
}