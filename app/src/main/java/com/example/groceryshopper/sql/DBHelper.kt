package com.example.groceryshopper.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context): SQLiteOpenHelper(context, "Items", null, 1){
    companion object{
        val CREATE_TABLE_QUERY = """
            CREATE TABLE Items(
                itemId INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                price REAL,
                quantity INTEGER,
                url TEXT,
                description TEXT
            )
        """.trimIndent()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE_QUERY)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}