package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

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
        val rgDisponibilidad = findViewById<RadioGroup>(R.id.rg_disponibilidad)
        var disponibilidad = false
        if(rgDisponibilidad.checkedRadioButtonId == R.id.rb_si){
            disponibilidad = true
        }else if (rgDisponibilidad.checkedRadioButtonId == R.id.rb_no){
            disponibilidad = false
        }

        BBaseDatosMemoria.arregloBTiendaAutomoviles.get(this.idTiendaAutomovil).getAutomoviles()
            .add(BAutomoviles(marca.text.toString(), modelo.text.toString(),
                precio.text.toString().toDouble(),disponibilidad))
    }
}