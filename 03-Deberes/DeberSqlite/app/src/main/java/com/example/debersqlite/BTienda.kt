package com.example.debersqlite

import java.util.Date

class BTienda (
    var id: Int?,
    var nombre: String?,
    var direccion: String?,
    var fechaApertura: Date?
    ) {

    override fun toString(): String {
        return buildString {
            append("Nombre: $nombre\n")
            append("Direccion: $direccion\n")
            append("Fecha de apertura: $fechaApertura\n")
        }
    }
}