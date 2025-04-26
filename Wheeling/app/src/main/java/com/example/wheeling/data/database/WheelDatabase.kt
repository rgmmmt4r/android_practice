package com.example.wheeling.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheeling.data.dao.WheelItemDao
import com.example.wheeling.data.model.WheelItem

@Database(entities = [WheelItem::class], version = 1)
abstract class WheelDatabase : RoomDatabase() {

    abstract fun wheelItemDao(): WheelItemDao

    companion object {
        @Volatile
        private var INSTANCE: WheelDatabase? = null

        fun getDatabase(context: Context): WheelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WheelDatabase::class.java,
                    "wheel_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}