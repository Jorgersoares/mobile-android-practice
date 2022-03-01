package com.example.minhascores.persistence

import android.content.ContentValues
import android.content.Context
import com.example.minhascores.model.Cor

class CorDAO {
    private lateinit var banco: BancoHelper

    constructor(context: Context){
        this.banco = BancoHelper(context)
    }

    fun insert(cor: Cor): Long{
        val contentValues = ContentValues()
        contentValues.put("nome", cor.nome)
        contentValues.put("codigo", cor.codigo)
        return this.banco.writableDatabase.insert("cores", null, contentValues)
    }

    fun select(): ArrayList<Cor>{
        val lista = ArrayList<Cor>()
        val colunas = arrayOf("id", "nome", "codigo")

        val cursor = this.banco.readableDatabase.query("cores", colunas, null, null, null, null, "nome")
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))
            lista.add(Cor(id, nome, codigo))
        }
        cursor.close()
        return lista
    }

//    fun count(): Int{
//        val sql = "select count(id) from pessoas"
//        val cursor = this.banco.readableDatabase.rawQuery(sql, null)
//        cursor.moveToFirst()
//        return cursor.getInt(0)
//    }

    fun find(id: Int): Cor?{
        val colunas = arrayOf("id", "nome", "codigo")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())

        val cursor = this.banco.readableDatabase.query("cores", colunas, where, pWhere, null, null, null)
        cursor.moveToFirst()

        if (cursor.count == 1){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))
            return Cor(id, nome, codigo)
        }

        return null
    }

    fun update(cor: Cor){
        val where = "id = ?"
        val pWhere = arrayOf(cor.id.toString())
        val contentValues = ContentValues()
        contentValues.put("nome", cor.nome)
        contentValues.put("codigo", cor.codigo)
        this.banco.writableDatabase.update("cores", contentValues, where, pWhere)
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("cores", where, pWhere)
    }
}