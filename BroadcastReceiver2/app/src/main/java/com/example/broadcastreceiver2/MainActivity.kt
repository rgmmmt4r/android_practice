package com.example.broadcastreceiver2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val receiver =
        object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                intent.extras?.let {
                    val tvMsg = findViewById<TextView>(R.id.tvMsg)
                    tvMsg.text = "${it.getString("msg")}"
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

        findViewById<Button>(R.id.btnMusic).setOnClickListener(){
            register("music")
        }

        findViewById<Button>(R.id.btnNew).setOnClickListener(){
            register("new")
        }

        findViewById<Button>(R.id.btnSport).setOnClickListener(){
            register("sport")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun register(channel: String){
        val intentFilter = IntentFilter(channel)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            registerReceiver(receiver,intentFilter, RECEIVER_EXPORTED)
        }else{
            registerReceiver(receiver,intentFilter)
        }
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel",channel))
    }

}