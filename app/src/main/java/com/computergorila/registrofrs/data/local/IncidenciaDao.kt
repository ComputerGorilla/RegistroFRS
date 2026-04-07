package com.computergorila.registrofrs.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidenciaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMes(mes: MesEntity)

    @Query("SELECT * FROM meses ORDER BY timestamp DESC")
    fun getMeses(): Flow<List<MesEntity>>

    @Delete
    suspend fun deleteMes(mes: MesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncidencial(incidencia: IncidenciaEntity)

    @Query("SELECT * FROM incidencias WHERE mesNombre = :mesNombre AND dia = :dia")
    suspend fun getIncidenciasByDia(mesNombre: String, dia: Int): List<IncidenciaEntity>

    @Query("SELECT * FROM incidencias WHERE mesNombre = :mesNombre")
    suspend fun getIncidenciasByMes(mesNombre: String): List<IncidenciaEntity>

    @Query("DELETE FROM incidencias WHERE mesNombre = :mesNombre AND dia = :dia AND numero = :numero AND paradaId = :paradaId")
    suspend fun deleteIncidenciaEspecifica(mesNombre: String, dia: Int, numero: Int, paradaId: String)
}