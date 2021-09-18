package com.example.groceryshopper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryshopper.databinding.FragmentShippingInfoBinding

class ShippingInfoFragment: Fragment() {
    lateinit var binding: FragmentShippingInfoBinding
    lateinit var clickListener: (Unit) -> Unit

    fun setOnClickedListener(listener: (Unit) -> Unit) {
        clickListener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShippingInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnContinue.setOnClickListener{
            if (this::clickListener.isInitialized) {
                clickListener
            }
        }
    }
}