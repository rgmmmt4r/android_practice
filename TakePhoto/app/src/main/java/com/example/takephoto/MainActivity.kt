package com.example.takephoto

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var angle = 0f
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ){bitmap: Bitmap?->
        if (bitmap != null){
            findViewById<ImageView>(R.id.imgPhoto).setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnCapture).setOnClickListener(){
            try {
                startForResult.launch(null)
            }catch (e : ActivityNotFoundException){
                Toast.makeText(this,"無相機應用程式",Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnRotate).setOnClickListener(){
            angle += 90f
            findViewById<ImageView>(R.id.imgPhoto).rotation = angle
        }
    }
}