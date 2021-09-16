package com.example.groceryshopper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.model.CartItem
import com.example.groceryshopper.databinding.HolderCartItemBinding
import com.example.groceryshopper.holder.CartItemsHolder

class CartItemAdapter(val cartItems: ArrayList<CartItem>?) :
    RecyclerView.Adapter<CartItemsHolder>() {

    lateinit var binding: HolderCartItemBinding
    lateinit var deleteListener: (CartItem, Int) -> Unit
    lateinit var addMoreItemListener: (CartItem, Int) -> Unit
    lateinit var takeLessItemListen: (CartItem, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemsHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        binding = HolderCartItemBinding.inflate(layoutInflate, parent, false)
        return CartItemsHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemsHolder, position: Int) {
        cartItems?.let {
            holder.bind(cartItems[position])

            if (this::deleteListener.isInitialized) {
                binding.btnRemoveItem.setOnClickListener { _ ->
                    deleteListener(it[position], position)
                }
            }

            if (this::addMoreItemListener.isInitialized) {
                binding.btnItemAdd.setOnClickListener { _ ->
                    addMoreItemListener(it[position], position)
                }
            }

            if (this::takeLessItemListen.isInitialized) {
                binding.btnItemSubtract.setOnClickListener { _ ->
                    takeLessItemListen(it[position], position)
                }
            }
        }
    }

    fun onClickedDeleteListener(listener: (CartItem, Int) -> Unit) {
        deleteListener = listener
    }

    fun onClickedAddMoreItemListener(listener: (CartItem, Int) -> Unit) {
        addMoreItemListener = listener
    }

    fun onClickedTakeLessItemListen(listener: (CartItem, Int) -> Unit) {
        takeLessItemListen = listener
    }

    override fun getItemCount() = cartItems?.size ?: 0
}