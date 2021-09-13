package com.example.groceryshopper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.groceryshopper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val LAUNCH_LOGIN_SCREEN: Int = 200
    val threeSeconds = 1000L * 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding. root)
        handler.sendEmptyMessageDelayed(LAUNCH_LOGIN_SCREEN, threeSeconds)
    }


    val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == LAUNCH_LOGIN_SCREEN) {
                startActivity(Intent(baseContext, LoginActivity::class.java))
                finish()
            }
        }
    }
}