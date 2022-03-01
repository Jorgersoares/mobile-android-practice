package com.example.myapplication

import Desejo
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var REQUEST_ADD_WISH = 1
    lateinit var wishList: ListView
    private lateinit var wishAddButton: FloatingActionButton
    private lateinit var wishListArray: ArrayList<Desejo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wishAddButton = findViewById(R.id.wish_add_btn)
        wishList = findViewById(R.id.wish_lv)

        wishListArray = ArrayList()

        wishAddButton.setOnClickListener(ClickOnWishAddButton())
        wishList.adapter = ArrayAdapter<Desejo>(
            this,
            android.R.layout.simple_list_item_1,
            wishListArray
        )
    }

    inner class ClickOnWishAddButton: View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(
                this@MainActivity,
                TelaCadastroDesejoActivity::class.java
            )
            startActivityForResult(intent, REQUEST_ADD_WISH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode === Activity.RESULT_OK){
            if(requestCode === REQUEST_ADD_WISH){
                val wish = data?.getSerializableExtra("WISH_ADDED") as Desejo
                (wishList.adapter as ArrayAdapter<Desejo>).add(wish)
            }
        }

    }
}