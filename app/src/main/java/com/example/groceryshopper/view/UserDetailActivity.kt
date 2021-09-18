package com.example.groceryshopper.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.groceryshopper.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        binding.etUserName.setText(sharedPref.getString("userName", ""))
        binding.etUserEmail.setText(sharedPref.getString("userEmail", ""))
        binding.etUserPassword.setText(sharedPref.getString("userPassword", ""))
        binding.etUserPhoneNumber.setText(sharedPref.getString("mobilePhone", ""))
        binding.etUserPassword.isEnabled = false
        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
        {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}