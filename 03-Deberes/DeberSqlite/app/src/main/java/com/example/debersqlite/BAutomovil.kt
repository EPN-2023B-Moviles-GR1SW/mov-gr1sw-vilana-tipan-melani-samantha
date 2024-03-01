package com.example.debersqlite

class BAutomovil (
    var idAutomovil: Int?,
    var modelo: String?,
    var precio: Double?,
    var autosDisponibles : Int?,
    var idTienda: Int?
){
    override fun toString(): String {
        return buildString {
            append("Id: $idAutomovil\n")
            append("Modelo: $modelo\n")
            append("Precio : $precio\n")
            append("Autos Disponibles: $autosDisponibles\n")
        }
    }

}