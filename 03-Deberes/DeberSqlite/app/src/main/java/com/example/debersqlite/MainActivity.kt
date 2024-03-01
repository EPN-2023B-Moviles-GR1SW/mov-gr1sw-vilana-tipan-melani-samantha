package com.example.debersqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EBaseDeDatos.tablaTienda = SqliteHelper(this)
        EBaseDeDatos.tablaAutomovil= SqliteHelper(this)

        val botonTienda = findViewById<Button>(R.id.btn_ver_tiendas)
        botonTienda
            .setOnClickListener {
                irActividad(
                    LVTiendas::class.java
                )
            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}