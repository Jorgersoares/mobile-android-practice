package com.example.idade

import android.content.Intent
import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btnCalc: Button
    private lateinit var nomeInput: EditText
    private lateinit var anoInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nomeInput = findViewById(R.id.input_nome)
        anoInput = findViewById(R.id.input_ano)
        btnCalc = findViewById(R.id.btnCalc)
        btnCalc.setOnClickListener({clickBtnCalc(it)})
    }

    fun clickBtnCalc(view: View){
        val nome = nomeInput.text.toString()
        val ano = anoInput.text.toString()
        if(nome != "" && ano != ""){
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("pessoa", Pessoa(nome,Integer.parseInt(ano)))
            startActivity(intent)
        }
        else{
            Toast.makeText(
                this,
                "Por favor preencha todos os campos!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}