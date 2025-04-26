package com.example.wheeling.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wheel_items")
data class WheelItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Int
)
