package com.example.rutas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rutas.models.Personal
import com.google.firebase.firestore.FirebaseFirestore


class MainViewModel : ViewModel() {
    val personalList = MutableLiveData<List<Personal>>()
    val parametroBusqueda = MutableLiveData<String>()

    private val db = FirebaseFirestore.getInstance()
    private val personalCollection = db.collection("personal")

    init {
        iniciar()
    }

    fun iniciar() {
        personalCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Error al obtener los datos del personal: ${error.message}")
                return@addSnapshotListener
            }

            val personalData = snapshot?.toObjects(Personal::class.java)
            personalList.value = personalData
        }
    }

    fun buscarPersonal() {
        val busqueda = parametroBusqueda.value ?: ""

        personalCollection.whereEqualTo("nombre", busqueda)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val personalData = querySnapshot.toObjects(Personal::class.java)
                personalList.value = personalData
            }
            .addOnFailureListener { error ->
                Log.e("Firestore", "Error al buscar el personal: ${error.message}")
            }
    }
}

