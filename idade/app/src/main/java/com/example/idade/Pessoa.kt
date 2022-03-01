package com.example.idade

import java.io.Serializable
import java.util.*

class Pessoa: Serializable{
    var nome: String
    var ano: Int
    var actual_year: Int

    constructor(nome: String, ano: Int){
        this.nome = nome
        this.ano = ano
        actual_year = Calendar.getInstance().get(Calendar.YEAR)
    }

    fun idade(): Int{
        return actual_year - ano
    }

}