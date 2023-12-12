package com.example.b2023_gr1sw_msvt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""

    fun mostrarSanckbar(texto:String){
        textoGlobal = textoGlobal + " "+texto
        Snackbar.make(
            findViewById(R.id.cl_ciclo_vida), //view
            textoGlobal, //texto
            Snackbar.LENGTH_INDEFINITE // TIEMPO
        )
            .show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }

    override fun onStart() {
        super.onStart()
        mostrarSanckbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSanckbar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSanckbar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSanckbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSanckbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSanckbar("onDestroy")
    }
}