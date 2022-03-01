package com.example.minhascores.adapter

import com.example.minhascores.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minhascores.model.Cor


class ListCorAdapter(private var list: ArrayList<Cor>, private var context: Context): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cor = this.getItem(position)
        val linha : View

        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            linha = inflater.inflate(R.layout.list_cor_view, null)
        }else{
            linha = convertView
        }

        val icon = linha.findViewById<ImageView>(R.id.list_icon_cor_img)
        val nome = linha.findViewById<TextView>(R.id.list_cor_nome)
        val codigo = linha.findViewById<TextView>(R.id.list_cor_codigo)

        icon.setColorFilter(cor.codigo)
        nome.text = cor.nome
        codigo.text = cor.toHex()

        return linha
    }

    override fun getItem(position: Int): Cor {
        return this.list[position]
    }

    override fun getItemId(position: Int): Long {
        return -1;
    }

    override fun getCount(): Int {
        return this.list.count()
    }

    fun add(cor: Cor){
        this.list.add(cor)
    }

    fun addAll(cores: ArrayList<Cor>){
        this.list.addAll(cores)
    }

    fun remove(position: Int){
        this.list.removeAt(position)
    }

    fun update(position: Int, cor: Cor){
        this.list[position] = cor
    }

}
