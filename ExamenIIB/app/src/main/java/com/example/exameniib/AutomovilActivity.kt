package com.example.exameniib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.debersqlite.BAutomovil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot

class AutomovilActivity : AppCompatActivity() {

    val query: Query? = null
    val arregloAutomovil = arrayListOf<BAutomovil>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<BAutomovil>

    private val db = FirebaseFirestore.getInstance()

    private var automovilSeleccionado:BAutomovil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automovil)

        val botonCrearAutomovil = findViewById<Button>(R.id.btn_crear_auto)
        botonCrearAutomovil.setOnClickListener {
            abrirActividadConParametros(
                CrudAutomovil::class.java,
                intent.getStringExtra("nombre")?:"",
                automovilSeleccionado?.modelo ?:""
            )
        }
        listView = findViewById<ListView>(R.id.lv_automoviles)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloAutomovil
        )
        listView.adapter = adaptador
        llenarDatos()

        registerForContextMenu(listView)
    }

    private fun llenarDatos() {
        val nombreTienda = intent.getStringExtra("nombre") ?: ""
        val nombreTienda2 = findViewById<TextView>(R.id.txt_tienda_select)
        nombreTienda2.text = nombreTienda

        db.collection("tiendas")
            .whereEqualTo("nombre", nombreTienda)
            .get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val idTienda = documentos.documents[0].id
                    obtenerAutomoviles(idTienda)
                } else {
                    Log.e("Firebase", "No se encontró la tienda con nombre: $nombreTienda")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    private fun obtenerAutomoviles(idTienda: String) {
        db.collection("tiendas")
            .document(idTienda)
            .collection("automoviles")
            .get()
            .addOnSuccessListener { documentos ->
                arregloAutomovil.clear()
                for (documento in documentos) {
                    documento.id
                    anadirAutomovil(documento)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    fun anadirAutomovil(auto: QueryDocumentSnapshot) {


        val nuevoautomovil = BAutomovil(
            auto.data["modelo"] as String?,
            auto.data["precio"] as String?,
            auto.data["autosDisponibles"] as String?
        )
        Log.i("NuevoAuto", "Nuevo auto: $nuevoautomovil")
        arregloAutomovil.add(nuevoautomovil)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombre = intent.getStringExtra("nombre") ?: ""
        val modelo = automovilSeleccionado?.modelo ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(CrudAutomovil::class.java,
                    nombre,modelo)
                true
            }
            R.id.mi_eliminar -> {
                eliminarLugar(nombre, modelo)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuautomovil, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        automovilSeleccionado = listView.adapter.getItem(info.position) as BAutomovil
    }



    fun eliminarLugar(nombre: String, modelo: String) {

        db.collection("tiendas")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { documentosTienda ->
                if (documentosTienda.documents.isNotEmpty()) {
                    val idTienda = documentosTienda.documents[0].id


                    db.collection("tiendas")
                        .document(idTienda)
                        .collection("automoviles")
                        .whereEqualTo("modelo", modelo)
                        .get()
                        .addOnSuccessListener { documentosAutomovil ->
                            if (documentosAutomovil.documents.isNotEmpty()) {
                                val idAuto = documentosAutomovil.documents[0].id


                                db.collection("tiendas")
                                    .document(idTienda)
                                    .collection("automoviles")
                                    .document(idAuto)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.i("Firebase", "Automovil eliminado con éxito!")
                                        llenarDatos() // Recargar la lista luego de eliminar un lugar turístico
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.e("Firebase", "Error eliminando el automovil: ", exception)
                                    }
                            } else {
                                Log.e("Firebase", "No se encontró el automovil con modelo: $modelo")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firebase", "Error obteniendo documentos: ", exception)
                        }
                } else {
                    Log.e("Firebase", "No se encontró la tienda con nombre: $nombre")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }




    fun abrirActividadConParametros(clase: Class<*>, nombre: String, modelo: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombreT", nombre)
        intentExplicito.putExtra("modeloL", modelo)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }

}