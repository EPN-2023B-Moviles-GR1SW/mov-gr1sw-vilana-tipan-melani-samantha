package com.example.proyectoiib

class Libro(
    var id: String,
    var tipo: String,
    var titulo: String,
    var autor: String,
    var genero: String,
    var urlLibro: String?,
    var urlImagen: String,
   // var recordatorio: Calendar?
) {

    // Getter para obtener la URL del libro
    fun getUrlLibroString(): String? {
        return this.urlLibro
    }

    // Setter para establecer la URL del libro
    fun setUrlLibroString(url: String) {
        this.urlLibro = url
    }

    fun getImagen() :String{
        return this.urlImagen
    }
/*
    fun getRecordatorioString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formatoFecha.format(this.recordatorio?.time ?: null)
    }

    fun setRecordatorioCalendar(fecha: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        try {
            val date = dateFormat.parse(fecha)
            val calendar = Calendar.getInstance()
            calendar.time = date
            this.recordatorio = calendar
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }*/
}