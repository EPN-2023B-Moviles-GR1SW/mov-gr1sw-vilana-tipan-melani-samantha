package com.example.examen01

import java.text.SimpleDateFormat
import java.util.Date

class BTiendaAutomoviles (nombre:String, direccion:String, fechaApertura:String){
    var nombre: String
    var direccion: String
    var fechaApertura: Date
    var automoviles: ArrayList<BAutomoviles>

    init{
        this.nombre = nombre
        this.direccion = direccion
        this.fechaApertura = SimpleDateFormat("dd/MM/yyyy").parse(fechaApertura)
        this.automoviles = arrayListOf<BAutomoviles>()
    }

    fun getAutomoviles(): ArrayList<BAutomoviles>{
        return this.automoviles
    }
    fun setAutomoviles(fecha:String){
        this.fechaApertura = SimpleDateFormat("dd/MM/yyyy").parse(fecha)
    }

    override fun toString(): String {
        return  " Nombre: $nombre \n"+
                " Direccion: $direccion \n"+
                " Fecha Apertura: $fechaApertura \n"
    }


}