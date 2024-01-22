package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class EditarAutomoviles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_automoviles)

        val marca = intent.getStringExtra("marca")
        val modelo = intent.getStringExtra("modelo")
        val precio = intent.getStringExtra("precio")

        val marcaA = findViewById<EditText>(R.id.input_marcaE)
        marcaA.setText(marca, TextView.BufferType.EDITABLE)

        val modeloA = findViewById<EditText>(R.id.input_modeloE)
        modeloA.setText(modelo, TextView.BufferType.EDITABLE)

        val precioA = findViewById<EditText>(R.id.input_precioE)
        precioA.setText(precio, TextView.BufferType.EDITABLE)

        val botonEditarAuto = findViewById<Button>(R.id.btn_editar_automovil)
        botonEditarAuto.setOnClickListener(){
            devolverRespuesta()
        }

    }


    private fun devolverRespuesta(){
        val intentDevolverParametros = Intent()

        val marcaA = findViewById<EditText>(R.id.input_marcaE)
        intentDevolverParametros.putExtra("marcaModificada",marcaA.text.toString())

        val modeloA = findViewById<EditText>(R.id.input_modeloE)
        intentDevolverParametros.putExtra("modeloModificado",modeloA.text.toString())

        val precioA = findViewById<EditText>(R.id.input_precioE)
        intentDevolverParametros.putExtra("precioModificado", precioA.text.toString())


        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()

    }





}