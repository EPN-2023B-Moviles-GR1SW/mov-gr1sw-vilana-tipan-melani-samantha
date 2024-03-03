package com.example.exameniifirestore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Firebase.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonTienda = findViewById<Button>(R.id.btn_ver_tiendas)
        botonTienda
            .setOnClickListener {
                irActividad(
                    LVTiendas::class.java
                )
            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}