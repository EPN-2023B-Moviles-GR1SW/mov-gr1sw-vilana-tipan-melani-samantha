package com.example.debersqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class LVAutomovil : AppCompatActivity() {

    var idItemSeleccionado = 0
    val arregloBAutomovil = arrayListOf<BAutomovil>()
    var idProductoSeleccionado = 0
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lvautomovil)

        val botonCrearProducto = findViewById<Button>(R.id.btn_crear_auto)
        botonCrearProducto.setOnClickListener {
            abrirActividadConParametros(
                CrudAutomovil::class.java,
                0
            )
        }
        llenarDatos()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = BAutomovilSeleccionado?.idAutomovil ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    CrudAutomovil::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar -> {
                EBaseDeDatos.tablaAutomovil!!.eliminarAutomovil(
                    idReal
                )
                llenarDatos()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    var BAutomovilSeleccionado: BAutomovil? = null
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuautomovil, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        BAutomovilSeleccionado = listView.adapter.getItem(info.position) as BAutomovil
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idProducto", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))
        startActivity(intentExplicito)
    }

    fun llenarDatos() {
        val idTienda = intent.getIntExtra("id", 0)
        val arregloProducto = EBaseDeDatos.tablaAutomovil!!.consultarAutosPorTienda(idTienda)
        val tiendaEncontrada = EBaseDeDatos.tablaTienda!!.consultarTiendaPorID(idTienda)
        val nombreTienda = findViewById<TextView>(R.id.txt_tienda_select)
        nombreTienda.text = tiendaEncontrada.nombre

        listView = findViewById<ListView>(R.id.lv_automoviles)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloProducto
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_Prod),
            texto,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null).show()
    }
}
