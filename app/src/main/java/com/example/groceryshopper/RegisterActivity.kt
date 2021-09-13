package com.example.groceryshopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopper.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSignUp.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val address = binding.etAddress.text.toString()
            val mobilePhone = binding.etMobilePhone.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (firstName.isEmpty()) {
                binding.etFirstName.error = "Invalid first name"
            }
            if (lastName.isEmpty()) {
                binding.etLastName.error = "Invalid last name"
            }
            if (address.isEmpty()) {
                binding.etAddress.error = "Invalid address"
            }
            if (mobilePhone.isEmpty()) {
                binding.etAddress.error = "Invalid phone number"
            }
            if (email.isEmpty()) {
                binding.etEmail.error = "Invalid email"
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Invalid password"
            }
            if (password != confirmPassword) {
                binding.etPassword.error = "Password not matched"
                binding.etConfirmPassword.error = "Password not matched"
            }

            val sharedPref = getSharedPreferences("userAuthentication", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("firstName", firstName)
            editor.putString("lastName", lastName)
            editor.putString("address", address)
            editor.putString("mobilePhone", mobilePhone)
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()
        }

        binding.btnAlreadyHaveAnAccount.setOnClickListener{
            startActivity(Intent(baseContext, LoginActivity::class.java))
        }
    }
}