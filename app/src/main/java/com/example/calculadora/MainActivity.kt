package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculadora.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.boton0.setOnClickListener {
            binding.pantalla.append("0")
        }
        binding.boton1.setOnClickListener {
            binding.pantalla.append("1")
        }
        binding.boton2.setOnClickListener {
            binding.pantalla.append("2")
        }
        binding.boton3.setOnClickListener {
            binding.pantalla.append("3")
        }
        binding.boton4.setOnClickListener {
            binding.pantalla.append("4")
        }
        binding.boton5.setOnClickListener {
            binding.pantalla.append("5")
        }
        binding.boton6.setOnClickListener {
            binding.pantalla.append("6")
        }
        binding.boton7.setOnClickListener {
            binding.pantalla.append("7")
        }
        binding.boton8.setOnClickListener {
            binding.pantalla.append("8")
        }
        binding.boton9.setOnClickListener {
            binding.pantalla.append("9")
        }
        binding.coma.setOnClickListener {
            if(binding.pantalla.text.toString() != "") binding.pantalla.append(".")
        }
        binding.suma.setOnClickListener {
            if(binding.pantalla.text.toString() != "") binding.pantalla.append("+")
        }
        binding.multiplicacion.setOnClickListener {
            if(binding.pantalla.text.toString() != "") binding.pantalla.append("*")
        }
        binding.resta.setOnClickListener {
            binding.pantalla.append("-")
        }
        binding.division.setOnClickListener {
            if(binding.pantalla.text.toString() != "") binding.pantalla.append("/")
        }
        binding.clear.setOnClickListener {
            binding.pantalla.text = ""
        }
        binding.igual.setOnClickListener {
            if(binding.pantalla.text.toString() != "") {
                try {
                    //Lo multiplicamos por 0 y, Si es divisible por 0, lo redondeamos
                    var resultado = calculo(binding.pantalla.text.toString()) * 10
                    if (resultado % 10.0 == 0.0) {
                        binding.pantalla.text = (resultado.toInt() / 10).toString()
                    } else {
                        val cadenaNum : String = (resultado / 10).toString()
                        //cortar el número a x decimales
                       if(cadenaNum.length<12) binding.pantalla.text = cadenaNum else binding.pantalla.text = cadenaNum.substring(0, 12)
                    }
                }catch(ex: Exception){
                    binding.pantalla.text = "Err"
                }
            }
        }

    }
}

fun calculo(cadena: String): Double{
    //Añadimos un caracter terminador
    var expresion = cadena + "$"
    var numero = "0"
    var numeros = ArrayList<Double>()
    var operandos = ArrayList<Char>()

    for(char in expresion) {
        if(char in "+-*/$") {
            numeros.add(numero.toDouble())
            numero = "0"
            continue
        }else{
            numero += char
        }
    }
    for (char in expresion){
        if(char in "+-*/") operandos.add(char)
    }


    if(expresion.startsWith('*') || expresion.startsWith('+') || expresion.startsWith('/')){
        numeros.removeAt(0)
        operandos.removeAt(0)
    }

    //hay 6 operandos y 7 números de forma alternada.
    // [1,2,3,4,5,6,7]
    //  [+,-,*,/,-,*]
    var i = 0
    while(i < operandos.size){
        when(operandos[i]){
            '*'-> {
                numeros[i] = numeros[i] * numeros[i + 1]
                numeros.removeAt(i+1)
                operandos.removeAt(i)
                i--
            }
            '/'->{
                numeros[i] = numeros[i] / numeros[i + 1]
                numeros.removeAt(i+1)
                operandos.removeAt(i)
                i--
            }
        }
        i++
    }
    i = 0
    while(i < operandos.size){
        when(operandos[i]){
            '+'-> {
                numeros[i] = numeros[i] + numeros[i + 1]
                numeros.removeAt(i+1)
                operandos.removeAt(i)
                i--
            }
            '-'->{
                numeros[i] = numeros[i] - numeros[i + 1]
                numeros.removeAt(i+1)
                operandos.removeAt(i)
                i--
            }
        }
        i++
    }
    return numeros[0]
}