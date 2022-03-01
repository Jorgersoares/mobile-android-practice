package com.example.minhascores.persistence

import android.content.Context
import android.database.sqlite.*

class BancoHelper(context: Context) : SQLiteOpenHelper(context, "cores.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql_table = "create table cores( "+
                "id integer primary key autoincrement, " +
                "nome text, "+
                "codigo integer ) "
        db?.execSQL(sql_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql_table = "drop table cores"
        db?.execSQL(sql_table)
        this.onCreate(db)
    }

}