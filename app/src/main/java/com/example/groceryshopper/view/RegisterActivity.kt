package com.example.groceryshopper.view

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
    lateinit var firstName: String
    lateinit var mobilePhone: String
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(baseContext)
        setupEvents()
    }

    private fun isValid(): Boolean{
        if (firstName.isEmpty()) {
            binding.etFirstName.error = "Invalid first name"
            return false
        }
        if (email.isEmpty()) {
            binding.etEmail.error = "Invalid email"
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Invalid password"
            return false
        }
        if (password != confirmPassword) {
            binding.etPassword.error = "Password not matched"
            binding.etConfirmPassword.error = "Password not matched"
            return false
        }
        return true
    }
    private fun setupEvents() {
        binding.btnSignUp.setOnClickListener {
            firstName = binding.etFirstName.text.toString()
            mobilePhone = binding.etMobilePhone.text.toString()
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            confirmPassword = binding.etConfirmPassword.text.toString()

           if (!isValid()) {
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