package com.example.groceryshopper.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryshopper.databinding.FragmentPaymentInfoBinding

class PaymentInfoFragment : Fragment() {
    lateinit var binding: FragmentPaymentInfoBinding
    lateinit var clickListener: (Unit) -> Unit

    fun setOnClickedListener(listener: (Unit) -> Unit) {
        clickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnSubmitPayment.setOnClickListener {
            if (this::clickListener.isInitialized) {
                clickListener
            }
        }
    }
}