package com.example.proyectoiib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnadirActivity : AppCompatActivity() {

    private var authActual = FirebaseAuth.getInstance().currentUser
    var spinnerTipo: Spinner? = null
    var tituloEditText: EditText? = null
    var autorEditText: EditText? = null
    var spinnerGenero: Spinner? = null
    var urlImagenEditar: EditText? = null
    var urlImagenTitulo: TextView? = null
    var urlLibroEditar: EditText? = null
    var urlLibroTitulo: TextView? = null
    var botonCrear: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir)

        spinnerTipo = findViewById(R.id.s_anadira_tipospinner)
        tituloEditText = findViewById(R.id.et_anadira_tituloE)
        autorEditText = findViewById(R.id.et_anadira_autorE)
        spinnerGenero = findViewById(R.id.s_anadira_genero)
        urlImagenTitulo = findViewById(R.id.tv_anadira_urlImagen)
        urlImagenEditar = findViewById(R.id.et_anadir_urlImagen)
        urlLibroEditar = findViewById(R.id.et_anadira_urlLibro)
        urlLibroTitulo = findViewById(R.id.tv_anadira_urlLibro)
        botonCrear = findViewById(R.id.btn_editara_actualizar)

        val opcionesTipo = arrayOf("Físico", "Virtual")
        val adaptadorSpinerTipo =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesTipo)
        adaptadorSpinerTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.spinnerTipo?.adapter = adaptadorSpinerTipo

        val db = Firebase.firestore
        val generosRef = db.collection("generos")
        val opcionesGenero = arrayListOf<String>()

        generosRef.get().addOnSuccessListener { generos ->
                for (genero in generos) {
                    val nombre = genero.getString("nombre")
                    if (nombre != null) {
                        opcionesGenero.add(nombre)
                    }
                }
                val adaptadorSpinerGenero =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesGenero)
                adaptadorSpinerGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.spinnerGenero?.adapter = adaptadorSpinerGenero
            }.addOnFailureListener { e ->

            }



        spinnerTipo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val seleccion = parent?.getItemAtPosition(position).toString()

                if (seleccion == "Físico") {
                    urlLibroEditar?.visibility = View.INVISIBLE
                    urlLibroTitulo?.visibility = View.INVISIBLE
                } else {
                    urlLibroEditar?.visibility = View.VISIBLE
                    urlLibroTitulo?.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        botonCrear!!.setOnClickListener {
            crearLibro()
        }


    }

    private fun crearLibro() {

        var urlLibroVirtual = ""
        if (urlLibroEditar!!.text.isNotEmpty()) {
            urlLibroVirtual = urlLibroEditar!!.text.toString()
        }

        val libroNuevo = hashMapOf(
            "tipo" to spinnerTipo!!.getItemAtPosition(spinnerTipo!!.selectedItemPosition)
                .toString(),
            "titulo" to tituloEditText!!.text.toString(),
            "autor" to autorEditText!!.text.toString(),
            "genero" to spinnerGenero!!.getItemAtPosition(spinnerGenero!!.selectedItemPosition)
                .toString(),
            "urlImagen" to urlImagenEditar!!.text.toString(),
            "urlLibro" to urlLibroVirtual
        )

        val db = Firebase.firestore
        val generosRef = db.collection("usuarios").document(authActual!!.uid).collection("libros")

        Log.e(
            "Valor del tipo",
            spinnerTipo!!.getItemAtPosition(spinnerTipo!!.selectedItemPosition).toString()
        )
        Log.e(
            "Valor del genero",
            spinnerGenero!!.getItemAtPosition(spinnerGenero!!.selectedItemPosition).toString()
        )

        generosRef.add(libroNuevo).addOnSuccessListener {
                finish()
            }.addOnFailureListener { e ->
                Log.e("TAG", "Error al agregar el libro", e)
            }
    }

}