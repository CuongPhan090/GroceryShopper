package com.example.groceryshopper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.model.Category
import com.example.groceryshopper.databinding.HolderCategoryBinding
import com.example.groceryshopper.holder.CategoryHolder

class CategoryAdapter(var categories: ArrayList<Category>, var imageLoader: ImageLoader) :
    RecyclerView.Adapter<CategoryHolder>() {
    lateinit var categorySelectedListener: (Category) -> Unit

    fun setOnCategorySelectedListener(listener: (Category) -> Unit) {
        categorySelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        val binding = HolderCategoryBinding.inflate(layoutInflate, parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categories[position], imageLoader)

        if (this::categorySelectedListener.isInitialized) {
            holder.itemView.setOnClickListener{
                categorySelectedListener(categories[position])
            }
        }

    }

    override fun getItemCount() = categories.size

}