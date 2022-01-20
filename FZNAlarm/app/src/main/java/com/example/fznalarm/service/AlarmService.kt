package com.example.fznalarm.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat.setAndAllowWhileIdle
import androidx.core.app.AlarmManagerCompat.setExact
import com.example.fznalarm.receiver.AlarmReceiver
import com.example.fznalarm.util.Constants
import com.example.fznalarm.util.RandomIntUtil





class AlarmService(private val context: Context) {
    private var alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
    private var _pendingIntent1 : PendingIntent? = null
    private var _pendingIntent2 : PendingIntent? = null
    private var _pendingIntent3 : PendingIntent? = null
    private var _pendingIntent4 : PendingIntent? = null
    private var _pendingIntent5 : PendingIntent? = null


    fun setExactAlarm(timeINMillis: Long){
        setAlarm(
            timeINMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
                }
            )
        )
    }
    //every day
    fun setRepetitiveAlarm(timeINMillis: Long){
        setAlarm(
            timeINMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_REPETITIVE_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)

                }
            )
        )
    }


    private fun setAlarm(timeINMillis: Long, pendingIntent: PendingIntent){
        alarmManager?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager!!.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeINMillis,
                    pendingIntent
                )
            }else{
                alarmManager!!.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeINMillis,
                    pendingIntent
                )
            }
        }
    }


    private fun getIntent() = Intent(context, AlarmReceiver:: class.java)

    private fun getPendingIntent(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            RandomIntUtil.getRandomInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun setFZNAlarm1(timeINMillis: Long){
        this._pendingIntent1 = getPendingIntent1(
            getIntent().apply {
                action = Constants.ACTION_SET_FZN_ALARM1
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
            }
        )
        setAlarm(
            timeINMillis,
            this._pendingIntent1!!
        )
    }

    private fun getPendingIntent1(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun cancelAlarm1(timeINMillis: Long){
        if(this._pendingIntent1 != null) {
            alarmManager?.cancel(this._pendingIntent1)
        }
    }

    fun setFZNAlarm2(timeINMillis: Long){
        this._pendingIntent2 = getPendingIntent2(
            getIntent().apply {
                action = Constants.ACTION_SET_FZN_ALARM2
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
            }
        )
        setAlarm(
            timeINMillis,
            this._pendingIntent2!!
        )
    }

    private fun getPendingIntent2(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun cancelAlarm2(timeINMillis: Long){
        if(this._pendingIntent2 != null) {
            alarmManager?.cancel(this._pendingIntent2)
        }
    }


    fun setFZNAlarm3(timeINMillis: Long){
        this._pendingIntent3 = getPendingIntent3(
            getIntent().apply {
                action = Constants.ACTION_SET_FZN_ALARM3
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
            }
        )
        setAlarm(
            timeINMillis,
            this._pendingIntent3!!
        )
    }

    private fun getPendingIntent3(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun cancelAlarm3(timeINMillis: Long){
        if(this._pendingIntent3 != null) {
            alarmManager?.cancel(this._pendingIntent3)
        }
    }

    fun setFZNAlarm4(timeINMillis: Long){
        this._pendingIntent4 = getPendingIntent4(
            getIntent().apply {
                action = Constants.ACTION_SET_FZN_ALARM4
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
            }
        )
        setAlarm(
            timeINMillis,
            this._pendingIntent4!!
        )
    }

    private fun getPendingIntent4(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            4,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun cancelAlarm4(timeINMillis: Long){
        if(this._pendingIntent4 != null) {
            alarmManager?.cancel(this._pendingIntent4)
        }
    }

    fun setFZNAlarm5(timeINMillis: Long){
        this._pendingIntent5 = getPendingIntent5(
            getIntent().apply {
                action = Constants.ACTION_SET_FZN_ALARM5
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeINMillis)
            }
        )
        setAlarm(
            timeINMillis,
            this._pendingIntent5!!
        )
    }

    private fun getPendingIntent5(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            5,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    fun cancelAlarm5(timeINMillis: Long){
        if(this._pendingIntent5 != null) {
            alarmManager?.cancel(this._pendingIntent5)
        }
    }
}