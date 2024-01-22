package com.example.examen

import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class BListViewAutomovil : AppCompatActivity() {

    var arregloAutos: ArrayList<BAutomoviles>? = null
    var idItemSeleccionado = 0
    var idTienda = 0

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data
                    arregloAutos?.get(idItemSeleccionado)?.marca = data?.getStringExtra("marcaModificado").toString()
                    arregloAutos?.get(idItemSeleccionado)?.modelo = data?.getStringExtra("modeloModificado").toString()
                    arregloAutos?.get(idItemSeleccionado)?.precio = data?.getStringExtra("precioModificado")?.toDouble() ?:0.0

                    actualizarListView(this.arregloAutos!!)

                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view_automovil)

        this.idTienda = intent.getIntExtra("idTienda",0)
        arregloAutos = BBaseDatosMemoria.arregloBTiendaAutomoviles.get(this.idTienda).obtenerAutomoviles()

        val nombreTienda = findViewById<TextView>(R.id.txt_titulo_lv)
        nombreTienda.setText(BBaseDatosMemoria.arregloBTiendaAutomoviles.get(intent.getIntExtra("idTiendaAuto",0)).nombre)

        actualizarListView(this.arregloAutos!!)

        val botonAnadir = findViewById<Button>(R.id.btn_anadir_automovil)
        botonAnadir.setOnClickListener(){
            abrirActividadCrearAutomovil(CrearAutomovil::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        actualizarListView(this.arregloAutos!!)
    }

    fun actualizarListView(arregloAutos: ArrayList<BAutomoviles>){
        val listView = findViewById<ListView>(R.id.lv_list_view_automovil)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, //como se va a ver(XML)
            arregloAutos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menuauto, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_auto ->{
                abrirActividadConParametrosEditarA(EditarAutomoviles::class.java)
                return true
            }
            R.id.mi_eliminar_auto ->{
                abrirDialogoEliminar()
                actualizarListView(this.arregloAutos!!)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    fun abrirActividadConParametrosEditarA(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val automovil = arregloAutos?.get(idItemSeleccionado)
        intentExplicito.putExtra("marca", automovil?.marca)
        intentExplicito.putExtra("modelo", automovil?.modelo)
        intentExplicito.putExtra("precio", automovil?.precio.toString())

        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun abrirActividadCrearAutomovil(clase: Class<*>){
        val intent = Intent(this, clase)
        intent.putExtra("id", this.idTienda)
        startActivity(intent)
    }



    fun abrirDialogoEliminar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton("Aceptar"){ dialog, which ->
            arregloAutos?.removeAt(idItemSeleccionado)
            actualizarListView(this.arregloAutos!!) }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }






}