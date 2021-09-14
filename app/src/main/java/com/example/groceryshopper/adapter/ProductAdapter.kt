package com.example.groceryshopper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.data.Product
import com.example.groceryshopper.databinding.HolderProductBinding
import com.example.groceryshopper.holder.ProductHolder

class ProductAdapter(var products: ArrayList<Product>, var imageLoader: ImageLoader): RecyclerView.Adapter<ProductHolder>() {

    lateinit var productClickedListener : (Product) -> Unit

    fun onProductClickedListener(listener : (Product) -> Unit) {
        productClickedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        val binding = HolderProductBinding.inflate(layoutInflate, parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(products[position], imageLoader)
        if (this::productClickedListener.isInitialized) {
            holder.itemView.setOnClickListener{
                productClickedListener(products[position])
            }
        }
    }

    override fun getItemCount() = products.size
}