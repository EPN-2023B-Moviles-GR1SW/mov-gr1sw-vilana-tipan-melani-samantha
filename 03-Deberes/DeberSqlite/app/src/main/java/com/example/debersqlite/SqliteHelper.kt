package com.example.debersqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.text.SimpleDateFormat

class SqliteHelper (
    contexto: Context?, 
) : SQLiteOpenHelper(
    contexto,
    "DeberSqlite",
    null,
    5
){

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaTienda =
            """
                CREATE TABLE TIENDA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                direccion VARCHAR(50),
                fechaApertura DATE
                )
            """.trimIndent()

        db?.execSQL(scriptSQLCrearTablaTienda)

        val scriptSQLCrearTablaAutomovil =
            """
                CREATE TABLE AUTOMOVIL(
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    modelo VARCHAR(50),
                    precio DOUBLE,
                    autosDisponibles INTEGER,
                    disponibilidad BOOLEAN,
                    idTienda INTEGER,
                    FOREIGN KEY (idTienda) REFERENCES TIENDA(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAutomovil)
    }

    override fun onUpgrade(db: SQLiteDatabase?,  oldVersion:Int, newVersion:Int) {
    }

    fun crearTienda(
        nombre: String,
        direccion: String,
        fechaApertura: String

    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("direccion", direccion)
        valoresAGuardar.put("fechaApertura", fechaApertura)


        val resultadoGuardar = baseDatosEscritura
            .insert(
                "TIENDA",
                null,
                valoresAGuardar
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarTienda(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "TIENDA",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarTienda(
        nombre: String,
        direccion: String,
        fechaApertura: String,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("direccion", direccion)
        valoresAActualizar.put("fechaApertura", fechaApertura)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "TIENDA",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarTiendaPorID(id: Int): BTienda {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM TIENDA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        // logica busqueda
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val BTiendaEncontrada = BTienda(0, "", "", null)
        val arreglo = arrayListOf<BTienda>()
        if (existeTienda) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Índice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaApertura = resultadoConsultaLectura.getString(3)

                if (id != null) {

                    BTiendaEncontrada.id = id
                    BTiendaEncontrada.nombre = nombre
                    BTiendaEncontrada.direccion = direccion
                    BTiendaEncontrada.fechaApertura = formatoFecha.parse(fechaApertura)

                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return BTiendaEncontrada
    }

    fun consultarTiendas(): ArrayList<BTienda> {
        val scriptConsultarTiendas = "SELECT * FROM TIENDA"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarTiendas,
            null
        )
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val arregloBTienda = arrayListOf<BTienda>()
        if (existeTienda) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaApertura = resultadoConsultaLectura.getString(3)
                arregloBTienda.add(
                    BTienda(
                        id,
                        nombre,
                        direccion,
                        formatoFecha.parse(fechaApertura)
                    )
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloBTienda
    }

    // AUTOMOVIL
    fun crearAutomovil(
        modelo: String,
        precio: Double,
        autosDisponibles: Int,
        idTienda: Int
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("modelo", modelo)
        valoresAGuardar.put("precio", precio)
        valoresAGuardar.put("autosDisponibles",autosDisponibles)
        valoresAGuardar.put("idTienda", idTienda)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "AUTOMOVIL",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarAutomovil(idProducto: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idProducto.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "AUTOMOVIL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun actualizarAutomovil(
        modelo: String,
        precio: Double,
        autosDisponibles: Int,
        idTienda: Int,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("modelo", modelo)
        valoresAActualizar.put("precio", precio)
        valoresAActualizar.put("autosDisponibles", autosDisponibles)
        valoresAActualizar.put("idTienda", idTienda)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "AUTOMOVIL",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarAutomovilPorID(id: Int): BAutomovil {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM AUTOMOVIL WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val existeAutomovil = resultadoConsultaLectura.moveToFirst()
        val BAutomovilEncontrado = BAutomovil(null, "", 0.0, 0,  0)

        if (existeAutomovil) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val modelo = resultadoConsultaLectura.getString(1)
                val precio = resultadoConsultaLectura.getDouble(2)
                val autosDisponibles = resultadoConsultaLectura.getInt(3)
                val idTienda = resultadoConsultaLectura.getInt(4)
                if (id != null) {

                    BAutomovilEncontrado.idAutomovil = id
                    BAutomovilEncontrado.modelo= modelo
                    BAutomovilEncontrado.precio = precio
                    BAutomovilEncontrado.autosDisponibles = autosDisponibles
                }
                BAutomovilEncontrado.idTienda = idTienda
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return BAutomovilEncontrado
    }

    fun consultarAutosPorTienda(idTienda: Int): ArrayList<BAutomovil> {
        val scriptConsultarAutos = "SELECT * FROM AUTOMOVIL WHERE idTienda = ?"
        val baseDatosLectura = readableDatabase
        val parametrosConsultaLectura = arrayOf(idTienda.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarAutos,
            parametrosConsultaLectura
        )
        val existeAutomovil = resultadoConsultaLectura.moveToFirst()
        val arregloBAutomovil = arrayListOf<BAutomovil>()
        if (existeAutomovil) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val modelo = resultadoConsultaLectura.getString(1)
                val precio = resultadoConsultaLectura.getDouble(2)
                val autosDisponibles = resultadoConsultaLectura.getInt(3)
                val idTienda = resultadoConsultaLectura.getInt(4)
                if (id != null) {
                    val BAutomovilEncontrado = BAutomovil(null, "", 0.0, 0, 0)
                    BAutomovilEncontrado.idAutomovil = id
                    BAutomovilEncontrado.modelo = modelo
                    BAutomovilEncontrado.precio = precio
                    BAutomovilEncontrado.autosDisponibles = autosDisponibles
                    BAutomovilEncontrado.idTienda = idTienda
                    arregloBAutomovil.add(BAutomovilEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloBAutomovil
    }
}