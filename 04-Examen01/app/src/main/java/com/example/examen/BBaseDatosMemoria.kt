package com.example.examen

class BBaseDatosMemoria {
    companion object{
        val arregloBTiendaAutomoviles = arrayListOf<BTiendaAutomoviles>()

        init{
            arregloBTiendaAutomoviles
                .add(BTiendaAutomoviles("Autolasa","Llano Grande", "13/11/2009"))
            arregloBTiendaAutomoviles.get(0).obtenerAutomoviles().add(BAutomoviles("Toyota", "Camry",30000.0))
            arregloBTiendaAutomoviles.get(0).obtenerAutomoviles().add(BAutomoviles("Honda","Accord", 28000.0))
            arregloBTiendaAutomoviles.get(0).obtenerAutomoviles().add(BAutomoviles("Chevrolet", "Malibu", 26000.0))



            arregloBTiendaAutomoviles
                .add(BTiendaAutomoviles("Cogmotors","Panamericana","18/03/2001"))
            arregloBTiendaAutomoviles.get(1).obtenerAutomoviles().add(BAutomoviles("Nissan", "Leaf", 35000.0))
            arregloBTiendaAutomoviles.get(1).obtenerAutomoviles().add(BAutomoviles("Hyundai", "Tucson", 25000.0))
            arregloBTiendaAutomoviles.get(1).obtenerAutomoviles().add(BAutomoviles("Kia", "Sportage", 26000.0))


        }
    }
}