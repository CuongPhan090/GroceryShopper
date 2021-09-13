package com.example.groceryshopper

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopper.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEvents()
    }

    private fun setupEvents() {
        binding.btnLogin.setOnClickListener{
        }

        binding.btnRegister.setOnClickListener{
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }

        binding.btnFacebook.setOnClickListener{

        }

        binding.btnGoogle.setOnClickListener{

        }
    }
}