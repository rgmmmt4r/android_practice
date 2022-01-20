package com.example.fznalarm.util

import com.example.fznalarm.MainActivity
import java.util.concurrent.atomic.AtomicInteger

object RandomIntUtil {
    private val seed = AtomicInteger()
    fun getRandomInt() = seed.getAndIncrement() +  System.currentTimeMillis().toInt()
}