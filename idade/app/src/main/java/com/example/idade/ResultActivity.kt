package com.example.idade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private lateinit var textResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        textResult = findViewById(R.id.result_text)
        textResult.setOnClickListener({clickText(it)})

        val pessoa = intent.getSerializableExtra("pessoa") as Pessoa
        var idade = pessoa.idade().toString()
        var nome = pessoa.nome


        textResult.setText("$nome, você possui $idade anos!")
    }

    fun clickText(view: View){
        finish()
    }
}