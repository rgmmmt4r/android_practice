package com.example.player1


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import java.lang.Exception

class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.exercise_01,R.raw.exercise_02,R.raw.exercise_03,
                                            R.raw.exercise_04)
    private var selectedId: Int = 1
    lateinit var spinner: Spinner
    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.exercise_spinner)
        textView = findViewById(R.id.playingText)


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.exercise_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this


    }
    private fun controlSound(id: Int){
        val fab_play: View = findViewById(R.id.fab_play)
        val fab_pause: View = findViewById(R.id.fab_pause)
        val fab_stop: View = findViewById(R.id.fab_stop)
        val seekbar: SeekBar = findViewById(R.id.seekbar)
        fab_play.setOnClickListener{
            if(mp == null){
                mp = MediaPlayer.create(this,id)
                Log.d("MainActivity","ID:${mp!!.audioSessionId}")
                initializeSeekBar()
            }
            mp?.start()
            Log.d("MainActivity","Duration:${mp!!.duration/1000} seconds")

        }
        fab_pause.setOnClickListener {
            if(mp !== null)mp?.pause()
            Log.d("MainActivity","Paused at:${mp!!.currentPosition/1000} seconds")
        }
        fab_stop.setOnClickListener {
            if(mp !==null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun initializeSeekBar(){
        val seekbar: SeekBar = findViewById(R.id.seekbar)
        seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object: Runnable{
          override fun run(){
              try{
                  seekbar.progress = mp!!.currentPosition
                  handler.postDelayed(this,1000)
              }catch(e:Exception){
                  seekbar.progress = 0
              }
          }
        }, 0)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        this.selectedId = pos
        this.textView.text = parent.getItemAtPosition(pos) as CharSequence?
        if(mp !==null){
            mp?.stop()
            mp?.reset()
            mp?.release()
            mp = null
        }
        controlSound(currentSong[selectedId])
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}
