package com.computergorila.registrofrs.data.model

import androidx.room.Entity

@Entity
data class ParadaEntity(
    val id: String,
    val nombre: String,
    val totalIncidencias: Int
)
