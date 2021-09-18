package com.example.groceryshopper.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopper.adapters.TabsAdapter
import com.example.groceryshopper.databinding.ActivityShippingPaymentBinding

class ShippingPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityShippingPaymentBinding
    lateinit var adapter: TabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val shippingInfoFragment = ShippingInfoFragment()
//        val paymentInfoFragment = PaymentInfoFragment()

        adapter = TabsAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

//        val fragment = supportFragmentManager.findFragmentByTag("")
//        if ( != null && )
//        supportFragmentManager.beginTransaction().replace(
//            R.id.contentPlaceHolder, shippingInfoFragment
//        ).addToBackStack("shippingInfoFragment").commit()
//
//
//        shippingInfoFragment.setOnClickedListener {
//            Toast.makeText(baseContext, "Clicked", Toast.LENGTH_SHORT).show()
//            binding.viewPager.currentItem = 2
//        }
    }
}