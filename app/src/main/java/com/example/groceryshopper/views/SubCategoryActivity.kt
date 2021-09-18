package com.example.groceryshopper.views

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.SUB_CATEGORY_END_POINT
import com.example.groceryshopper.adapters.SubCategoryAdapter
import com.example.groceryshopper.models.SubCategory
import com.example.groceryshopper.databinding.ActivitySubCategoryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivitySubCategoryBinding
    lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var adapter: SubCategoryAdapter
    lateinit var subCategories: ArrayList<SubCategory>

    private val imageCache = object : ImageLoader.ImageCache {
        val lruCache: LruCache<String, Bitmap> = LruCache(100)
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
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(baseContext)
        imageLoader = ImageLoader(requestQueue, imageCache)

        loadSubCategory()
    }

    private fun loadSubCategory() {
        val categoryId = intent?.extras?.getInt("categoryId")
        val categoryName = intent?.extras?.getString("categoryName")

        supportActionBar?.title = "$categoryName"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val subCategoryRequest = JsonObjectRequest(
            Request.Method.GET,
            "${BASE_URL}${SUB_CATEGORY_END_POINT}$categoryId",
            null,
            {
                binding.pbSubcategory.visibility = View.GONE
                val error = it.getString("error").toBoolean()
                if (error) {
                    Toast.makeText(baseContext, "Unable to load data", Toast.LENGTH_LONG).show()
                } else {
                    val data = it.getJSONArray("data").toString()
                    val type = object: TypeToken<ArrayList<SubCategory>>(){}.type
                    val gson = Gson()
                    subCategories = gson.fromJson(data, type)
                    adapter = SubCategoryAdapter(subCategories, imageLoader)
                    binding.rvSubcategories.adapter = adapter
                    binding.rvSubcategories.layoutManager = LinearLayoutManager(baseContext)

                    adapter.setOnSubCategoryClickedListener{ subCategory ->
                        val subId = subCategory.subId
                        val style = subCategory.subName
                        val foodName = categoryName
                        val intent = Intent(baseContext, ProductActivity::class.java)
                        intent.putExtra("subId", subId)
                        intent.putExtra("style", style)
                        intent.putExtra("foodName", foodName)
                        startActivity(intent)
                    }
                }
            }, {
                binding.pbSubcategory.visibility = View.GONE
                it.printStackTrace()
                Toast.makeText(baseContext, "Unable to make load data request", Toast.LENGTH_LONG).show()
            }
        )

        requestQueue.add(subCategoryRequest)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
