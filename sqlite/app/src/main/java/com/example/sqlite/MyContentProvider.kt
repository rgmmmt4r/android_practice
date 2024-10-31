package com.example.sqlite

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {
    private lateinit var dbrw: SQLiteDatabase

    override fun delete(
        uri: Uri, selection: String?,
        selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val book = values ?:return null
        val rowId = dbrw.insert("myTable", null, book)
        return Uri.parse("content://com.example.contentprovider/$rowId")
    }

    override fun onCreate(): Boolean {
        val context = context ?:return false
        dbrw = MyDBHelper(context).writableDatabase
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}