package com.example.debersqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat

class CrudTienda : AppCompatActivity() {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_tienda)
        val idTienda = intent.getIntExtra("id", 0)
        llenarDatosFormulario(idTienda)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_tienda)
        botonCrearBDD
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
                val direccion = findViewById<EditText>(R.id.input_direccion_tienda)
                val fechaApertura = findViewById<EditText>(R.id.input_fecha_tienda)
                EBaseDeDatos
                    .tablaTienda!!.crearTienda(
                        nombre.text.toString(),
                        direccion.text.toString(),
                        fechaApertura.text.toString()
                    )
                finish()
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_tienda)
        botonActualizarBDD
            .setOnClickListener{

                val id = idTienda
                val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
                val direccion = findViewById<EditText>(R.id.input_direccion_tienda)
                val fechaApertura = findViewById<EditText>(R.id.input_fecha_tienda)

                EBaseDeDatos.tablaTienda!!.actualizarTienda(
                    nombre.text.toString(),
                    direccion.text.toString(),
                    fechaApertura.text.toString(),
                    id
                )
                finish()
            }
    }

    override fun onResume() {
        super.onResume()
    }


    fun llenarDatosFormulario(idTienda: Int) {
        if (idTienda != 0) {
            val tiendaEncontrada = EBaseDeDatos.tablaTienda!!.consultarTiendaPorID(idTienda)
            val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
            val direccion = findViewById<EditText>(R.id.input_direccion_tienda)
            val fechaApertura = findViewById<EditText>(R.id.input_fecha_tienda)

            nombre.setText(tiendaEncontrada.nombre)
            direccion.setText(tiendaEncontrada.direccion)
            fechaApertura.setText(formatoFecha.format(tiendaEncontrada.fechaApertura))
        }
    }
}
