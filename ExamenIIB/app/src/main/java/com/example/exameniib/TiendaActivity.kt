package com.example.exameniib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TiendaActivity : AppCompatActivity() {

    val query: Query? = null
    val arreglo = arrayListOf<BTienda>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<BTienda>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)

        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_tiendas)
        botonCrearTienda.setOnClickListener {
            irActividad(CrudTienda::class.java)
        }

        listView = findViewById<ListView>(R.id.lv_tiendas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        consultarTiendas(adaptador)
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun consultarTiendas(adapter: ArrayAdapter<BTienda>){
        val db = Firebase.firestore
        val tiendaRefUnico = db.collection("tiendas")
        tiendaRefUnico
            .get()
            .addOnSuccessListener { // it => eso (lo que llegue)
                limpiarArreglo() // Limpia el arreglo aquí
                for (tienda in it){
                    tienda.id
                    anadirAArregloTienda(tienda)
                }
                adaptador.notifyDataSetChanged() // Notifica los cambios aquí
            }
            .addOnFailureListener {
                // Errores
            }
    }

    fun limpiarArreglo() {arreglo.clear()}
    fun anadirAArregloTienda(
        tienda: QueryDocumentSnapshot
    ){
        // ciudad.id
        val nuevaTienda = BTienda(

            tienda.data.get("nombre") as String?,
            tienda.data.get("direccion") as String?,
            tienda.data.get("fechaApertura") as String?
        )
        arreglo.add(nuevaTienda)
    }

    fun eliminarTienda(nombre: String) {
        val db = Firebase.firestore
        val tiendasRefUnico = db.collection("tiendas")

        tiendasRefUnico
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    // Obtener el ID del primer documento en los resultados y eliminarlo
                    val documentId = result.documents[0].id
                    tiendasRefUnico
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "DocumentSnapshot successfully deleted!")
                        }
                        .addOnFailureListener {
                            Log.i("firebase-firestore", "Error deleting document")
                        }
                } else {
                    Log.i("firebase-firestore", "No document found with the name: $nombre")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("firebase-firestore", "Error getting documents: ", exception)
            }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menutienda, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        tiendaSeleccionada = listView.adapter.getItem(info.position) as BTienda
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreTiendaReal = tiendaSeleccionada?.nombre ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CrudTienda::class.java,
                    nombreTiendaReal
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                eliminarTienda(nombreTiendaReal)
                consultarTiendas(adaptador)
                return true
            }
            R.id.mi_ver_automovil ->{
                abrirActividadConParametros(
                    AutomovilActivity::class.java,
                    nombreTiendaReal
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    var tiendaSeleccionada: BTienda? = null



    fun abrirActividadConParametros(clase: Class<*>, nombreTienda: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombre", nombreTienda)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }


    override fun onResume() {
        super.onResume()
        consultarTiendas(adaptador)
    }




}