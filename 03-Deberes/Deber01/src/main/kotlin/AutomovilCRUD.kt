package org.example

import java.io.*

class AutomovilCRUD {
    companion object {
        //val arregloAutomoviles = arrayListOf<Automovil>()

        fun crearAutomovil(arregloAuto: ArrayList<Automovil>) {
            println("Ingrese la informacion del automóvil")
            println("ID:")
            var idAuto = readLine()?.toInt() as Int
            println("Marca:")
            var marcaAuto = readLine()?.toString() as String
            println("Modelo:")
            var modeloAuto = readLine()?.toString() as String
            println("Precio")
            var precioAuto = readLine()?.toDouble() as Double
            println("Disponibilidad: (Sí: 1, No: 2)")
            var x: Int
            var disponibilidad: Boolean = false
            x = readLine()?.toInt() as Int
            when (x) {
                1 -> disponibilidad = true
                2 -> disponibilidad= false
                else -> println("Opcion invalida")
            }

            println("Tienda")
            var tiendaAuto = readLine()?.toInt() as Int

            arregloAuto.add(
                Automovil(
                    idAutomovil = idAuto,
                    marca = marcaAuto,
                    modelo = modeloAuto,
                    precio = precioAuto,
                    disponibilidad = disponibilidad,
                    tienda = tiendaAuto
                )
            )

            Archivos.guardarAutomoviles(arregloAuto)
         
        }


        fun verAutomoviles(arregloAuto: ArrayList<Automovil>){
            println("Lista de automoviles")
            println(arregloAuto)
        }

        fun eliminarAutomovil(arregloAuto: ArrayList<Automovil>, idAutoE: Int) {

            with(arregloAuto.iterator()){
                forEach {
                    if(it.idAutomovil == idAutoE){
                        remove()
                        println("Automovil eliminado con exito!!")
                    }
                }
            }
            Archivos.guardarAutomoviles(arregloAuto)
            Archivos.guardarTiendas(Archivos.leerTiendas())
        }


        fun actualizarAutomovil() {
            println("Ingrese el ID del automóvil que desea actualizar:")
            val idAutoE = readLine()?.toIntOrNull()

            if (idAutoE != null) {

                val archivoPath = "archivoAutomoviles.txt"
                val archivoTempPath = "archivo_temp.txt"

                try {
                    val reader = BufferedReader(FileReader(archivoPath))
                    val writer = BufferedWriter(FileWriter(archivoTempPath))

                    var line: String?
                    var encontrado = false

                    while (reader.readLine().also { line = it } != null) {
                        val tokens = line!!.split("|")
                        if (tokens.isNotEmpty() && tokens[0].toIntOrNull() == idAutoE) {

                            encontrado = true

                            println("Ingrese la nueva marca:")
                            val nuevaMarca = readLine()?.toString() ?: tokens[1]

                            println("Ingrese el nuevo modelo:")
                            val nuevoModelo = readLine()?.toString() ?: tokens[2]

                            println("Ingrese el nuevo precio:")
                            val nuevoPrecio = readLine()?.toDoubleOrNull() ?: tokens[3].toDouble()

                            println("Ingrese la nueva disponibilidad: (Sí: 1, No: 2)")
                            var x: Int
                            var nuevaDisponibilidad: Boolean = tokens[4].toBoolean()
                            x = readLine()?.toIntOrNull() ?: 0

                            when (x) {
                                1 -> nuevaDisponibilidad = true
                                2 -> nuevaDisponibilidad = false
                                else -> println("Opción inválida")
                            }

                            println("Ingrese la nueva tienda:")
                            val nuevaTienda = readLine()?.toIntOrNull() ?: tokens[5].toInt()


                            writer.write("$idAutoE|$nuevaMarca|$nuevoModelo|$nuevoPrecio|$nuevaDisponibilidad|$nuevaTienda\n")
                        } else {

                            writer.write("$line\n")
                        }
                    }

                    reader.close()
                    writer.close()


                    val archivoOriginal = File(archivoPath)
                    val archivoTemp = File(archivoTempPath)
                    archivoOriginal.delete()
                    archivoTemp.renameTo(archivoOriginal)

                    if (!encontrado) {
                        println("No se encontró el automóvil con el ID proporcionado.")
                    } else {
                        println("Automóvil actualizado con éxito.")
                    }
                } catch (e: IOException) {
                    println("Error al actualizar el automóvil.")
                    e.printStackTrace()
                }
            } else {
                println("ID no válido. La actualización ha sido cancelada.")
            }
        }
        fun listarAutomoviles(idTiendaAutomovil: Int, arregloAuto: ArrayList<Automovil>):ArrayList<Automovil>{
            val arregloAutoTienda = arrayListOf<Automovil>()
            arregloAuto.forEach{
                valorIteracion ->
                if(valorIteracion.tienda == idTiendaAutomovil){
                    arregloAutoTienda.add(valorIteracion)
                }
            }
            return  arregloAutoTienda
        }








    }
}