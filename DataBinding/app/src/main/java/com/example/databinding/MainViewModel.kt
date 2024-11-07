package com.example.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _formula = MutableLiveData<String>()
    val formula: LiveData<String> = _formula

    private val _result = MutableLiveData<Float>()
    val result: LiveData<Float> = _result

    private var lastKey = ""

    private val operators = listOf("+","-","x","÷")

    fun onKeyClick(key: String){
        when(key){
            "C" ->{
                _formula.value = ""
                _result.value = 0f
            }

            in operators ->{
                val currentFormula = _formula.value ?: ""
                if(currentFormula.isEmpty() || lastKey in operators){
                    return
                }else{
                    _formula.value = "$currentFormula $key "
                }
            }

            else ->{
                val currentFormula = _formula.value ?: ""
                if(key == "." && (currentFormula.isEmpty() || lastKey in operators)){
                    return
                }
                _formula.value = "$currentFormula$key"
                _result.value = calculate()

            }
        }
        lastKey = key
    }

    private fun calculate(): Float{
        return try {
            val formula = _formula.value ?: return 0f
            val parts = formula.split(" ")
            val stack = mutableListOf<String>()
            var i = 0
            while (i < parts.size){
                val part = parts[i]
                if(part == "x" || part == "÷" ){
                    val prev = stack.removeAt(stack.size-1).toFloat()
                    val next = parts[i+1].toFloat()
                    val result = if(part == "x") prev * next else prev / next
                    stack.add(result.toString())
                    i+=2
                }else {
                    stack.add(part)
                    i++
                }
            }

            var result = stack[0].toFloat()
            i = 1
            while (i<stack.size){
                val operator = stack[i]
                val next = stack[i+1].toFloat()
                result = when(operator){
                    "+" -> result + next
                    "-" -> result - next
                    else -> result
                }
                i+=2
            }
            result
        }catch (e: NumberFormatException){
            -1f
        }
    }

    init {
        _formula.value = ""
        _result.value = 0f
    }
}