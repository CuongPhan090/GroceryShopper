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
            contentValues.put("productId", item.productId)
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

    fun deleteAllItem(): Boolean {
        try {
            val result = db.delete("Items", null, null)
            return result == 1
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Toast.makeText(context, "Unable to delete the item", Toast.LENGTH_LONG).show()
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
            contentValues.put("productId", item.productId)
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
            val productId = cursor.getString(1)
            val name = cursor.getString(2)
            val price = cursor.getDouble(3)
            val quantity = cursor.getInt(4)
            val url = cursor.getString(5)
            val description = cursor.getString(6)
            val item = CartItem(itemId, productId, url, name, quantity, price, description)
            items.add(item)
        }
        return items
    }
}