package com.example.exameniib


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrudTienda : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_crud_tienda)

        db = Firebase.firestore

        val nombreTienda = intent.getStringExtra("nombre")

        val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
        val direccion = findViewById<EditText>(R.id.input_direccion_tienda)
        val fechaApertura = findViewById<EditText>(R.id.input_fecha_tienda)


        llenarDatosFormulario(nombreTienda)

        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_tienda)
        botonCrearTienda
            .setOnClickListener{
                val tienda = hashMapOf(
                    "nombre" to nombre.text.toString(),
                    "direccion" to direccion.text.toString(),
                    "fechaApertura" to fechaApertura.text.toString()
                )

                db.collection("tiendas")
                    .add(tienda)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {  }
            }

        val botonActualizarTienda = findViewById<Button>(R.id.btn_actualizar_tienda)
        botonActualizarTienda
            .setOnClickListener{

               documentoId?.let{id ->
               val tiendaActualizada = hashMapOf(

                   "nombre" to nombre.text.toString(),
                   "direccion" to direccion.text.toString(),
                   "fechaApertura" to fechaApertura.text.toString()
               )

                   db.collection("tiendas")
                       .document(id)
                       .update(tiendaActualizada as Map<String, Any>)
                       .addOnSuccessListener {
                           finish()
                       }
                       .addOnFailureListener {  }
               }
            }


        if (nombreTienda != null) {
            botonCrearTienda.visibility = Button.INVISIBLE
        } else {
            botonActualizarTienda.visibility = Button.INVISIBLE
        }

    }
    fun llenarDatosFormulario(nombreTienda: String?) {
        if(nombreTienda != null){
            db.collection("tiendas")
                .whereEqualTo("nombre", nombreTienda)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.isNotEmpty()) {
                        val tienda = documents.documents[0]

                        val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
                        val direccion = findViewById<EditText>(R.id.input_direccion_tienda)
                        val fechaApertura = findViewById<EditText>(R.id.input_fecha_tienda)

                        nombre.setText(tienda.getString("nombre"))
                        direccion.setText(tienda.getString("direccion"))
                        fechaApertura.setText(tienda.getString("fechaApertura"))


                        // Guardar el ID del documento para usarlo durante la actualización
                        documentoId = tienda.id
                    }
                }
                .addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }

}