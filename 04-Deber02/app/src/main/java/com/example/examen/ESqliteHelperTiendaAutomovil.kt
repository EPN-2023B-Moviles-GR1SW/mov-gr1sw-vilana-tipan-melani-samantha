package com.example.examen

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperTiendaAutomovil (
    val contexto: Context?, //THIS
): SQLiteOpenHelper(
    contexto,
    "examen",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaTiendas =
            """
               CREATE TABLE TIENDAAUTOMOVIL(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               direccion VARCHAR(50),
               fecha VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaTiendas)
        val scriptSQLCrearTablaAutomovil =
            """
               CREATE TABLE TABLAAUTOMOVIL(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               marca VARCHAR(50),
               modelo VARCHAR(50),
               precio DOUBLE
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAutomovil)

    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}

    fun crearTienda(
        nombre: String,
        direccion:String,
        fechaApertura: String
    ):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("direccion", direccion)
        valoresAGuardar.put("fechaApertura", fechaApertura)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "TIENDAAUTOMOVIL", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarTiendaFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        //where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "TIENDAAUTOMOVIL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun actualizarTiendaFormulario(
        nombre: String,
        direccion: String,
        fechaApertura: String,
        id:Int,

    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("direccion", direccion)
        valoresAActualizar.put("fechaApertura", fechaApertura)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "TIENDAAUTOMOVIL", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarTiendaPorID(id: Int): BTiendaAutomoviles{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM TIENDAAUTOMOVIL WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        // logica busqueda
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val tiendaEncontrada = BTiendaAutomoviles(0,"", "" , "")
        //val arreglo = arrayListOf<BTiendaAutomoviles>()
        if(existeTienda){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaApertura = resultadoConsultaLectura.getString(3)
                if(id != null){

                    tiendaEncontrada.id = id
                    tiendaEncontrada.nombre = nombre
                    tiendaEncontrada.direccion = direccion
                    tiendaEncontrada.setFechaApertura(fechaApertura)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return tiendaEncontrada
    }

    fun consultarTiendasAutomovil():ArrayList<BTiendaAutomoviles>{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM TIENDAAUTOMOVIL
        """.trimIndent()


        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null,
        )
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val tiendasAutomovil = ArrayList<BTiendaAutomoviles>()
        if(existeTienda){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaApertura = resultadoConsultaLectura.getString(3)

                val tiendaAuto = BTiendaAutomoviles(id,nombre, direccion, fechaApertura)
                tiendasAutomovil.add(tiendaAuto)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return tiendasAutomovil
    }

    //CRUD AUTOMOVIL

    fun crearAutomovil(
        marca: String,
        modelo: String,
        precio: Double
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("marca", marca)
        valoresAGuardar.put("modelo", modelo)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "AUTOMOVIL", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarAutomovilFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        //where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "AUTOMOVIL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun actualizarAutomovilFormulario(
        marca: String,
        modelo: String,
        precio: Double,
        id:Int,

        ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("marca", marca)
        valoresAActualizar.put("modelo", modelo)
        valoresAActualizar.put("precio", precio)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "AUTOMOVIL", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarAutomovilPorID(id: Int): BAutomoviles{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM AUTOMOVIL WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        // logica busqueda
        val existeAutomovil= resultadoConsultaLectura.moveToFirst()
        val automovilEncontrado = BAutomoviles(0,"","", 0.0)
        //val arreglo = arrayListOf<BTiendaAutomoviles>()
        if(existeAutomovil){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val marca = resultadoConsultaLectura.getString(1)
                val modelo= resultadoConsultaLectura.getString(2)
                val precio = resultadoConsultaLectura.getDouble(3)
                if(id != null){

                    automovilEncontrado.id = id
                    automovilEncontrado.marca = marca
                    automovilEncontrado.modelo = modelo
                    automovilEncontrado.precio = precio

                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return automovilEncontrado
    }

    fun consultarAutomovilesPorTienda(idTienda:Int): ArrayList<BAutomoviles>{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM AUTOMOVIL WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idTienda.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        val existeAutomovil= resultadoConsultaLectura.moveToFirst()
        val automoviles = ArrayList<BAutomoviles>()
        if(existeAutomovil){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val marca = resultadoConsultaLectura.getString(1)
                val modelo= resultadoConsultaLectura.getString(2)
                val precio = resultadoConsultaLectura.getDouble(3)

                val automovil = BAutomoviles(id, marca, modelo, precio)
                automoviles.add(automovil)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return automoviles
    }

    override fun toString(): String {
        return this.contexto.toString()
    }


}