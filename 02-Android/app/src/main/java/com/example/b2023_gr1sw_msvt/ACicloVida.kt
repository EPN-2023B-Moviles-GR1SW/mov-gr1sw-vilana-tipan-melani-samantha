package com.example.b2023_gr1sw_msvt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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
        mostrarSanckbar("onCreate")
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

    override fun onSaveInstanceState(outState: Bundle ) {
        outState.run {
            //GUARDAR LAS VARIABLES
            //PRIMITIVOS
            putString("textoGuardado", textoGlobal)
            //putInt("numeroGuardado", numero)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //RECUPERAR LAS VARIABLES
        //PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState
            .getString("textoGuardado")
        //val textoRecuperado:Int? = savedInstanceState
        //.getInt("numeroGuardado")
        if(textoRecuperado!= null){
            mostrarSanckbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }
}