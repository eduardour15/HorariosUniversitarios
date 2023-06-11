package com.example.rutas.viewmodel

import android.app.Person
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutas.config.Constantes
import com.example.rutas.config.PersonalApp.Companion.db
import com.example.rutas.models.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioViewModel : ViewModel() {
    var id = MutableLiveData<Long>()
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
            var mPersonal =
                Personal(0, nombre.value!!, salida.value!!, llegada.value!!, telefono.value!!)
            when (operacion) {
                Constantes.OPERACION_INSERTAR -> {
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            db.personalDao().insert(
                                arrayListOf<Personal>(mPersonal)
                            )
                        }
                        operacionExitosa.value = result.isNotEmpty()
                    }
                }

                Constantes.OPERACION_EDITAR -> {
                    mPersonal.idBus = id.value!!
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            db.personalDao().update(mPersonal)
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
            var persona: Personal = withContext(Dispatchers.IO) {
                db.personalDao().getById(id.value!!)
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
        var mPersonal =
            Personal(id.value!!, "", "", "", "")
        viewModelScope.launch {
            var result = withContext(Dispatchers.IO) {
                db.personalDao().delete(mPersonal)
            }
            operacionExitosa.value = (result > 0)
        }
    }
}