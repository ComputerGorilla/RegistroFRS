package com.computergorila.registrofrs.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="incidencias")
data class IncidenciaEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val paradaId: String,
    val fecha: String,
    val numero: Int
)