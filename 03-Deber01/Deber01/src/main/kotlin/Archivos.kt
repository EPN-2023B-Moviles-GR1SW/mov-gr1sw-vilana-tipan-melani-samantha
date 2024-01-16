package org.example

import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat

class Archivos {
    companion object {
        fun guardarAutomoviles(automoviles: ArrayList<Automovil>) {
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter("archivoAutomoviles.txt")
                fileWriter.append("Automoviles \n")

                for (auto in automoviles) {

                    fileWriter.append(auto.idAutomovil.toString())
                    fileWriter.append('|')
                    fileWriter.append(auto.marca)
                    fileWriter.append('|')
                    fileWriter.append(auto.modelo)
                    fileWriter.append('|')
                    fileWriter.append(auto.precio.toString())
                    fileWriter.append('|')
                    fileWriter.append(auto.disponibilidad.toString())
                    fileWriter.append('|')
                    fileWriter.append(auto.tienda.toString())
                    fileWriter.append("\n")
                }

            } catch (e: Exception) {
                println("Error al escribir en el archivo!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Error al cerrar el FileWriter!")
                    e.printStackTrace()
                }
            }
        }

        fun leerAutomoviles(): ArrayList<Automovil> {
            var fileReader: BufferedReader? = null
            val automoviles = ArrayList<Automovil>()
            var line: String?
            try {
                fileReader = BufferedReader(FileReader("archivoAutomoviles.txt"))
                fileReader.readLine()
                line = fileReader.readLine()
                while (line != null) {
                    val tokens = line.split("|")
                    if (tokens.size > 0) {
                        val automovil = Automovil(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            tokens[2],
                            tokens[3].toDouble(),
                            tokens[4].toBoolean(),
                            tokens[5].toInt()
                        )
                        automoviles.add(automovil)
                        line = fileReader.readLine()
                    }
                   // line = fileReader.readLine()
                }
            } catch (e: Exception) {
                println("Error al leer el archivo!")
                e.printStackTrace()
            } finally {
                try {
                    fileReader?.close()
                } catch (e: IOException) {
                    println("Error al cerrar el BufferedReader!")
                    e.printStackTrace()
                }
            }
            return automoviles
        }
        fun guardarTiendas(tiendas: ArrayList<TiendaAutomovil>) {
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter("archivoTiendas.txt")


                for (tienda in tiendas) {
                    fileWriter.append("Tienda de automoviles \n")
                    fileWriter.append(tienda.idTienda.toString())
                    fileWriter.append('|')
                    fileWriter.append(tienda.nombre)
                    fileWriter.append('|')
                    fileWriter.append(tienda.direccion)
                    fileWriter.append('|')
                    fileWriter.append(SimpleDateFormat("dd/MM/yyyy").format(tienda.fechaApertura))
                    fileWriter.append('|')
                    fileWriter.append("Lista Automoviles: ")
                    fileWriter.append(tienda.arregloAutomoviles.toString())
                    fileWriter.append("\n")

                }
            } catch (e: Exception) {
                println("Error al escribir en el archivo!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Error al cerrar el FileWriter!")
                    e.printStackTrace()
                }
            }
        }

        fun leerTiendas(): ArrayList<TiendaAutomovil> {
            var fileReader: BufferedReader? = null
            val tiendas = ArrayList<TiendaAutomovil>()
            var line: String?
            try {
                fileReader = BufferedReader(FileReader("archivoTiendas.txt"))
                fileReader.readLine()
                line = fileReader.readLine()
                while (line != null) {
                    val tokens = line.split("|")

                    if (tokens.size > 0) {
                        val tienda = TiendaAutomovil(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            tokens[2],
                            SimpleDateFormat("dd/MM/yyyy").parse(tokens[3]),
                            AutomovilCRUD.listarAutomoviles(Integer.parseInt(tokens[0]), leerAutomoviles())
                        )
                        tiendas.add(tienda)
                        line = fileReader.readLine()
                    }

                    line = fileReader.readLine()
                }
            } catch (e: Exception) {
                println("Error al leer el archivo!")
                e.printStackTrace()
            } finally {
                try {
                    fileReader?.close()
                } catch (e: IOException) {
                    println("Error al cerrar el BufferedReader!")
                    e.printStackTrace()
                }
            }
            return tiendas
        }


    }
}
