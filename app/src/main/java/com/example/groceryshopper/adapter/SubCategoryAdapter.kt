package com.example.groceryshopper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.model.SubCategory
import com.example.groceryshopper.databinding.HolderSubCategoryBinding
import com.example.groceryshopper.holder.SubCategoryHolder

class SubCategoryAdapter(var subCategories: ArrayList<SubCategory>, var imageLoader: ImageLoader) :
    RecyclerView.Adapter<SubCategoryHolder>() {

    lateinit var subCategoryClickedListener : (SubCategory) -> Unit

    fun setOnSubCategoryClickedListener(listener : (SubCategory) -> Unit) {
        subCategoryClickedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        val binding = HolderSubCategoryBinding.inflate(layoutInflate, parent, false)
        return SubCategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryHolder, position: Int) {
        holder.bind(subCategories[position], imageLoader)

        if (this::subCategoryClickedListener.isInitialized) {
            holder.itemView.setOnClickListener{
                subCategoryClickedListener(subCategories[position])
            }
        }
    }

    override fun getItemCount() = subCategories.size
}