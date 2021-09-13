package com.example.groceryshopper.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.REGISTER_END_POINT
import com.example.groceryshopper.databinding.ActivityRegisterBinding
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(baseContext)
        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSignUp.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val mobilePhone = binding.etMobilePhone.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (firstName.isEmpty()) {
                binding.etFirstName.error = "Invalid first name"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.etEmail.error = "Invalid email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Invalid password"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                binding.etPassword.error = "Password not matched"
                binding.etConfirmPassword.error = "Password not matched"
                return@setOnClickListener
            }

            // API POST here
            val userInfo = JSONObject()
            userInfo.put("firstName", firstName)
            userInfo.put("mobile", mobilePhone)
            userInfo.put("password", password)
            userInfo.put("email", email)

            val registerRequest = JsonObjectRequest(
                Request.Method.POST,
                "$BASE_URL$REGISTER_END_POINT",
                userInfo,
                {
                    val error = it.getString("error").toBoolean()
                    if (error) {
                        Toast.makeText(baseContext, "Register failed!", Toast.LENGTH_LONG).show()
                    } else{
                        Toast.makeText(baseContext, "Register successfully!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }, {
                    it.printStackTrace()
                    Toast.makeText(baseContext, "Cannot complete the register request!", Toast.LENGTH_LONG).show()
                }
            )
            requestQueue.add(registerRequest)
        }

        binding.btnAlreadyHaveAnAccount.setOnClickListener{
            finish()
        }
    }
}