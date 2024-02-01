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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    var arregloTiendas = BBaseDatosMemoria.arregloBTiendaAutomoviles
    var idItemSeleccionado = 0
    var arregloTiendasAutomovil: ArrayList<BTiendaAutomoviles> = arrayListOf<BTiendaAutomoviles>()

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data
                    /*
                    arregloTiendas.get(idItemSeleccionado).nombre = data?.getStringExtra("nombreModificado").toString()
                    arregloTiendas.get(idItemSeleccionado).direccion = data?.getStringExtra("direccionModificada").toString()
                    arregloTiendas.get(idItemSeleccionado).setFechaApertura(data?.getStringExtra("fechaAperturaModificada").toString())
                    */
                    EBaseDeDatos.tablaTiendaAutomovil.actualizarTiendaFormulario(
                        data?.getStringExtra("nombreModificada").toString(),
                        data?.getStringExtra("direccionModificado").toString(),
                        data?.getStringExtra("fechaAperturaModificada").toString(),
                        data?.getIntExtra("idTienda", 0)!!
                    )

                    actualizarListView()

                }
            }
        }

    val callbackContenidoIntentExplicito1 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data

                    EBaseDeDatos.tablaTiendaAutomovil.crearTienda(
                        data?.getStringExtra("nombreModificada").toString(),
                        data?.getStringExtra("direccionModificado").toString(),
                        data?.getStringExtra("fechaAperturaModificada").toString(),
                    )

                    actualizarListView()

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actualizarListView()
        val botonAnadirTienda = findViewById<Button>(R.id.btn_anadir_tienda)
        botonAnadirTienda.setOnClickListener(){
            irActividad(CrearTiendaAutomovil::class.java)
        }
    }

    fun actualizarListView(){
        this.arregloTiendasAutomovil = EBaseDeDatos.tablaTiendaAutomovil.consultarTiendasAutomovil()

        val listView = findViewById<ListView>(R.id.lv_list_view_tiendas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, //como se va a ver(XML)
            arregloTiendas
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
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }
    override fun onResume() {
        super.onResume()
        actualizarListView()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametrosEditarTienda(EditarTiendaAutomovil::class.java)
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogoEliminar()
                actualizarListView()
                return true
            }
            R.id.mi_ver_autos ->{
                try{
                    abrirActividadListarAutomoviles(BListViewAutomovil::class.java)
                } catch(e: Exception){
                    val mensajeError = "Error inesperado: ${e.message}"
                    Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadConParametrosEditarTienda(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val tiendaAutomovil = arregloTiendas.get(idItemSeleccionado)
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        intentExplicito.putExtra("nombre", tiendaAutomovil.nombre)
        intentExplicito.putExtra("direccion", tiendaAutomovil.direccion)
        intentExplicito.putExtra("fechaapertura", formatoFecha.format(tiendaAutomovil.fechaApertura))


        callbackContenidoIntentExplicito.launch(intentExplicito)
    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }



    fun abrirDialogoEliminar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton("Aceptar"){ dialog, which ->
            BBaseDatosMemoria.arregloBTiendaAutomoviles.removeAt(idItemSeleccionado)
            actualizarListView() }

        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun abrirActividadListarAutomoviles(clase: Class<*>){
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("idTienda", arregloTiendas.get(idItemSeleccionado).id?:0)
        intentExplicito.putExtra("nombreTienda", arregloTiendas.get(idItemSeleccionado).nombre)
        startActivity(intentExplicito)
    }





}