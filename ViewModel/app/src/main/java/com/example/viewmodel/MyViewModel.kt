package com.example.viewmodel

import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var counter: Int = 0
    fun incrementCounter(){
        counter++
    }
}