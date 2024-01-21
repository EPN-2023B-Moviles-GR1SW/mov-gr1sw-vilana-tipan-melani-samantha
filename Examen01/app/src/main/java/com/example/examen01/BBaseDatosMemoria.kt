package com.example.examen01

class BBaseDatosMemoria {
    companion object{
        val arregloBTiendaAutomoviles = arrayListOf<BTiendaAutomoviles>()

        init{
            arregloBTiendaAutomoviles
                .add(BTiendaAutomoviles("Autolasa","Av. Pedro Menendez Gilbert", "13/11/2009"))
            arregloBTiendaAutomoviles.get(0).getAutomoviles().add(BAutomoviles("Toyota", "Camry",30000.0, true))
            arregloBTiendaAutomoviles.get(0).getAutomoviles().add(BAutomoviles("Honda","Accord", 28000.0, true))
            arregloBTiendaAutomoviles.get(0).getAutomoviles().add(BAutomoviles("Chevrolet", "Malibu", 26000.0, false))
            arregloBTiendaAutomoviles.get(0).getAutomoviles().add(BAutomoviles("Ford", "Fusion", 27000.0, true))


            arregloBTiendaAutomoviles
                .add(BTiendaAutomoviles("Cogmotors","Panamericana","18/03/2001"))
            arregloBTiendaAutomoviles.get(1).getAutomoviles().add(BAutomoviles("Nissan", "Leaf", 35000.0, true))
            arregloBTiendaAutomoviles.get(1).getAutomoviles().add(BAutomoviles("Hyundai", "Tucson", 25000.0, true))
            arregloBTiendaAutomoviles.get(1).getAutomoviles().add(BAutomoviles("Kia", "Sportage", 26000.0, true))
            arregloBTiendaAutomoviles.get(1).getAutomoviles().add(BAutomoviles("Tesla", "Model S", 80000.0, false))

        }
    }
}