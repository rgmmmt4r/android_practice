package com.example.animation

import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


        val imgFrame = findViewById<ImageView>(R.id.imgFrame)
        val imgTween = findViewById<ImageView>(R.id.imgTween)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnAlpha = findViewById<Button>(R.id.btnAlpha)
        val btnScale = findViewById<Button>(R.id.btnScale)
        val btnTranslate = findViewById<Button>(R.id.btnTranslate)
        val btnRotate = findViewById<Button>(R.id.btnRotate)


        val frameAnim = imgFrame.background as AnimationDrawable

        btnStart.setOnClickListener(){
            frameAnim.start()
        }

        btnStop.setOnClickListener(){
            frameAnim.stop()
        }

        btnAlpha.setOnClickListener(){
            val anim = AlphaAnimation(
                1.0f,
                0.2f
            )
            anim.duration = 1000
            imgTween.startAnimation(anim)
        }

        btnScale.setOnClickListener(){
            val anim = ScaleAnimation(
                1.0f,
                1.5f,
                1.0f,
                1.5f
            )
            anim.duration = 1000
            imgTween.startAnimation(anim)
        }

        btnTranslate.setOnClickListener(){
            val anim = TranslateAnimation(
                0f,
                100f,
                0f,
                -100f
            )
            anim.duration = 1000
            imgTween.startAnimation(anim)
        }

        btnRotate.setOnClickListener(){
            val anim = RotateAnimation(
                0f,
                360f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = 1000
            imgTween.startAnimation(anim)
        }


    }
}