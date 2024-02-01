package com.example.examen

class BAutomoviles (marca: String, modelo: String, precio: Double) {
    var id: Int? = null
    var marca: String
    var modelo: String
    var precio: Double

    constructor(id:Int, marca: String, modelo: String, precio: Double):
            this(marca,modelo, precio){
        this.id = id
    }



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