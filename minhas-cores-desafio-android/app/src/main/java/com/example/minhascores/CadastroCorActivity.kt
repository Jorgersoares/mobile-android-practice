package com.example.minhascores

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.minhascores.model.Cor
import com.example.minhascores.persistence.CorDAO

class CadastroCorActivity : AppCompatActivity() {
    lateinit var nomeInput: EditText
    lateinit var redSeek: SeekBar
    lateinit var greenSeek: SeekBar
    lateinit var blueSeek: SeekBar
    lateinit var btnSubmit: Button
    lateinit var btnCancel: Button
    lateinit var btnPreview: Button
    lateinit var corDAO: CorDAO
    var indiceListaCorParaAtualizar: Int = -1
    var objetoCorParaAtualizar: Cor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cor)

        this.nomeInput = findViewById(R.id.cor_nome_input)
        this.redSeek = findViewById(R.id.seek_red)
        this.greenSeek = findViewById(R.id.seek_green)
        this.blueSeek = findViewById(R.id.seek_blue)
        this.btnSubmit = findViewById(R.id.submit_button)
        this.btnCancel = findViewById(R.id.cancel_button)
        this.btnPreview = findViewById(R.id.btn_preview_color)

        this.corDAO = CorDAO(this)

        this.btnSubmit.setOnClickListener(ClickOnSubmitButtonListener())
        this.btnCancel.setOnClickListener(ClickOnCancelButtonListener())
        this.redSeek.setOnSeekBarChangeListener(ChangeSeekBarsListener())
        this.greenSeek.setOnSeekBarChangeListener(ChangeSeekBarsListener())
        this.blueSeek.setOnSeekBarChangeListener(ChangeSeekBarsListener())
        this.btnPreview.setOnClickListener(ClickOnPreviewButton())

        this.updatePreview(redSeek.progress, greenSeek.progress, blueSeek.progress)

        if (intent.hasExtra("UPDATE_COLOR") && intent.hasExtra("UPDATE_COLOR_INDEX")) {
            this.fillForm(
                    intent.getSerializableExtra("UPDATE_COLOR") as Cor,
                    intent.getIntExtra("UPDATE_COLOR_INDEX", -1))
        }

    }

    inner class ClickOnSubmitButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            var nome = nomeInput.text.toString()
            var codigo = Color.rgb(redSeek.progress, greenSeek.progress, blueSeek.progress)
            var cor = Cor(nome, codigo)
            var intent = Intent()
            if (this@CadastroCorActivity.indiceListaCorParaAtualizar != -1 && this@CadastroCorActivity.objetoCorParaAtualizar != null) {
                cor.id = this@CadastroCorActivity.objetoCorParaAtualizar?.id!!
                this@CadastroCorActivity.corDAO.update(cor)
                intent.putExtra("INDEX_UPDATE_COLOR", indiceListaCorParaAtualizar)
            } else {
                val corId = corDAO.insert(cor).toInt()
                cor.id = corId
            }
            intent.putExtra("ADDED_COLOR", cor)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    inner class ClickOnCancelButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            finish()
        }
    }

    inner class ChangeSeekBarsListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            this@CadastroCorActivity.updatePreview(redSeek.progress, greenSeek.progress, blueSeek.progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    inner class ClickOnPreviewButton: View.OnClickListener{
        override fun onClick(v: View?) {
            val hex = this@CadastroCorActivity.btnPreview.text
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", hex)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                    this@CadastroCorActivity,
                    "Copiado: $hex",
                    Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun updatePreview(redValue: Int, greenValue: Int, blueValue: Int) {
        val rgb = Color.rgb(redValue, greenValue, blueValue)
        btnPreview.setBackgroundColor(rgb)
        btnPreview.text = Cor.toHex(rgb)
    }

    private fun fillForm(cor: Cor, index: Int) {
        if (cor != null) {
            this.nomeInput.setText(cor.nome)
            this.btnPreview.setBackgroundColor(cor.codigo)
            this.redSeek.progress = Color.red(cor.codigo)
            this.greenSeek.progress = Color.green(cor.codigo)
            this.blueSeek.progress = Color.blue(cor.codigo)
            this.indiceListaCorParaAtualizar = index
            this.objetoCorParaAtualizar = cor
        }
    }
}