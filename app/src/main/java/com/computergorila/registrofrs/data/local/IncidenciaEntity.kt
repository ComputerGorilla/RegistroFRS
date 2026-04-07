package com.computergorila.registrofrs.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "incidencias",
    foreignKeys = [
        ForeignKey(
            entity = MesEntity::class,
            parentColumns = ["nombre"],
            childColumns = ["mesNombre"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IncidenciaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mesNombre: String,
    val dia: Int,
    val paradaId: String,
    val numero: Int
)