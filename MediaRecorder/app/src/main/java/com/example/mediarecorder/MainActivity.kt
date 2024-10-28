package com.example.mediarecorder

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.util.Calendar

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
        @Suppress("DEPRECATION")
        recorder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            MediaRecorder(this)
        }else{
            MediaRecorder()
        }

        player = MediaPlayer()

        folder = File(filesDir.absolutePath + "/record")

        if(!folder.exists()){
            folder.mkdirs()
        }

    }


    private fun setListener(){
        val btnRecord = findViewById<Button>(R.id.btnRecord)
        val btnStopRecord = findViewById<Button>(R.id.btnStopRecord)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnStopPlay = findViewById<Button>(R.id.btnStopPlay)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)


        btnRecord.setOnClickListener(){
            fileName = "${Calendar.getInstance().time.time}"
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(File(folder,fileName).absolutePath)
            recorder.prepare()
            recorder.start()
            tvTitle.text = "錄音中"

            btnRecord.isEnabled = false
            btnStopRecord.isEnabled = true
            btnPlay.isEnabled = false
            btnStopPlay.isEnabled = true
        }

        btnStopRecord.setOnClickListener(){
            try{
                val file = File(folder, fileName)
                recorder.stop()
                tvTitle.text = "已儲存至${file.absolutePath}"
                btnRecord.isEnabled = true
                btnStopRecord.isEnabled =false
                btnPlay.isEnabled = true
                btnStopPlay.isEnabled = false
            }catch (e:Exception){
                e.printStackTrace()
                recorder.reset()
                tvTitle.text = "錄音失效"
                btnRecord.isEnabled = true
                btnStopRecord.isEnabled =false
                btnPlay.isEnabled = false
                btnStopPlay.isEnabled = false
            }
        }

        btnPlay.setOnClickListener(){
            val file = File(folder, fileName)
            player.setDataSource(applicationContext, Uri.fromFile(file))
            player.setVolume(1f,1f)
            player.prepare()
            player.start()
            tvTitle.text = "播放中"
            btnRecord.isEnabled = false
            btnStopRecord.isEnabled =false
            btnPlay.isEnabled = false
            btnStopPlay.isEnabled = true
        }

        btnStopPlay.setOnClickListener(){
            val file = File(folder, fileName)
            player.stop()
            player.reset()
            tvTitle.text = "播放結束"
            btnRecord.isEnabled = true
            btnStopRecord.isEnabled =false
            btnPlay.isEnabled = true
            btnStopPlay.isEnabled = false
        }

        player.setOnCompletionListener {
            it.reset()
            tvTitle.text = "播放結束"
            btnRecord.isEnabled = true
            btnStopRecord.isEnabled =false
            btnPlay.isEnabled = true
            btnStopPlay.isEnabled = false
        }


    }
}