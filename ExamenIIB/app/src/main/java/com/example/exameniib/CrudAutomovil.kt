package com.example.exameniib

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class CrudAutomovil : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var documentoId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_crud_automovil)

        val nombreTienda = intent.getStringExtra("nombreT")?:""
        val modeloAuto = intent.getStringExtra("modeloA")?:""

        llenarDatosFormulario(nombreTienda,modeloAuto)

        val botonCrearAutomovil = findViewById<Button>(R.id.btn_crear_automovil)
        botonCrearAutomovil
            .setOnClickListener {
                try {
                    val modeloI = findViewById<EditText>(R.id.input_modelo).text.toString()
                    val precioI = findViewById<EditText>(R.id.input_precio).text.toString()
                    val autosDisponiblesI = findViewById<EditText>(R.id.input_autos_disponibles).text.toString()

                    val nuevoAuto = hashMapOf(
                        "modelo" to modeloI,
                        "precio" to precioI,
                        "autosDisponibles" to autosDisponiblesI
                    )

                    db.collection("tiendas")
                        .whereEqualTo("nombre", nombreTienda)
                        .get()
                        .addOnSuccessListener { documentos ->
                            if (documentos.documents.isNotEmpty()) {
                                val tiendaId = documentos.documents[0].id


                                db.collection("tiendas").document(tiendaId)
                                    .collection("automoviles")
                                    .add(nuevoAuto)
                                    .addOnSuccessListener {
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                    }
                            } else {
                                mostrarSnackBar("Tienda no encontrado" + nombreTienda)
                            }
                        }
                        .addOnFailureListener { e ->
                            mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                        }

                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }

        val botonActualizarAutomovil = findViewById<Button>(R.id.btn_actualizar_automovil)
        botonActualizarAutomovil
            .setOnClickListener {
                try {
                   // val id = idAutomovilE
                    val modeloE = findViewById<EditText>(R.id.input_modelo).text.toString()
                    val precio = findViewById<EditText>(R.id.input_precio).text.toString()
                    val autosDisponibles = findViewById<EditText>(R.id.input_autos_disponibles).text.toString()
                   // val idTienda = idTiendaSelected

                    db.collection("tiendas")
                        .whereEqualTo("nombre", nombreTienda)
                        .get()
                        .addOnSuccessListener { documentos ->
                            if (documentos.documents.isNotEmpty()) {
                                val tiendaId = documentos.documents[0].id

                                db.collection("tiendas").document(tiendaId)
                                    .collection("automoviles")
                                    .whereEqualTo("modelo", modeloAuto)
                                    .get()
                                    .addOnSuccessListener { documentosAuto ->
                                        if (documentos.documents.isNotEmpty()) {
                                            val autoId = documentosAuto.documents[0].id

                                            db.collection("tiendas").document(tiendaId)
                                                .collection("automoviles").document(autoId)
                                                .update(
                                                    "modelo", modeloE,
                                                    "precio", precio,
                                                    "autosDisponibles", autosDisponibles
                                                )
                                                .addOnSuccessListener {
                                                    finish()
                                                }
                                        } else {
                                            mostrarSnackBar("Automovil no encontrado")
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                    }
                            } else {
                                mostrarSnackBar("Tienda no encontrada")
                            }
                        }
                        .addOnFailureListener { e ->
                            mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                        }
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }
        if (modeloAuto.isNotEmpty()) {
                //ocultar boton crear
            botonCrearAutomovil.visibility = Button.INVISIBLE
        } else {
                //ocultar boton actualizar
            botonActualizarAutomovil.visibility = Button.INVISIBLE
        }


    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_crud_automovil),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }



    fun llenarDatosFormulario(nombreTienda: String, modeloAuto: String) {
        if (nombreTienda.isNotEmpty() && modeloAuto.isNotEmpty()) {
            db.collection("tiendas").whereEqualTo("nombre", nombreTienda)
                .get()
                .addOnSuccessListener { documentosTienda ->
                    if (documentosTienda.documents.isNotEmpty()) {
                        val tienda = documentosTienda.documents[0]

                        db.collection("tiendas").document(tienda.id)
                            .collection("automoviles").whereEqualTo("modelo", modeloAuto)
                            .get()
                            .addOnSuccessListener { documentosAuto ->
                                if (documentosAuto.documents.isNotEmpty()) {
                                    val auto = documentosAuto.documents[0]

                                    val modelo = findViewById<EditText>(R.id.input_modelo)
                                    val precio = findViewById<EditText>(R.id.input_precio)
                                    val autosDisponibles = findViewById<EditText>(R.id.input_autos_disponibles)

                                    modelo.setText(auto.getString("modelo"))
                                    precio.setText(auto.getString("precio"))
                                    autosDisponibles.setText(auto.getString("autosDisponibles"))
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }


}