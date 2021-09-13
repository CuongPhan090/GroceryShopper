package com.example.groceryshopper.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.data.Category
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest.IMAGE_BASE_URL
import com.example.groceryshopper.databinding.HolderCategoryBinding

class CategoryHolder(var binding: HolderCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category, imageLoader: ImageLoader) {
        imageLoader.get(
            "$IMAGE_BASE_URL${category.catImage}",
            ImageLoader.getImageListener(binding.nivCategoryImage,
                R.drawable.ic_default_image,
                R.drawable.ic_error
            ))

        binding.tvCategoryTitle.text = category.catName
        binding.nivCategoryImage.setImageUrl("$IMAGE_BASE_URL${category.catImage}", imageLoader)

    }
}