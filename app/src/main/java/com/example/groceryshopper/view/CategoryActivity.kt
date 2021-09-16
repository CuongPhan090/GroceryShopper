package com.example.groceryshopper.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.model.Category
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.CATEGORY_END_POINT
import com.example.groceryshopper.adapter.CategoryAdapter
import com.example.groceryshopper.databinding.ActivityCategoryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var adapter: CategoryAdapter
    lateinit var requestQueue: RequestQueue
    lateinit var categories: ArrayList<Category>
    lateinit var imageLoader: ImageLoader

    val imageCache = object : ImageLoader.ImageCache {
        val lruCache: LruCache<String, Bitmap> = LruCache(200)
        override fun getBitmap(url: String?): Bitmap? {
            return lruCache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            url?.let {
                lruCache.put(it, bitmap)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Category"
        requestQueue = Volley.newRequestQueue(baseContext)
        imageLoader = ImageLoader(requestQueue, imageCache)
        loadCategory()
    }

    private fun loadCategory() {

        val categoryRequest = JsonObjectRequest(
            Request.Method.GET,
            "$BASE_URL$CATEGORY_END_POINT",
            null,
            {
                binding.pbCategory.visibility = View.GONE
                val error = it.getString("error").toBoolean()
                if (error) {
                    Toast.makeText(baseContext, "Unable to load data", Toast.LENGTH_LONG).show()
                } else {
                    val data = it.getJSONArray("data").toString()
                    val gson = Gson()
                    val type = object : TypeToken<ArrayList<Category>>() {}.type
                    categories = gson.fromJson(data, type)

                    adapter = CategoryAdapter(categories, imageLoader)
                    binding.rvCategories.adapter = adapter
                    binding.rvCategories.layoutManager = LinearLayoutManager(baseContext)

                    adapter.setOnCategorySelectedListener {
                    val intent = Intent(baseContext, SubCategoryActivity::class.java)
                    intent.putExtra("categoryId", it.catId)
                    intent.putExtra("categoryName", it.catName)
                    startActivity(intent)
                    }
                }
            }, {
                binding.pbCategory.visibility = View.GONE
                it.printStackTrace()
                Toast.makeText(baseContext, "Unable to make load data request", Toast.LENGTH_LONG)
                    .show()
            }
        )

        requestQueue.add(categoryRequest)
    }
}