package com.example.rutas.repository

import com.example.rutas.dao.PersonalDao
import com.example.rutas.models.Personal

class PersonalRepository(private val personalDao: PersonalDao) {
    suspend fun getAllPersonal(): List<Personal> {
        return personalDao.getAll()
    }

    suspend fun getPersonalById(id: Long): Personal {
        return personalDao.getById(id)
    }

    suspend fun searchPersonalByName(name: String): List<Personal> {
        return personalDao.getByName(name)
    }

    suspend fun insertPersonal(personalList: List<Personal>): List<Long> {
        return personalDao.insert(personalList)
    }

    suspend fun updatePersonal(personal: Personal): Int {
        return personalDao.update(personal)
    }

    suspend fun deletePersonal(personal: Personal): Int {
        return personalDao.delete(personal)
    }

}