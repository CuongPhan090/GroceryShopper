package com.example.groceryshopper.views

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.UPDATE_PROFILE_END_POINT
import com.example.groceryshopper.databinding.ActivityUserDetailBinding
import org.json.JSONObject

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    lateinit var sharedPref: SharedPreferences
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // display user current details
        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        binding.etUserName.setText(sharedPref.getString("userName", ""))
        binding.etUserEmail.setText(sharedPref.getString("userEmail", ""))
        binding.etUserPassword.setText(sharedPref.getString("userPassword", ""))
        binding.etUserPhoneNumber.setText(sharedPref.getString("mobilePhone", ""))
        binding.etUserPassword.isEnabled = false
        binding.etUserEmail.isEnabled = false
        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener {
            save()
        }
    }

    // API not support
    private fun save() {
        AlertDialog.Builder(this).apply {
            setTitle("Notification")
            setMessage("This feature has not implemented yet")
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }.show()

    // Working code
//        val userFirstName = binding.etUserName.text.toString()
//        val userMobilePhone = binding.etUserPhoneNumber.text.toString()
//        val userId = sharedPref.getString("userId", "")
//        val jsonObject = JSONObject()
//        jsonObject.put("firstName", userFirstName)
//        jsonObject.put("mobile", userMobilePhone)
//
//        Log.d("Shared", "$BASE_URL$UPDATE_PROFILE_END_POINT$userId")
//        val jsonRequest = JsonObjectRequest(
//            Request.Method.PUT,
//            "$BASE_URL$UPDATE_PROFILE_END_POINT$userId",
//            jsonObject,
//            {
//                val error = it.getBoolean("error")
//                if (error) {
//                    Toast.makeText(baseContext, "Unable to save profile", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(baseContext, "Successfully save profile", Toast.LENGTH_SHORT).show()
//                }
//                finish()
//            }, {
//                it.printStackTrace()
//                Toast.makeText(baseContext, "Unable to send save profile request", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        )
//        requestQueue.add(jsonRequest)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
        {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}