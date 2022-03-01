package com.example.myapplication

import Desejo
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class TelaCadastroDesejoActivity : AppCompatActivity() {
    lateinit var descricaoInput: EditText
    lateinit var valorInput: EditText
    lateinit var submitButton: Button
    lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro_desejo)

        descricaoInput = findViewById(R.id.descricao_input)
        valorInput = findViewById(R.id.valor_input)
        submitButton = findViewById(R.id.submit_button)
        cancelButton = findViewById(R.id.cancel_button)

        submitButton.setOnClickListener(submitButtonClick())
        cancelButton.setOnClickListener(cancelButtonClick())

    }

    inner class submitButtonClick: View.OnClickListener {
        override fun onClick(v: View?) {
            var descricao = descricaoInput.text.toString()
            var valor = valorInput.text.toString()
            if(descricao != "" && valor != ""){
                var desejo = Desejo(descricao, valor.toFloat())
                val intent = Intent()
                intent.putExtra("WISH_ADDED", desejo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(
                    this@TelaCadastroDesejoActivity,
                    "Preencha todos os campos!",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    inner class cancelButtonClick: View.OnClickListener {
        override fun onClick(v: View?) {
            finish()
        }

    }
}