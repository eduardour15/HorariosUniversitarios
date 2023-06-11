package com.example.rutas.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal")
data class Personal(
    @PrimaryKey(autoGenerate = true)
    var idBus: Long,
    var nombre: String,
    var salida: String,
    var llegada: String,
    var telefono: String
)
