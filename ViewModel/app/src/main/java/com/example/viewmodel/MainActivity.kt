package com.example.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        val tvHint = findViewById<TextView>(R.id.tvHint)
        val edAccount = findViewById<TextView>(R.id.edAccount)
        val edPassword = findViewById<TextView>(R.id.edPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener(){
            viewModel.registerAccount(
                edAccount.text.toString(),
                edPassword.text.toString()
            )
        }


        viewModel.registerResult.observe(this){ result ->
            Toast.makeText(
                this,result.second,Toast.LENGTH_SHORT
            ).show()

            if(result.first){
                tvHint.text = result.second
                tvHint.setTextColor(
                    getColor(android.R.color.holo_green_dark)
                )
                val i = Intent(this,activity_sec::class.java)
                startActivity(i)
            }else{
                tvHint.text = "註冊失敗：${result.second}"
                tvHint.setTextColor(
                    getColor(android.R.color.holo_red_dark)
                )
            }

        }
    }
}