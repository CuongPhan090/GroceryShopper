package com.example.groceryshopper.views

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.R
import com.example.groceryshopper.models.Category
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.CATEGORY_END_POINT
import com.example.groceryshopper.adapters.CategoryAdapter
import com.example.groceryshopper.databinding.ActivityCategoryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var adapter: CategoryAdapter
    lateinit var requestQueue: RequestQueue
    lateinit var categories: ArrayList<Category>
    lateinit var imageLoader: ImageLoader
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var headView: View
    lateinit var userNameHeader: TextView
    lateinit var userEmailHeader: TextView
    lateinit var sharedPref: SharedPreferences


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

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        actionBarDrawerToggle.syncState()
        headView = binding.navView.getHeaderView(0)
//        val userPhoto : ImageView = findViewById(R.id.iv_user_photo)
        userNameHeader = headView.findViewById(R.id.tv_user_name)
        userEmailHeader = headView.findViewById(R.id.tv_user_email)


        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        userNameHeader.text = sharedPref.getString("userName", "Username").toString()
        userEmailHeader.text = sharedPref.getString("userEmail", "Email").toString()

        binding.navView.setNavigationItemSelectedListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.action_profile -> {
                    startActivity(Intent(baseContext, UserDetailActivity::class.java))
                }
                R.id.action_resetPassword -> {
                    startActivity(Intent(baseContext, ResetPasswordActivity::class.java))
                    // go to reset password activity, use _id + PUT to update the changes
                }
                R.id.action_logOut -> {
                    AlertDialog.Builder(this).apply {
                        setMessage("Are you sure you want to exit the app?")
                        setPositiveButton("Yes") { dialog, _ ->
                            sharedPref.edit().clear().apply()
                            finishAffinity()
                            dialog.dismiss()
                        }
                        setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                    }.create().show()
                }
                R.id.action_cart -> {
                    startActivity(Intent(baseContext, CartActivity::class.java))
                }
                R.id.action_my_orders -> {
                    startActivity(Intent(baseContext, OrderHistory::class.java))
                }
                R.id.action_settings -> {
                    android.app.AlertDialog.Builder(this).apply {
                        setTitle("Notification")
                        setMessage("This feature has not implemented yet")
                        setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                    }.show()
                }
                R.id.action_contactUs -> {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789")))
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.toolBar.title = "Category"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(binding.toolBar)

        requestQueue = Volley.newRequestQueue(baseContext)
        imageLoader = ImageLoader(requestQueue, imageCache)
        loadCategory()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            finishAffinity()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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