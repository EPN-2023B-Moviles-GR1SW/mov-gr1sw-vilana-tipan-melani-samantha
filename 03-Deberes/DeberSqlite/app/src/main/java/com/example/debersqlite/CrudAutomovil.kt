package com.example.debersqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CrudAutomovil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_automovil)

        val idAutomovilE = intent.getIntExtra("idAutomovil", 0)
        val idTiendaSelected = intent.getIntExtra("id", 0)
        llenarDatos(idAutomovilE)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_automovil)
        botonCrearBDD
            .setOnClickListener {
                try {
                    val modelo = findViewById<EditText>(R.id.input_modelo)
                    val precio = findViewById<EditText>(R.id.input_precio)
                    val autosDisponibles = findViewById<EditText>(R.id.input_autos_disponibles)
                    val idTienda = idTiendaSelected

                    EBaseDeDatos.tablaAutomovil!!.crearAutomovil(
                        modelo.text.toString(),
                        precio.text.toString().toDouble(),
                        autosDisponibles.text.toString().toInt(),
                        idTienda
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_automovil)
        botonActualizarBDD
            .setOnClickListener {
                try {
                    val id = idAutomovilE
                    val modelo = findViewById<EditText>(R.id.input_modelo)
                    val precio = findViewById<EditText>(R.id.input_precio)
                    val autosDisponibles = findViewById<EditText>(R.id.input_autos_disponibles)
                    val idTienda = idTiendaSelected

                    EBaseDeDatos.tablaAutomovil!!.actualizarAutomovil(
                        modelo.text.toString(),
                        precio.text.toString().toDouble(),
                        autosDisponibles.text.toString().toInt(),
                        idTienda,
                        id
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }
    }

    private fun llenarDatos(idAutomovilEditar: Int) {
        if (idAutomovilEditar != 0) {
            val automovilEncontrado =
                EBaseDeDatos.tablaAutomovil!!.consultarAutomovilPorID(idAutomovilEditar)
            if (automovilEncontrado != null) {
                findViewById<EditText>(R.id.input_modelo).setText(automovilEncontrado.modelo)
                findViewById<EditText>(R.id.input_precio).setText(automovilEncontrado.precio.toString())
                findViewById<EditText>(R.id.input_autos_disponibles).setText(automovilEncontrado.autosDisponibles.toString()
                )

            }
        }
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_crud_automovil),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}