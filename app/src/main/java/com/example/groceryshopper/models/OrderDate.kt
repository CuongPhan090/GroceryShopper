package com.example.groceryshopper.models

data class OrderDate(
    val _id: String,
    val date: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val shippingAddress: ShippingAddress
)

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val _id: String,
    val paymentMode: String
)

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)