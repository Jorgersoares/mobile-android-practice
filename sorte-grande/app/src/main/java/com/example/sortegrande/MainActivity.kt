package com.example.sortegrande

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var botao : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        botao = findViewById(R.id.btn)
        botao.setOnClickListener({ gerarNumeros(it) })
    }

    fun gerarNumeros(view: View){
        var random = Random()
        var setNumeros = mutableSetOf<Int>()
        while(setNumeros.size < 6){
            var numero = random.nextInt(61)
            if(numero > 0){
                setNumeros.add(numero)
            }
        }
        Log.i("SORTEGRANDE", setNumeros.joinToString(","))
    }
}