package com.example.examen

import java.text.SimpleDateFormat
import java.util.Date

class BTiendaAutomoviles(nombre:String, direccion:String, fechaApertura:String) {
    var nombre: String
    var direccion: String
    var fechaApertura: Date
    var automoviles: ArrayList<BAutomoviles>

    init{
        this.nombre = nombre
        this.direccion = direccion
        this.fechaApertura = StringToDate(fechaApertura)
        this.automoviles = arrayListOf<BAutomoviles>()
    }

    fun obtenerAutomoviles(): ArrayList<BAutomoviles>{
        return this.automoviles
    }

    fun StringToDate(fecha: String): Date{
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        return formatoFecha.parse(fecha)
    }

    fun setFechaApertura(fecha: String){
        this.fechaApertura = StringToDate(fecha)
    }




    override fun toString(): String {
        return  " Nombre: $nombre \n"+
                " Direccion: $direccion \n"+
                " Fecha Apertura: $fechaApertura \n"
    }

}