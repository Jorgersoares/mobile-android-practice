package com.example.minhascores

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.minhascores.adapter.ListCorAdapter
import com.example.minhascores.model.Cor
import com.example.minhascores.persistence.CorDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val REQUEST_ADD_COLOR = 1
    val REQUEST_UPDATE_COLOR = 2
    lateinit var btnAdd: FloatingActionButton
    lateinit var listView: ListView
    lateinit var listCor: ArrayList<Cor>
    lateinit var corDAO: CorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.listCor = ArrayList()
        this.btnAdd = findViewById(R.id.cor_add_btn)
        this.btnAdd.setOnClickListener(ClickOnAddBtnListener())
        this.listView = findViewById(R.id.cores)
        this.listView.adapter = ListCorAdapter(this.listCor, this)
        this.listView.onItemClickListener = ClickOnListItemListener()
        this.listView.onItemLongClickListener = ClickLongOnListItemListener()
        this.corDAO = CorDAO(this)

        (this.listView.adapter as ListCorAdapter).addAll(this.corDAO.select())
        (this.listView.adapter as ListCorAdapter).notifyDataSetChanged()
    }

    inner class ClickOnAddBtnListener: View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity, CadastroCorActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_COLOR);
        }
    }

    inner class ClickOnListItemListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val cor = (this@MainActivity.listView.adapter as ListCorAdapter).getItem(position)
            val intent = Intent(this@MainActivity, CadastroCorActivity::class.java)
            intent.putExtra("UPDATE_COLOR", cor)
            intent.putExtra("UPDATE_COLOR_INDEX", position)
            startActivityForResult(intent, REQUEST_UPDATE_COLOR)
        }
    }

    inner class ClickLongOnListItemListener: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ): Boolean {
            this@MainActivity.corDAO.delete((listView.adapter as ListCorAdapter).getItem(position).id)
            (this@MainActivity.listView.adapter as ListCorAdapter).remove(position)
            (this@MainActivity.listView.adapter as ListCorAdapter).notifyDataSetChanged()
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_ADD_COLOR){
                val cor = data?.getSerializableExtra("ADDED_COLOR") as Cor
                (this.listView.adapter as ListCorAdapter).add(cor)
                (this.listView.adapter as ListCorAdapter).notifyDataSetChanged()
                Toast.makeText(this, "Cor salva com sucesso!", Toast.LENGTH_SHORT).show()
            }
            if(requestCode == REQUEST_UPDATE_COLOR){
                val cor = data?.getSerializableExtra("ADDED_COLOR") as Cor
                val index = data.getIntExtra("INDEX_UPDATE_COLOR", -1)
                (this.listView.adapter as ListCorAdapter).update(index, cor)
                (this.listView.adapter as ListCorAdapter).notifyDataSetChanged()
                }
            }
        }
}
