package com.example.fznalarm

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.annotation.RequiresApi
import com.example.fznalarm.service.AlarmService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmService: AlarmService
    private lateinit var setExact:View
    private lateinit var setRepetitive:View

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmService = AlarmService(this)
        setExact = findViewById(R.id.setExact)
        setRepetitive = findViewById(R.id.setRepetitive)

        setExact.setOnClickListener {
            setAlarm{timeInMillis -> alarmService.setExactAlarm(timeInMillis)}
        }

        setRepetitive.setOnClickListener{
            var time0 = LocalDateTime.now().withHour(11).withMinute(15).withSecond(0).withNano(0)
            var millis0 = time0.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            alarmService.setRepetitiveAlarm(millis0)
        }
        val switch1: Switch = findViewById(R.id.alarm_switch1)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            var time1 = LocalDateTime.now().withHour(5).withMinute(55).withSecond(0).withNano(0)
            var millis1 = time1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if(isChecked){
                alarmService.setFZNAlarm1(millis1)
            }else{
                alarmService.cancelAlarm1(millis1)
            }
        }

        val switch2: Switch = findViewById(R.id.alarm_switch2)

        switch2.setOnCheckedChangeListener { _, isChecked ->
            var time2 = LocalDateTime.now().withHour(11).withMinute(55).withSecond(0).withNano(0)
            var millis2 = time2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if(isChecked){
                alarmService.setFZNAlarm2(millis2)
            }else{
                alarmService.cancelAlarm2(millis2)
            }
        }

        val switch3: Switch = findViewById(R.id.alarm_switch3)

        switch3.setOnCheckedChangeListener { _, isChecked ->
            var time3 = LocalDateTime.now().withHour(17).withMinute(55).withSecond(0).withNano(0)
            var millis3 = time3.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if(isChecked){
                alarmService.setFZNAlarm3(millis3)
            }else{
                alarmService.cancelAlarm3(millis3)
            }
        }

        val switch4: Switch = findViewById(R.id.alarm_switch4)

        switch4.setOnCheckedChangeListener { _, isChecked ->
            var time4 = LocalDateTime.now().withHour(20).withMinute(55).withSecond(0).withNano(0)
            var millis4 = time4.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if(isChecked){
                alarmService.setFZNAlarm4(millis4)
            }else{
                alarmService.cancelAlarm4(millis4)
            }
        }

        val switch5: Switch = findViewById(R.id.alarm_switch5)

        switch5.setOnCheckedChangeListener { _, isChecked ->
            var time5 = LocalDateTime.now().withHour(23).withMinute(55).withSecond(0).withNano(0)
            var millis5 = time5.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if(isChecked){
                alarmService.setFZNAlarm5(millis5)
            }else{
                alarmService.cancelAlarm5(millis5)
            }
        }

    }

    private fun setAlarm(callback: (Long)->Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND,0)
            this.set(Calendar.MILLISECOND,0)
            DatePickerDialog(
                this@MainActivity,
                0,
                {_,year,month,day ->
                    this.set(Calendar.YEAR,year)
                    this.set(Calendar.MONTH,month)
                    this.set(Calendar.DAY_OF_MONTH,day)

                    TimePickerDialog(
                        this@MainActivity,
                        0,
                        { _, hour, min ->
                            this.set(Calendar.HOUR_OF_DAY,hour)
                            this.set(Calendar.MINUTE,min)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()

                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

}