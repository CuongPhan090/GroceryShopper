package com.example.groceryshopper.view

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.R
import com.example.groceryshopper.UrlRequest
import com.example.groceryshopper.model.Product
import com.example.groceryshopper.databinding.ActivityProductDetailBinding
import com.example.groceryshopper.model.CartItem
import com.example.groceryshopper.sql.ItemDao

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var imageLoader: ImageLoader
    lateinit var requestQueue: RequestQueue
    lateinit var itemDao: ItemDao
    lateinit var cartItem: ArrayList<CartItem>
    private var product: Product? = null


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

        itemDao = ItemDao(baseContext)
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
            val intent = Intent(baseContext, CartActivity::class.java)
            intent.putExtra("productDetail", product)
            startActivity(intent)
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
            if (quantity == 0) {
                Toast.makeText(baseContext, "Invalid quantity", Toast.LENGTH_SHORT).show()
            } else {
                val name = product?.productName
                val price = product?.price?.toDouble()
                val url = "${UrlRequest.IMAGE_BASE_URL}${product?.image}"
                val description = product?.description
                var quantity = binding.tvQuantity.text.toString().toInt()
                cartItem = itemDao.showItems()
                // if duplicated item is added to the cart
                // then update the only quantity in database
                cartItem.forEach{ item ->
                    if (item.name == name) {
                        quantity += item.quantity
                        val item = CartItem(item.itemId, url, name?: "", quantity, price?: 0.0, description?: "")
                        itemDao.updateItem(item)
                        Toast.makeText(baseContext, "Add to cart successfully", Toast.LENGTH_SHORT).show()
                        finish()
                        return@setOnClickListener
                    }
                }
                val item = CartItem(0, url, name?: "", quantity, price?: 0.0, description?: "")
                itemDao.addItem(item)
                Toast.makeText(baseContext, "Add to cart successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}