package com.example.groceryshopper.views

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.ADDRESS_END_POINT
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.ORDER_END_POINT
import com.example.groceryshopper.adapters.TabsAdapter
import com.example.groceryshopper.databinding.ActivityShippingPaymentBinding
import com.example.groceryshopper.models.CartItem
import com.example.groceryshopper.sql.ItemDao
import org.json.JSONArray
import org.json.JSONObject

class ShippingPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityShippingPaymentBinding
    lateinit var adapter: TabsAdapter
    lateinit var requestQueue: RequestQueue
    lateinit var bundleList: ArrayList<Bundle>
    lateinit var sharedPref : SharedPreferences
    lateinit var priceSharedPref : SharedPreferences
    lateinit var orderJsonObject: JSONObject
    lateinit var itemDao: ItemDao
    lateinit var cartItems: List<CartItem>
    lateinit var pd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        priceSharedPref = getSharedPreferences("Price", MODE_PRIVATE)
        orderJsonObject = JSONObject()
        itemDao = ItemDao(baseContext)

        adapter = TabsAdapter(supportFragmentManager)
        bundleList = ArrayList<Bundle>()
        setupEvents()


        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupEvents() {
        adapter.onClickedChangeTabListener {
            binding.viewPager.currentItem = 1
            bundleList.add(it)
        }

        adapter.onSubmitPaymentListener {
            pd = ProgressDialog(this).apply{
                setTitle("Please wait...")
                setMessage("Submitting your order...")
                setCancelable(false)
                show()
            }

            bundleList.add(it)
            uploadAddress()
            submitOrder()
        }
    }

    // use for access/modify/delete all saved address
    private fun uploadAddress() {
        val pincode = bundleList[0].getString("pincode")
        val city = bundleList[0].getString("city")
        val streetName = bundleList[0].getString("streetName")
        val houseNo = bundleList[0].getString("houseNo")
        val type = bundleList[0].getString("type")

        val jsonObject = JSONObject()
        jsonObject.put("pincode", pincode)
        jsonObject.put("city", city)
        jsonObject.put("streetName", streetName)
        jsonObject.put("houseNo", houseNo)
        jsonObject.put("type", type)
        orderJsonObject.put("shippingAddress", jsonObject)

        jsonObject.put("userId", sharedPref.getString("userId", ""))

        val uploadAddressRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL$ADDRESS_END_POINT",
            jsonObject,
            {
                val error = it.getBoolean("error")
                if (error) {
                    Toast.makeText(baseContext, "Unable to upload address", Toast.LENGTH_SHORT).show()
                }
            }, {
                it.printStackTrace()
                Toast.makeText(baseContext, "Unable to send upload address request", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(uploadAddressRequest)
    }

    private fun submitOrder() {
        cartItems = itemDao.showItems()
        val paymentMode = JSONObject()

        paymentMode.put("paymentMode", bundleList[1].getString("paymentMethod"))
        orderJsonObject.put("payment", paymentMode)
        orderJsonObject.put("userId", sharedPref.getString("userId", ""))

        val productJsonObjectArray = JSONArray()
        cartItems.forEach{
            val itemJsonObject = JSONObject()
            itemJsonObject.put("id", it.productId)
            itemJsonObject.put("quantity", it.quantity)
            itemJsonObject.put("price", it.price)
            itemJsonObject.put("productName", it.name)
            productJsonObjectArray.put(itemJsonObject)
        }

        orderJsonObject.put("products", productJsonObjectArray)

        val orderSummaryJsonObject = JSONObject()
        orderSummaryJsonObject.put("deliveryCharges", 0)
        orderSummaryJsonObject.put("totalAmount", priceSharedPref.getFloat("totalPrice", 0f))
        orderSummaryJsonObject.put("discount", 0)
        orderSummaryJsonObject.put("ourPrice", priceSharedPref.getFloat("totalPrice", 0f))
        orderJsonObject.put("orderSummary", orderSummaryJsonObject)

        val uploadOrderRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL$ORDER_END_POINT",
            orderJsonObject,
            {
                pd.dismiss()
                val error = it.getBoolean("error")
                if (error) {
                    Toast.makeText(baseContext, "Unable to upload order", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Successfully submit the order", Toast.LENGTH_SHORT).show()
                }
                itemDao.deleteAllItem()
                startActivity(Intent(baseContext, CategoryActivity::class.java))
            }, {
                pd.dismiss()
                it.printStackTrace()
                Toast.makeText(baseContext, "Unable to send order request", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(uploadOrderRequest)
    }

}