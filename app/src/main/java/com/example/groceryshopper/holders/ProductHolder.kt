package com.example.groceryshopper.holders

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest.IMAGE_BASE_URL
import com.example.groceryshopper.models.Product
import com.example.groceryshopper.databinding.HolderProductBinding


class ProductHolder(var binding: HolderProductBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, imageLoader: ImageLoader) {
        imageLoader.get(
            "$IMAGE_BASE_URL${product.image}",
            ImageLoader.getImageListener(
                binding.nivProduct,
                R.drawable.ic_default_image,
                R.drawable.ic_error
            )
        )
        binding.tvProductName.text = product.productName
        binding.tvProductPrice.text = "INR${product.price}"
        binding.nivProduct.setImageUrl("$IMAGE_BASE_URL${product.image}", imageLoader )
    }
}
