package com.example.rutas.adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rutas.R
import com.example.rutas.config.Constantes
import com.example.rutas.databinding.ItemListBinding
import com.example.rutas.models.Personal
import com.example.rutas.ui.FormularioActivity


class PersonalAdapter(private val dataSet: List<Personal>?) :
    RecyclerView.Adapter<PersonalAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet?.get(position)
        viewHolder.enlazarItem(item!!)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet!!.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemListBinding = ItemListBinding.bind(view)
        val contexto: Context = view.context

        fun enlazarItem(item: Personal) {
            binding.tvNombre.text = item.nombre
            binding.tvTelefono.text = item.telefono
            binding.tvLlegada.text = item.llegada
            binding.tvSalida.text = item.salida

            binding.root.setOnClickListener {
                val intent = Intent(contexto,FormularioActivity::class.java)
                intent.putExtra(Constantes.OPERACION_KEY,Constantes.OPERACION_EDITAR)
                intent.putExtra(Constantes.ID_PERSONAL_KEY,item.idBus)
                contexto.startActivity(intent)
            }
        }

    }

}
