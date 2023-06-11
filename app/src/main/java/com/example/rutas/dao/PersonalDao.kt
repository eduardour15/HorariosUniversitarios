package com.example.rutas.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rutas.models.Personal

@Dao
interface PersonalDao {
    @Query("SELECT * FROM Personal")
    suspend fun getAll(): List<Personal>

    @Query("SELECT * FROM Personal WHERE idBus = :id")
    suspend fun getById(id: Long): Personal

    @Query("SELECT * FROM Personal WHERE nombre LIKE '%'||:name ||'%' OR  " +
            "llegada LIKE '%' ||:name ||'%' OR salida LIKE '%'||:name ||'%'")
    suspend fun getByName(name:String): List<Personal>

    @Insert
    suspend fun insert(personas: List<Personal>): List<Long>

    @Update
    suspend fun update(personal: Personal): Int

    @Delete
    suspend fun delete(personal: Personal): Int
}
