package com.example.groceryshopper.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryshopper.adapter.CartItemAdapter
import com.example.groceryshopper.model.CartItem
import com.example.groceryshopper.databinding.ActivityCartBinding
import com.example.groceryshopper.model.Product
import com.example.groceryshopper.sql.ItemDao

class CartActivity : AppCompatActivity() {
    lateinit var adapter: CartItemAdapter
    lateinit var binding: ActivityCartBinding
    lateinit var itemDao: ItemDao
    private var product: Product? = null

    var cartItems: ArrayList<CartItem>? = null  //empty cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent?.extras?.getParcelable("productDetail")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Checkout"
        itemDao = ItemDao(baseContext)
        cartItems = itemDao.showItems()
        adapter = CartItemAdapter(cartItems)
        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(baseContext)

        setupEvents()
    }

    private fun setupEvents() {
        calculateTotalPrice()
        deleteItem()
        addQuantity()
        subtractQuantity()
    }

    private fun subtractQuantity() {
        adapter.onClickedTakeLessItemListen{ cartItem, position ->
            if (cartItem.quantity == 0)
            {
                Toast.makeText(baseContext, "Invalid action", Toast.LENGTH_SHORT).show()
                return@onClickedTakeLessItemListen
            }
            cartItem.quantity -= 1
            itemDao.updateItem(cartItem)
            cartItems?.get(position)?.quantity?.minus(1)
            adapter.notifyDataSetChanged()
            calculateTotalPrice()
        }
    }

    private fun addQuantity() {
        adapter.onClickedAddMoreItemListener { cartItem, position ->
            // if the user order more than its available quantity in stock
            if (cartItem.quantity >= product?.quantity ?: 10000) {
                Toast.makeText(baseContext, "Maximum allow quantity", Toast.LENGTH_SHORT).show()
                return@onClickedAddMoreItemListener
            }
            cartItem.quantity += 1
            itemDao.updateItem(cartItem)
            cartItems?.get(position)?.quantity?.plus(1)
            adapter.notifyDataSetChanged()
            calculateTotalPrice()
        }
    }

    private fun deleteItem() {
        adapter.onClickedDeleteListener { cartItem, position ->
            val dialog = AlertDialog.Builder(this).apply {
                setTitle("Confirm Action")
                setMessage("Are you sure you want to do this?")
                setPositiveButton("Yes") {
                    dialog, _ ->
                        itemDao.deleteItem(cartItem.itemId)
                        cartItems?.removeAt(position)
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                        calculateTotalPrice()
                }
                setNegativeButton("No") {
                    dialog, _ ->
                        dialog.dismiss()
                }
            }.create()
            dialog.show()
        }
    }

    private fun calculateTotalPrice() {
        var totalPrice = 0.0
        cartItems?.forEach{
            item -> totalPrice += (item.price * item.quantity)
        }
        binding.tvTotalPrice.text = "${totalPrice}INR"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}