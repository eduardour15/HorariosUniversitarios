package com.example.rutas.viewmodel

import android.app.Person
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutas.config.Constantes
import com.example.rutas.config.PersonalApp.Companion.db
import com.example.rutas.models.Personal
import com.example.rutas.repository.PersonalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioViewModel() : ViewModel() {
    private val personalRepository: PersonalRepository = PersonalRepository(db.personalDao())
    var id = MutableLiveData<String>()
    var nombre = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    var salida = MutableLiveData<String>()
    var llegada = MutableLiveData<String>()
    var operacion: String = Constantes.OPERACION_INSERTAR
    var operacionExitosa = MutableLiveData<Boolean>()

    init {
        salida.value = "Chinandega"
    }

    fun guardarUsuario() {
        if (validarInformacion()) {
            val mPersonal = Personal("0", nombre.value!!, salida.value!!, llegada.value!!, telefono.value!!)
            when (operacion) {
                Constantes.OPERACION_INSERTAR -> {
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            personalRepository.insertPersonal(listOf(mPersonal))
                        }
                        operacionExitosa.value = result.isNotEmpty()
                    }
                }
                Constantes.OPERACION_EDITAR -> {
                    mPersonal.idBus = id.value!!
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            personalRepository.updatePersonal(mPersonal)
                        }
                        operacionExitosa.value = (result > 0)
                    }
                }
            }
        } else {
            operacionExitosa.value = false
        }
    }
    fun cargarDatos() {
        viewModelScope.launch {
            val persona: Personal = withContext(Dispatchers.IO) {
                personalRepository.getPersonalById(id.value!!.toString())
            }
            nombre.value = persona.nombre
            salida.value = persona.salida
            llegada.value = persona.llegada
            telefono.value = persona.telefono
        }
    }


    private fun validarInformacion(): Boolean {
        //Devuelve true si la informacion no es nula o vacía
        return !(nombre.value.isNullOrEmpty() ||
                telefono.value.isNullOrEmpty() ||
                salida.value.isNullOrEmpty() ||
                llegada.value.isNullOrEmpty()
                )
    }

    fun eliminarPersonal() {
        val mPersonal = Personal(id.value!!, "", "", "", "")
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                personalRepository.deletePersonal(mPersonal)
            }
            operacionExitosa.value = (result > 0)
        }
    }

}