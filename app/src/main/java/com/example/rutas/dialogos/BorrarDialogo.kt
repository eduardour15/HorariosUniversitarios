package com.example.rutas.dialogos

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.rutas.R

class BorrarDialogo(var borrarListener: BorrarListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.BORRAR_PERSONAL)
                .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                    borrarListener.borrarPersonal()
                }).setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface BorrarListener {
        fun borrarPersonal()
    }
}