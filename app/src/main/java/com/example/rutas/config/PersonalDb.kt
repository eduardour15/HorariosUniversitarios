package com.example.rutas.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rutas.dao.PersonalDao
import com.example.rutas.models.Personal

@Database(
    entities = [Personal::class],
    version = 1
)
abstract class PersonalDb: RoomDatabase() {
    abstract fun personalDao(): PersonalDao
}