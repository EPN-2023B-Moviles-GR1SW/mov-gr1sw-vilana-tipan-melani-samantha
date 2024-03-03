package com.example.exameniifirestore

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LVTiendas : AppCompatActivity() {
    val arreglo: ArrayList<ITienda> = arrayListOf()
    private var adaptador: ArrayAdapter<ITienda>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lvtiendas)

        val listView = findViewById<ListView>(R.id.lv_tiendas)
        adaptador = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,arreglo
        )
        limpiarArreglo()
        listView.adapter = adaptador
        adaptador?.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonCrearTiendas = findViewById<Button>(R.id.btn_crear_tiendas)
        botonCrearTiendas.setOnClickListener {

        }

    }
}