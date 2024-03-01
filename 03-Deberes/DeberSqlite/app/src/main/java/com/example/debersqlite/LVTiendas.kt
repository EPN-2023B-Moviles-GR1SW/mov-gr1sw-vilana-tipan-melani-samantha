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


class LVTiendas : AppCompatActivity() {

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lvtiendas)
        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_tiendas)
        botonCrearTienda.setOnClickListener {
            irActividad(CrudTienda::class.java)
        }
        actualizarLista()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = BTiendaSeleccionada?.id ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CrudTienda::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar ->{

                EBaseDeDatos.tablaTienda!!.eliminarTienda(
                    idReal
                )
                actualizarLista()
                return true
            }
            R.id.mi_ver_automovil ->{
                abrirActividadConParametros(
                    LVAutomovil::class.java,
                    idReal
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    var BTiendaSeleccionada: BTienda? = null

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menutienda, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        BTiendaSeleccionada = listView.adapter.getItem(info.position) as BTienda
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("id", id)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun actualizarLista(){
        val arregloTienda = EBaseDeDatos.tablaTienda!!.consultarTiendas()
        //Adaptador
        listView = findViewById<ListView>(R.id.lv_tiendas)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloTienda
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}