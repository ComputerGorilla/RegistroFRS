package com.computergorila.registrofrs.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meses")
data class MesEntity(
    @PrimaryKey val nombre: String,
    val timestamp: Long = System.currentTimeMillis()
)