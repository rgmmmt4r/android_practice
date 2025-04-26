package com.example.wheeling.data.database

import androidx.lifecycle.LiveData
import com.example.wheeling.data.dao.WheelItemDao
import com.example.wheeling.data.model.WheelItem

class WheelRepository(private val dao: WheelItemDao) {

    val allItems: LiveData<List<WheelItem>> = dao.getAllItems()

    suspend fun addItem(item: WheelItem) {
        dao.insertItem(item)
    }

    suspend fun deleteItem(item: WheelItem) {
        dao.deleteItem(item)
    }

    suspend fun clearItems() {
        dao.clearAll()
    }
}