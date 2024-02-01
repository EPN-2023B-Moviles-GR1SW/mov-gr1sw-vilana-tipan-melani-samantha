package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class EditarTiendaAutomovil : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tienda_automovil)

        val nombre = intent.getStringExtra("nombre")
        val direccion = intent.getStringExtra("direccion")
        val fechaApertura = intent.getStringExtra("fechaApertura")

        val textNombre = findViewById<EditText>(R.id.input_nombreE)
        textNombre.setText(nombre, TextView.BufferType.EDITABLE)

        val textDireccion = findViewById<EditText>(R.id.input_direccionE)
        textDireccion.setText(direccion, TextView.BufferType.EDITABLE)

       val textfechaApertura = findViewById<EditText>(R.id.input_fecha_aperturaE)
        textfechaApertura.setText(fechaApertura, TextView.BufferType.EDITABLE)


        val botonEditar = findViewById<Button>(R.id.btn_editar_tienda)
        botonEditar.setOnClickListener(){
            try {
                devolverRespuesta()
            } catch (e: Exception) {
                val errorMessage = "Ocurrió un error: ${e.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }


        }

    }
    private fun devolverRespuesta() {
        val intentDevolverParametros = Intent()

        val textNombre = findViewById<EditText>(R.id.input_nombreE)
        intentDevolverParametros.putExtra("nombreModificado", textNombre.text.toString())

        val textDireccion = findViewById<EditText>(R.id.input_direccionE)
        intentDevolverParametros.putExtra("direccionModificada", textDireccion.text.toString())

        val textfechaApertura = findViewById<EditText>(R.id.input_fecha_aperturaE)
        intentDevolverParametros.putExtra("fechaAperturaModificada", textfechaApertura.text.toString())


        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }

}



