package org.example

import java.io.Serializable

class Automovil (
    val idAutomovil: Int,
    var marca: String,
    var modelo: String,
    var precio: Double,
    var disponibilidad: Boolean,
    var tienda: Int
): Serializable{

    override fun toString(): String {
        return " ID: $idAutomovil"+ " Marca: $marca" +
                " Modelo: $modelo"+ " Precio: $precio" +
                " Disponibilidad: $disponibilidad " + " Tienda: $tienda"
    }


}