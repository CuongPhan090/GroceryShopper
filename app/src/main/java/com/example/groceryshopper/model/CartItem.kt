package com.example.groceryshopper.model

data class CartItem(
    val itemId: Long = 0L,
    val url: String,
    val name: String,
    var quantity: Int,
    val price: Double,
    val description: String
)
