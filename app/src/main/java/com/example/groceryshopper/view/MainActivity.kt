package com.example.groceryshopper.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest
import com.example.groceryshopper.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        const val LAUNCH_LOGIN_SCREEN: Int = 200
        const val LAUNCH_CATEGORY_SCREEN : Int = 300
        const val threeSeconds = 1000L * 3
    }

    private lateinit var binding : ActivityMainBinding
    lateinit var requestQueue: RequestQueue
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding. root)

        requestQueue = Volley.newRequestQueue(baseContext)
        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        if (sharedPref.contains("usersEmail")) {
            handler.sendEmptyMessageDelayed(LAUNCH_CATEGORY_SCREEN, threeSeconds)
        } else {
            handler.sendEmptyMessageDelayed(LAUNCH_LOGIN_SCREEN, threeSeconds)
        }
    }


    val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == LAUNCH_LOGIN_SCREEN) {
                startActivity(Intent(baseContext, LoginActivity::class.java))
                finish()
            }
            if (msg.what == LAUNCH_CATEGORY_SCREEN) {
                sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
                val email = sharedPref.getString("usersEmail", "")
                val password = sharedPref.getString("userPassword", "")

                val userAuthentication = JSONObject()
                userAuthentication.put("email", email)
                userAuthentication.put("password", password)

                val loginRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "${UrlRequest.BASE_URL}${UrlRequest.LOGIN_END_POINT}",
                    userAuthentication,
                    {
                        startActivity(Intent(baseContext, CategoryActivity::class.java))
                    }, {
                        it.printStackTrace()
                        Toast.makeText(baseContext, "Unable to load data", Toast.LENGTH_LONG).show()
                    }
                )
                requestQueue.add(loginRequest)
            }
        }
    }
}