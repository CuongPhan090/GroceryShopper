package com.example.groceryshopper.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopper.databinding.HolderOrderHistoryBinding
import com.example.groceryshopper.models.OrderEachProduct

class OrderHistoryHolder(var binding: HolderOrderHistoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(orderEachProduct: OrderEachProduct) {
        binding.tvOrderName.text = "Product name: ${orderEachProduct.productName}"
        binding.tvOrderPrice.text = "Order price: ${orderEachProduct.price}INR"
        binding.tvOrderQuantity.text = "Quantity: ${orderEachProduct.quantity}"
        binding.tvTotalPrice.text = "Product total price: ${(orderEachProduct.price *  orderEachProduct.quantity)}INR"
    }
}