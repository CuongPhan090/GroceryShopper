package com.example.groceryshopper.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopper.models.CartItem
import com.example.groceryshopper.databinding.HolderCartItemBinding
import com.squareup.picasso.Picasso

class CartItemsHolder(val binding: HolderCartItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItem: CartItem) {
        val quantity : Int = cartItem.quantity.toString().toInt()
        val price: Double = cartItem.price.toString().toDouble()
        val totalPrice : Double = quantity * price
        binding.tvCartItemName.text = cartItem.name
        binding.tvCartItemQuantity.text = "$quantity"
        binding.tvTotalPriceEachItem.text = "${totalPrice}INR"
        Picasso.get().load(cartItem.url).into(binding.ivCartItemImage)

    }
}