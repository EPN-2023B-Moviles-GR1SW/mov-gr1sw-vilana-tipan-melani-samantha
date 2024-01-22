package com.example.examen

class BAutomoviles (marca: String, modelo: String, precio: Double) {
    var marca: String
    var modelo: String
    var precio: Double


    init{
        this.marca = marca
        this.modelo = modelo
        this.precio = precio

    }

    override fun toString(): String {
        return  "Marca: $marca \n" +
                "Modelo: $modelo \n"+
                "Precio: $precio \n"
    }

}