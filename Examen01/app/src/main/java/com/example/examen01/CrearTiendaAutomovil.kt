package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearTiendaAutomovil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tienda_automovil)

        val crearTienda = findViewById<Button>(R.id.btn_crear_tienda)
        crearTienda.setOnClickListener() { crearTiendaAutomovil() }
    }

    private fun crearTiendaAutomovil(){
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        val fechaApertura = findViewById<EditText>(R.id.input_fecha_apertura)

        BBaseDatosMemoria.arregloBTiendaAutomoviles
            .add(BTiendaAutomoviles(nombre.text.toString(),
                direccion.text.toString(),
                fechaApertura.text.toString()))

        finish()
    }
}