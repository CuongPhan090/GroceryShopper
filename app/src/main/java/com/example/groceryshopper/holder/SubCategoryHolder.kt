package com.example.groceryshopper.holder

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest.IMAGE_BASE_URL
import com.example.groceryshopper.data.SubCategory
import com.example.groceryshopper.databinding.HolderSubCategoryBinding

class SubCategoryHolder(var binding: HolderSubCategoryBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(subCategory: SubCategory, imageLoader: ImageLoader) {
        imageLoader.get(
            "$IMAGE_BASE_URL$subCategory.subImage",
            ImageLoader.getImageListener(
                binding.nivSubcategoryImage,
                R.drawable.ic_default_image,
                R.drawable.ic_error
            )
        )

        binding.tvSubcategoryTitle.text = subCategory.subName
        binding.nivSubcategoryImage.setImageUrl("$IMAGE_BASE_URL$subCategory.subImage", imageLoader)
    }
}