package com.example.exameniib

class BAutomovil (
    var modelo: String?,
    var precio: String?,
    var autosDisponibles : String?,
){
    override fun toString(): String {
        return buildString {
            append("Modelo: $modelo\n")
            append("Precio : $precio\n")
            append("Autos Disponibles: $autosDisponibles\n")
        }
    }

}