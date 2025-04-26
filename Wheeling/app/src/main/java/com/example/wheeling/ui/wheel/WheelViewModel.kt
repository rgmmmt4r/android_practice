package com.example.wheeling.ui.wheel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheeling.data.database.WheelRepository
import com.example.wheeling.data.model.WheelItem
import kotlinx.coroutines.launch

class WheelViewModel(private val repository: WheelRepository) : ViewModel() {

    val wheelItems: LiveData<List<WheelItem>> = repository.allItems

    fun addItem(name: String, weight: Int) {
        viewModelScope.launch {
            val newItem = WheelItem(name = name, weight = weight)
            repository.addItem(newItem)
        }
    }

    fun spinWheel(): WheelItem? {
        val items = wheelItems.value ?: return null
        if (items.isEmpty()) return null

        val weightedList = items.flatMap { item -> List(item.weight) { item } }
        return weightedList.random()
    }

    fun clearItems() {
        viewModelScope.launch {
            repository.clearItems()
        }
    }
}