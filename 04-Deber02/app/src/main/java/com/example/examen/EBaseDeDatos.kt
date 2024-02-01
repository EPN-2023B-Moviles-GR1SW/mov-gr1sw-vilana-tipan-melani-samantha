package com.example.examen

import android.content.Context

class EBaseDeDatos {
    companion object{
        private var tablaTiendas: ESqliteHelperTiendaAutomovil? = null

        val tablaTiendaAutomovil: ESqliteHelperTiendaAutomovil
            get(){
                if(tablaTiendas == null){
                    throw IllegalAccessException("Instancia inicializada")
                }
                return tablaTiendas!!
            }

        fun inicializar(context: Context){
            if(tablaTiendas == null){
                tablaTiendas = ESqliteHelperTiendaAutomovil(context)
            }
        }

        override fun toString(): String {
            return tablaTiendaAutomovil.toString()
        }

    }
}