package com.example.rutas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import java.util.UUID

@Entity(tableName = "personal")
data class Personal(
    @PrimaryKey
    var idBus: String = UUID.randomUUID().toString(),
    var nombre: String = "",
    var salida: String = "",
    var llegada: String = "",
    var telefono: String = ""
)
