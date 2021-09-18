package com.example.groceryshopper.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.widget.Toast
import com.example.groceryshopper.models.CartItem

class ItemDao(val context: Context) {
    val db: SQLiteDatabase = DBHelper(context).writableDatabase

    fun addItem(item: CartItem): Boolean {
        try {
            val contentValues = ContentValues()
            contentValues.put("name", item.name)
            contentValues.put("url", item.url)
            contentValues.put("quantity", item.quantity)
            contentValues.put("price", item.price)
            contentValues.put("description", item.description)
            val result = db.insert("Items", null, contentValues)
            return result != - 1L
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Toast.makeText(context, "Unable to add the item", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun deleteItem(itemId: Long): Boolean {
        try {
            val result = db.delete("Items", "itemId=$itemId", null)
            return result == 1
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Toast.makeText(context, "Unable to delete the item", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun updateItem(item: CartItem): Boolean {
        try {
            val contentValues = ContentValues()
            contentValues.put("name", item.name)
            contentValues.put("url", item.url)
            contentValues.put("quantity", item.quantity)
            contentValues.put("price", item.price)
            contentValues.put("description", item.description)
            val result = db.update("Items",contentValues,  "itemId=${item.itemId}", null)
            return result == 1
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Toast.makeText(context, "Unable to update the item", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun showItems(): ArrayList<CartItem> {
        val items = ArrayList<CartItem>()
        val cursor : Cursor = db.query("Items", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val itemId = cursor.getLong(0)
            val name = cursor.getString(1)
            val price = cursor.getDouble(2)
            val quantity = cursor.getInt(3)
            val url = cursor.getString(4)
            val description = cursor.getString(5)
            val item = CartItem(itemId, url, name, quantity, price, description)
            items.add(item)
        }
        return items
    }
}