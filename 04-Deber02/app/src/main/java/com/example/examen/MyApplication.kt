package com.example.examen

import android.app.Application
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EBaseDeDatos.inicializar(this)

    }
}