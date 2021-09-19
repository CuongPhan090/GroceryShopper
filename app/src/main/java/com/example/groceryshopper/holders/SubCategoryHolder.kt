package com.example.groceryshopper.holders

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.models.SubCategory
import com.example.groceryshopper.databinding.HolderSubCategoryBinding

class SubCategoryHolder(var binding: HolderSubCategoryBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(subCategory: SubCategory, imageLoader: ImageLoader) {
        binding.tvSubcategoryTitle.text = subCategory.subName
    }
}