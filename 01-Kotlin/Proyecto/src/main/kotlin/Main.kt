import java.util.Date

fun main() {
    println("Hola Mundo");
    //Tipos de variables
    //INMUTABLE (No se reasignan "=")
    val inmutable: String = "Adrian";
    //inmutable = "Vicente";

    //Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //val >var
    //Duck typing
    //si algo parece un pato posiblemente es un pato

    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //USAR SIEMPRE VAL

    //Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clase Java
    val fechaNacimiento: Date = Date();

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    //void  -> Unit
    //fun nombre funcion (nombre del parametro: tipo): tipo de funcion (void no devuelve nada)
    fun imprimirNombre(nombre: String): Unit {
        // template strings
        //"Bienvenido: "+ nombre + " "
        println("Nombre : ${nombre}")
    }

    //funcion calcular sueldo
    fun calcularSueldo(
        sueldo: Double, //Requerido
        tasa: Double = 12.00, //Opcional (defecto)
        bonoEspecial: Double? = null, //opcion null -> nullable
    ): Double {
        //Int -> Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)

        if (bonoEspecial == null) {
            return sueldo * (100 / tasa)
        } else {
            bonoEspecial.dec()//decrementar

            return sueldo * (100 / tasa) + bonoEspecial
        }
    }

    //parametros nombrados
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) //Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)//Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumatres = Suma(1, null)

}

//clases
//Java
abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){//Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}
//Kotlin
abstract class Numeros( //constructor PRIMARIO
    //ejemplo:
    //uno: Int, (Parametro(sin modificador de acceso))
    //private var uno: Int, //Propiedad Publica Clase numeros, uno
    //var uno: Int, //Propiedad de la clase (por defecto es PUBLIC)
    //public var uno: Int,
    //Propiedad de la clase protected numeros.numerosUno
    protected val numeroUno: Int,
    //Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
){
    //var cedula: String = "" (public es por defecto)
    //private valorCalculado: Int = 0 (private)
    init{
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos; // sin el "this", es  lo mismo
        println("Inicializando")
    }
}

class Suma( //Constructor Primario Suma
    uno: Int, //Parametro
    dos: Int, //Parametro
) :Numeros(uno, dos){ // <- Constructor del Padre
    init{ //Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }
    constructor( //Segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ) : this( //llamada constructor primario
        if(uno == null) 0 else uno,
        dos
    ){ // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor( // tercer constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ) : this( //llamada constructor primario
        uno,
        if(dos == null) 0 else uno
    ) // si no lo necesitamos al bloque de codigo "{}" lo omitimos
}

