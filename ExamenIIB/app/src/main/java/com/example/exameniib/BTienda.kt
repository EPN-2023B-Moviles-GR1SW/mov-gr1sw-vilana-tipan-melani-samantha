package com.example.exameniib
import java.util.Date

class BTienda (
    var nombre: String?,
    var direccion: String?,
    var fechaApertura: String?
    ) {

    override fun toString(): String {
        return buildString {
            append("Nombre: $nombre\n")
            append("Direccion: $direccion\n")
            append("Fecha de apertura: $fechaApertura\n")
        }
    }
}