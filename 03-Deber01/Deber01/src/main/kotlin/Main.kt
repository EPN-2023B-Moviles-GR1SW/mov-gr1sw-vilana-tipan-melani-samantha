package org.example

fun main() {

    println("Bienvenido")

    var opcion: Int?= null
    do {
        println("\nMenú:")
        println("1. Ver Automóviles")
        println("2. Agregar Automóvil")
        println("3. Actualizar Automóvil")
        println("4. Eliminar Automóvil")
        println("5. Ver Tiendas")
        println("6. Agregar Tienda")
        println("7. Actualizar Tienda")
        println("8. Eliminar Tienda")
        println("9. Salir")
        print("Seleccione una opción: ")

        try {
            opcion = readLine()?.toInt() as Int
            when (opcion) {
                1 -> AutomovilCRUD.verAutomoviles(Archivos.leerAutomoviles())
                2 -> AutomovilCRUD.crearAutomovil(Archivos.leerAutomoviles())
                3 -> AutomovilCRUD.actualizarAutomovil()
                4 -> {
                    println("Ingrese el ID del automovil que desea eliminar")
                    var idAuto = readLine()?.toInt() as Int
                    AutomovilCRUD.eliminarAutomovil(Archivos.leerAutomoviles(),idAuto)
                }
                5 -> TiendaAutomovilCRUD.verTiendas(Archivos.leerTiendas())
                6 -> TiendaAutomovilCRUD.crearTienda(Archivos.leerTiendas())
                7 -> TiendaAutomovilCRUD.actualizarTienda()
                8 ->{
                    println("Ingrese el ID de la tienda que desea eliminar")
                    var idTiendaAuto = readLine()?.toInt() as Int
                    TiendaAutomovilCRUD.eliminarTienda(Archivos.leerTiendas(), idTiendaAuto)
                }
                9 -> println("Saliendo del programa")
                else -> println("Opción no válida. Por favor, elija nuevamente.")
            }
        } catch (e: NumberFormatException) {
            println("Por favor, ingrese un número válido.")
        }

    } while (opcion != 9)

    Archivos.guardarAutomoviles(Archivos.leerAutomoviles())
    Archivos.guardarTiendas(Archivos.leerTiendas())
}