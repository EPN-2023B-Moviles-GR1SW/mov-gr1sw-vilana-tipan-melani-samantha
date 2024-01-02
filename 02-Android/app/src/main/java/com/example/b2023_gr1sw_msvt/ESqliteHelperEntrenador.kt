package com.example.b2023_gr1sw_msvt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?, //THIS
    ) : SQLiteOpenHelper(
        contexto,
        "moviles", // nombre BDD
        null,
        1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {

        val scriptSQLCrearTablaentrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre varchar(50),
                descripcion varchar(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaentrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun crearEntrenador(
        nombre:String,
        descripcion:String
    ):Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", //Nombre Tabla
                null,
                valoresAGuardar //valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }
    fun eliminarEntrenador(id:Int):Boolean {
        val conexionEscritura = writableDatabase
        //where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString() )
        val resultadoEliminacion = conexionEscritura
            //delete from id = 1 ===>[1]
            .delete(
                "ENTRENADOR", //Nombre Tabla
                "id=?",// Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenador(
        nombre:String,
        descripcion: String,
        id:Int):Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        //where ID=?
        val parametrosConsultaActualizar = arrayOf( id.toString())
         val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", //Nombre Tabla
                valoresAActualizar,// Valores
                "id=?", //consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarEntrenadorPorID(id:Int): BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura =
            """
            SELECT * FROM ENTRENADOR WHERE ID = ?
            """
            .trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura, //Parametros
        )

        //logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0,"","")
        val arreglo = arrayOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)// Indice
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id!= null){
                    //llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }



}