package com.example.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearAutomovil : AppCompatActivity() {
    var idTiendaAutomovil = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_automovil)
        this.idTiendaAutomovil = intent.getIntExtra("id",0)

        val crearAutomovil = findViewById<Button>(R.id.btn_crear_automovil)
        crearAutomovil.setOnClickListener (){ crearAutomovil()  }
    }

    private fun crearAutomovil(){
        val marca = findViewById<EditText>(R.id.input_marca)
        val modelo = findViewById<EditText>(R.id.input_modelo)
        val precio = findViewById<EditText>(R.id.input_precio)

        BBaseDatosMemoria.arregloBTiendaAutomoviles.get(this.idTiendaAutomovil).obtenerAutomoviles()
            .add(
                BAutomoviles(marca.text.toString(), modelo.text.toString(),
                    precio.text.toString().toDouble())
            )
        finish()
    }
}