package com.example.groceryshopper.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.PRODUCT_SUB_CATEGORY_END_POINT
import com.example.groceryshopper.adapter.ProductAdapter
import com.example.groceryshopper.data.Product
import com.example.groceryshopper.databinding.ActivityProductBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductBinding
    lateinit var imageLoader: ImageLoader
    lateinit var requestQueue: RequestQueue
    lateinit var adapter: ProductAdapter
    lateinit var products: ArrayList<Product>

    private val imageCache = object: ImageLoader.ImageCache{
        val lruCache: LruCache<String, Bitmap> = LruCache(100)
        override fun getBitmap(url: String?): Bitmap? {
            return lruCache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            url?.let{
                lruCache.put(url, bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(baseContext)
        imageLoader = ImageLoader(requestQueue, imageCache)

        loadProduct()

    }

    private fun loadProduct() {
        val subCategoryId = intent?.extras?.getInt("subId")
        val style = intent?.extras?.getString("style")
        val foodName = intent?.extras?.getString("foodName")

        supportActionBar?.title = "$style $foodName"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val productRequest = JsonObjectRequest(
            Request.Method.GET,
            "$BASE_URL$PRODUCT_SUB_CATEGORY_END_POINT$subCategoryId",
            null,
            {
                binding.pbProducts.visibility = View.GONE
                val data = it.getJSONArray("data").toString()
                val type = object: TypeToken<ArrayList<Product>>(){}.type
                products = Gson().fromJson(data, type)
                adapter = ProductAdapter(products, imageLoader)
                binding.rvProducts.adapter = adapter
                binding.rvProducts.layoutManager = LinearLayoutManager(baseContext)

                adapter.onProductClickedListener { product ->
                    val intent = Intent(baseContext, ProductDetailActivity::class.java)
                    intent.putExtra("productDetail", product)
                    startActivity(intent)
                }

            }, {
                binding.pbProducts.visibility = View.GONE
                it.printStackTrace()
                Toast.makeText(baseContext, "Unable to load products", Toast.LENGTH_LONG).show()
            }
        )

        requestQueue.add(productRequest)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}