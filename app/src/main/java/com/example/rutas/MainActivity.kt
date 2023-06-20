package com.example.rutas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rutas.adaptadores.PersonalAdapter
import com.example.rutas.config.Constantes
import com.example.rutas.databinding.ActivityMainBinding
import com.example.rutas.ui.FormularioActivity

import com.example.rutas.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get()

        binding.lifecycleOwner = this
        binding.modelo = viewModel
        viewModel.iniciar()

        binding.myRecycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }
        viewModel.personalList.observe(this, Observer {
            binding.myRecycler.adapter = PersonalAdapter(it)
        })

        binding.btnAbrirFormulario.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_INSERTAR)
            startActivity(intent)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val busqueda = p0.toString().trim()

                if (busqueda.isEmpty()) {
                    viewModel.iniciar()
                } else {
                    viewModel.actualizarBusqueda(busqueda)
                }
            }
        })

    }
}