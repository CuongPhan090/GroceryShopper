package com.example.groceryshopper.views

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.ORDER_END_POINT
import com.example.groceryshopper.adapters.OrderHistoryAndDateAdapter
import com.example.groceryshopper.databinding.ActivityOrderHistoryBinding
import com.example.groceryshopper.models.OrderDate
import com.example.groceryshopper.models.OrderEachProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class OrderHistory : AppCompatActivity() {
    lateinit var binding : ActivityOrderHistoryBinding
    lateinit var adapter : OrderHistoryAndDateAdapter
    lateinit var requestQueue: RequestQueue
    lateinit var sharedPref: SharedPreferences
    lateinit var orderHistoryDate: ArrayList<Any>
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderHistoryDate = ArrayList()
        supportActionBar?.title = "Order History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        requestQueue = Volley.newRequestQueue(this)
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(baseContext)
        pd = ProgressDialog(this)
        pd.show()
        loadingData()
    }

    private fun loadingData() {
        val userId = sharedPref.getString("userId", "")

        val orderHistoryRequest = JsonObjectRequest(
            Request.Method.GET,
            "$BASE_URL$ORDER_END_POINT$userId",
            null,
            {
                pd.dismiss()
                val error = it.getBoolean("error")
                if (error) {
                    Toast.makeText(this, "Unable to download order history", Toast.LENGTH_SHORT).show()
                    return@JsonObjectRequest
                }
                val jsonArray = it.getJSONArray("data")
                val orderDateType = object: TypeToken<OrderDate>(){}.type
                val orderEachProductType = object: TypeToken<OrderEachProduct>(){}.type
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val eachOrderDate: OrderDate = gson.fromJson(jsonArray[i].toString(), orderDateType)
                    orderHistoryDate.add(eachOrderDate)
                    val products = ((it.getJSONArray("data")[i]) as JSONObject ).getJSONArray("products")
                    for (j in 0 until products.length()) {
                        val product: OrderEachProduct = gson.fromJson(products[j].toString(), orderEachProductType)
                        orderHistoryDate.add(product)
                    }
                }

                adapter = OrderHistoryAndDateAdapter(orderHistoryDate)
                binding.rvOrderHistory.adapter = adapter

            }, {
                pd.dismiss()
                it.printStackTrace()
                Toast.makeText(this, "Unable to send order history request", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(orderHistoryRequest)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressDialog() {
        pd = ProgressDialog(this).apply {
            setMessage("Logging in...")
            setCancelable(false)
            show()
        }
    }
}