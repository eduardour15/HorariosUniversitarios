package com.example.rutas.repository

import com.example.rutas.dao.PersonalDao
import com.example.rutas.firestore.PersonalFireStore
import com.example.rutas.models.Personal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PersonalRepository(private val personalDao: PersonalDao) {
    private val db: FirebaseFirestore = PersonalFireStore.db

    suspend fun getAllPersonal(): List<Personal> {
        return withContext(Dispatchers.IO) {
            val snapshot = db.collection("personal").get().await()
            snapshot.toObjects(Personal::class.java)
        }
    }

    suspend fun getPersonalById(id: String): Personal {
        return withContext(Dispatchers.IO) {
            val snapshot = db.collection("personal").document(id).get().await()
            snapshot.toObject(Personal::class.java)!!
        }
    }

    suspend fun searchPersonalByName(name: String): List<Personal> {
        return withContext(Dispatchers.IO) {
            val snapshot = db.collection("personal").whereEqualTo("nombre", name).get().await()
            snapshot.toObjects(Personal::class.java)
        }
    }

    suspend fun insertPersonal(personalList: List<Personal>): List<String> {
        return withContext(Dispatchers.IO) {
            val batch = db.batch()
            val documentRefs = mutableListOf<String>()
            personalList.forEach { personal ->
                val docRef = db.collection("personal").document(personal.idBus)
                documentRefs.add(docRef.id)
                batch.set(docRef, personal)
            }
            batch.commit().await()
            documentRefs
        }
    }

    suspend fun updatePersonal(personal: Personal): Int {
        return withContext(Dispatchers.IO) {
            val docRef = db.collection("personal").document(personal.idBus)
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                docRef.set(personal).await()
                1
            } else {
                0
            }
        }
    }

    suspend fun deletePersonal(personal: Personal): Int {
        return withContext(Dispatchers.IO) {
            val docRef = db.collection("personal").document(personal.idBus)
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                docRef.delete().await()
                1
            } else {
                0
            }
        }
    }
}