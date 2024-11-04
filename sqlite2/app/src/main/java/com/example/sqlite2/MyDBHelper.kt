package com.example.sqlite2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION

class MyDBHelper (
    context: Context,
    name: String = DB_Name,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = VERSION
): SQLiteOpenHelper(context,name,factory,version){
    companion object{
        private  const val DB_Name = "myDatabase"
        private  const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE myTable(book text PRIMARY KEY,price interger not NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("Drop TABLE IF EXISTS myTable")
        onCreate(db)
    }
}
