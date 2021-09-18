package com.example.groceryshopper.models

data class CartItem(
    val itemId: Long = 0L,
    val productId: String,
    val url: String,
    val name: String,
    var quantity: Int,
    val price: Double,
    val description: String
)
