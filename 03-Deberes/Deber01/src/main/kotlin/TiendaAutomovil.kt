package org.example

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TiendaAutomovil(
    val idTienda: Int,
    var nombre: String,
    var direccion: String,
    var fechaApertura: Date,
    var arregloAutomoviles: ArrayList<Automovil>?
):Serializable{

    override fun toString(): String {

        return " ID Tienda: $idTienda"+
            " Nombre: $nombre"+
            " Direccion: $direccion"+
            " Fecha Apertura: $fechaApertura"+
            " \n Automóviles: ${arregloAutomoviles}"
    }


}