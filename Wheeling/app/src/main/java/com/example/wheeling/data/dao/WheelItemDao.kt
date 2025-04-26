package com.example.wheeling.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wheeling.data.model.WheelItem

@Dao
interface WheelItemDao {

    @Query("SELECT * FROM wheel_items")
    fun getAllItems(): LiveData<List<WheelItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WheelItem)

    @Delete
    suspend fun deleteItem(item: WheelItem)

    @Query("DELETE FROM wheel_items")
    suspend fun clearAll()
}