package com.example.groceryshopper.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import android.view.Menu
import android.view.MenuItem
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest
import com.example.groceryshopper.data.Product
import com.example.groceryshopper.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var imageLoader: ImageLoader
    lateinit var requestQueue: RequestQueue
    var product: Product? = null

    private val imageCache = object : ImageLoader.ImageCache {
        val lruCache: LruCache<String, Bitmap> = LruCache(200)
        override fun getBitmap(url: String?): Bitmap? {
            return lruCache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            url?.let {
                lruCache.put(url, bitmap)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        requestQueue = Volley.newRequestQueue(baseContext)
        imageLoader = ImageLoader(requestQueue, imageCache)
        product = intent?.extras?.getParcelable("productDetail")

        loadDetails()
        setupEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        if (item.itemId == R.id.action_view_cart) {
            startActivity(Intent(baseContext, CartActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDetails() {
        binding.tvProductDescription.text = product?.description
        binding.tvProductName.text = product?.productName
        binding.nivProductDetailImage.setImageUrl(
            "${UrlRequest.IMAGE_BASE_URL}${product?.image}",
            imageLoader
        )
    }

    private fun setupEvents() {
        var quantity = 1
        binding.btnAdd.setOnClickListener {
            if (quantity <= product?.quantity ?: 1)
                quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnSubtract.setOnClickListener {
            if (quantity > 0) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnAddToCart.setOnClickListener {

        }
    }
}