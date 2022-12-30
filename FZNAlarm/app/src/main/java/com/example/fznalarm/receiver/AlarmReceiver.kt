package com.example.fznalarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.text.format.DateFormat
import com.example.fznalarm.R
import com.example.fznalarm.service.AlarmService
import com.example.fznalarm.util.Constants
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit

//import java.text.DateFormat


private var mp: MediaPlayer? = null
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM ->{
                buildNotification(context,"Set Exact Time",convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_ALARM ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
            }

            Constants.ACTION_SET_FZN_ALARM1 ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setFZNAlarm1(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
                play(context)
            }

            Constants.ACTION_SET_FZN_ALARM2 ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setFZNAlarm2(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
                play(context)
            }

            Constants.ACTION_SET_FZN_ALARM3 ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setFZNAlarm3(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
                play(context)
            }

            Constants.ACTION_SET_FZN_ALARM4 ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setFZNAlarm4(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
                play(context)
            }

            Constants.ACTION_SET_FZN_ALARM5 ->{
                val cal = Calendar.getInstance().apply{
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setFZNAlarm5(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Time",convertDate(timeInMillis))
                play(context)
            }
        }
    }


    private fun buildNotification(context: Context,title: String,message: String){
        Notify
            .with(context)
            .content {
                this.title = title
                this.text = "時間到! - $message"
            }
            .show()

    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}

private fun play(context: Context){
    mp = if (mp != null && mp!!.isPlaying) {
        mp!!.stop()
        mp!!.release()
        null
    } else {
        null
    }

    mp = MediaPlayer.create(context, R.raw.lifelike)
    mp!!.start()

    mp!!.setOnCompletionListener { mp ->
        if (mp!!.isPlaying) {
            mp.release()
        } else {
            mp.stop()
            mp.release()
        }
    }
}




