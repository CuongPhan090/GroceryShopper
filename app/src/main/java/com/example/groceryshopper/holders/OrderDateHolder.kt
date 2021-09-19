package com.example.groceryshopper.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopper.databinding.HolderOrderDateBinding
import com.example.groceryshopper.models.OrderDate
import java.text.SimpleDateFormat

class OrderDateHolder(var binding: HolderOrderDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(orderDate: OrderDate) {
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val desFormat = SimpleDateFormat("MM-dd-yyyy")
        val parseSource = sourceFormat.parse(orderDate.date)
        val destSource = desFormat.format(parseSource)
        binding.tvOrderDate.text = "Order Date: $destSource"

        val shippingAddress = orderDate.shippingAddress
        val completedAddress =
            "${shippingAddress.houseNo} ${shippingAddress.streetName}, ${shippingAddress.city}, ${shippingAddress.pincode}"
        binding.tvOrderAddress.text = "Address: $completedAddress"
        binding.tvOverAllPrice.text = "Total Price: ${orderDate.orderSummary.ourPrice}INR"
        binding.tvPaymentMethod.text = "Payment method: ${orderDate.payment.paymentMode}"
        binding.tvOrderId.text= "Order id: ${orderDate.orderSummary._id}"
    }
}