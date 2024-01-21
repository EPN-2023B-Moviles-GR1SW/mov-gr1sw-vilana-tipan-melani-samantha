package com.example.examen01

class BAutomoviles(marca: String,modelo: String,precio: Double,disponibilidad: Boolean){
    var marca: String
    var modelo: String
    var precio: Double
    var disponibilidad: Boolean

    init{
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.disponibilidad = disponibilidad
    }

    override fun toString(): String {
        return  "Marca: $marca \n" +
                "Modelo: $modelo \n"+
                "Precio: $precio \n" +
                "Disponibilidad: $disponibilidad "
    }

}