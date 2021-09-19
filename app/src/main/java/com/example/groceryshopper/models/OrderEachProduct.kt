package com.example.groceryshopper.models

data class OrderEachProduct(
    val _id: String,
    val price: Int,
    val productName: String,
    val quantity: Int
)