package com.example.groceryshopper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.groceryshopper.databinding.FragmentShippingInfoBinding

class ShippingInfoFragment: Fragment() {
    lateinit var binding: FragmentShippingInfoBinding
    lateinit var continueClickedListener: (Bundle) -> Unit

    fun setOnChangeTabListener(listener: (Bundle) -> Unit) {
        continueClickedListener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShippingInfoBinding.inflate(layoutInflater, container, false)
        binding.btnContinue.setOnClickListener{
            sendAddressInfo()
        }
        return binding.root
    }

    private fun sendAddressInfo() {
        val bundle = Bundle()
        val pinCode = binding.etZipcode.text.toString()
        val city = binding.etCity.text.toString()
        val streetName = binding.etStreetName.text.toString()
        val houseNo = binding.etHouseNumber.text.toString()
        val type = binding.etType.text.toString()

        bundle.putString("pincode", pinCode)
        bundle.putString("city", city)
        bundle.putString("streetName", streetName)
        bundle.putString("houseNo", houseNo)
        bundle.putString("type", type)
        if (this::continueClickedListener.isInitialized) {
            if (validateInput()) {
                continueClickedListener(bundle)
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.etZipcode.text.toString().isEmpty()) {
            binding.etZipcode.error = "Invalid input"
            return false
        }
        if (binding.etCity.text.toString().isEmpty()) {
            binding.etCity.error = "Invalid input"
            return false
        }
        if (binding.etStreetName.text.toString().isEmpty()) {
            binding.etStreetName.error = "Invalid input"
            return false
        }
        if (binding.etHouseNumber.text.toString().isEmpty()) {
            binding.etHouseNumber.error = "Invalid input"
            return false
        }
        if (binding.etType.text.toString().isEmpty()) {
            binding.etType.error = "Invalid input"
            return false
        }
        return true
    }
}
