package com.example.mediarecorder

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var recorder: MediaRecorder
    private lateinit var player: MediaPlayer
    private lateinit var folder: File
    private var fileName = ""

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.isNotEmpty() && requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                finish()
            }else{
                doInitialize()
                setListener()
            }
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

        val permission = android.Manifest.permission.RECORD_AUDIO

        if (ActivityCompat.checkSelfPermission(this,permission)
            !=PackageManager.PERMISSION_GRANTED
            ){
            ActivityCompat.requestPermissions(
                this, arrayOf(permission),0
            )
        } else{
            doInitialize()
            setListener()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::recorder.isInitialized) recorder.release()
        if(::player.isInitialized) player.release()
    }

    private fun doInitialize(){
    }


    private fun setListener(){
    }
}