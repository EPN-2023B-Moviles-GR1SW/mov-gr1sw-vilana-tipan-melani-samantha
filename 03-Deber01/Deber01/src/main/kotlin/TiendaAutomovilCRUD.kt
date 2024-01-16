package org.example

import java.io.*
import java.text.SimpleDateFormat

class TiendaAutomovilCRUD {
    companion object{
        fun crearTienda(arregloTienda: ArrayList<TiendaAutomovil>) {
            println("Ingrese la información de la tienda")
            println("ID:")
            val idTienda = readLine()?.toInt() as Int
            println("Nombre:")
            val nombreTienda = readLine()?.toString() as String
            println("Dirección:")
            val direccionTienda = readLine()?.toString() as String
            println("Fecha de Apertura (dd/MM/yyyy):")
            val fechaAperturaTienda =
                SimpleDateFormat("dd/MM/yyyy").parse(readLine()?.toString() as String)
            arregloTienda.add(
                TiendaAutomovil(
                    idTienda = idTienda,
                    nombre = nombreTienda,
                    direccion = direccionTienda,
                    fechaApertura = fechaAperturaTienda,
                    null
                )
            )
            Archivos.guardarTiendas(arregloTienda)
        }
        fun verTiendas(arregloTienda: ArrayList<TiendaAutomovil>){
            println("Lista de tiendas")
            println(arregloTienda)
        }
        fun eliminarTienda(arregloTienda: ArrayList<TiendaAutomovil>, idTienda: Int){
            with(arregloTienda.iterator()){
                forEach {
                    if(it.idTienda == idTienda){
                        remove()
                        println("Tienda eliminada con exito!!")
                    }else{
                        println("No se encontro la tienda")
                    }
                }
            }
            Archivos.guardarTiendas(arregloTienda)
        }
        fun actualizarTienda() {
            println("Ingrese el ID de la tienda que desea actualizar:")
            val idTiendaE = readLine()?.toIntOrNull()

            if (idTiendaE != null) {

                val archivoPath = "archivoTiendas.txt"
                val archivoTempPath = "archivo_temp_tiendas.txt"

                try {
                    val reader = BufferedReader(FileReader(archivoPath))
                    val writer = BufferedWriter(FileWriter(archivoTempPath))

                    var line: String?
                    var encontrado = false

                    while (reader.readLine().also { line = it } != null) {
                        val tokens = line!!.split("|")
                        if (tokens.isNotEmpty() && tokens[0].toIntOrNull() == idTiendaE) {

                            encontrado = true

                            println("Ingrese el nuevo nombre:")
                            val nuevoNombre = readLine()?.toString() ?: tokens[1]

                            println("Ingrese la nueva dirección:")
                            val nuevaDireccion = readLine()?.toString() ?: tokens[2]

                            println("Ingrese la nueva fecha de apertura (dd/MM/yyyy):")
                            val nuevoFormatoFecha = SimpleDateFormat("dd/MM/yyyy")
                            val nuevaFechaAperturaString = readLine()?.toString() ?: tokens[3]
                            val nuevaFechaApertura = nuevoFormatoFecha.parse(nuevaFechaAperturaString)


                            writer.write("$idTiendaE|$nuevoNombre|$nuevaDireccion|${SimpleDateFormat("dd/MM/yyyy").format(nuevaFechaApertura)}\n")
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
                        println("No se encontró la tienda con el ID proporcionado.")
                    } else {
                        println("Tienda actualizada con éxito.")
                    }
                } catch (e: IOException) {
                    println("Error al actualizar la tienda.")
                    e.printStackTrace()
                }
            } else {
                println("ID no válido. La actualización ha sido cancelada.")
            }
        }






    }
}