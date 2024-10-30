package com.example.broadcastreceiver2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val receiver =
        object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                val msg = intent.extras?.getString("msg")
                Toast.makeText(context,"螢幕開啟",Toast.LENGTH_SHORT).show()
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
        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_ON)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        }else{
            registerReceiver(receiver, intentFilter)
        }
//        val intent = Intent("MyBroadcast")
//        intent.putExtra("msg","Hello, World")
//        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}