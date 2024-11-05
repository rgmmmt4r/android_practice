package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {

    private val networkErr = listOf(true,false,false)

    private val accountList = mutableListOf<String>()

    private val _registerResult = MutableLiveData<Pair<Boolean,String>>()

    val registerResult: LiveData<Pair<Boolean, String>> = _registerResult

    fun registerAccount(
        account: String,
        password: String
    ){
        when{
            account.isEmpty() || password.isEmpty() ->
                _registerResult.value = Pair(false,"帳號或密碼不得為空")
            networkErr.random() ->
                _registerResult.value = Pair(false,"網路錯誤")
            accountList.contains(account) ->
                _registerResult.value = Pair(false,"帳號已存在")
            else ->{
                accountList.add(account)
                _registerResult.value = Pair(true,"註冊成功")
            }
        }
    }
}