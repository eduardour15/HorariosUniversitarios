package com.example.rutas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutas.config.PersonalApp.Companion.db
import com.example.rutas.models.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    val personalList= MutableLiveData<List<Personal>>()
    val parametroBusqueda = MutableLiveData<String>()

    fun iniciar(){
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                db.personalDao().getAll()
            }
        }
    }

    fun buscarPersonal() {
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                db.personalDao().getByName(parametroBusqueda.value!!)
            }
        }
    }
}