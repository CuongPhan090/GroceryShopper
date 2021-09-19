package com.example.groceryshopper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.groceryshopper.databinding.FragmentPaymentInfoBinding

class PaymentInfoFragment : Fragment() {
    lateinit var binding: FragmentPaymentInfoBinding
    lateinit var submitPaymentListener: (Bundle) -> Unit
    val bundle = Bundle()

    fun setOnSubmitPaymentListener(listener: (Bundle) -> Unit) {
        submitPaymentListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentInfoBinding.inflate(layoutInflater, container, false)
        selectPaymentMethod()
        binding.btnSubmitPayment.setOnClickListener {
            sendPaymentInfo()
        }
        return binding.root
    }

    private fun selectPaymentMethod() {
        val spinner = binding.spinner
        val paymentList = listOf("Cash", "Credit Card", "Debit Card")
        val adapter = ArrayAdapter(
                binding.root.context,
                android.R.layout.simple_spinner_item,
                android.R.id.text1,
                paymentList
            )
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bundle.putString("paymentMethod", paymentList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinner.adapter = adapter
    }


    private fun sendPaymentInfo() {
        val cardHolder = binding.etCardHolder.text.toString()
        val cardNumber = binding.etCardNumber.text.toString()
        val expDate = binding.etExpDate.text.toString()
        val securityCode = binding.etSecurityCode.text.toString()

        bundle.putString("cardHolder", cardHolder)
        bundle.putString("cardNumber", cardNumber)
        bundle.putString("expDate", expDate)
        bundle.putString("securityCode", securityCode)

        if (this::submitPaymentListener.isInitialized) {
            if (isValidate()) {
                submitPaymentListener(bundle)
            }
        }
    }

    private fun isValidate(): Boolean{
        if (binding.etCardHolder.text.toString().isEmpty()) {
            binding.etCardHolder.error = "Invalid input"
            return false
        }
        if (binding.etCardNumber.text.toString().isEmpty()) {
            binding.etCardNumber.error = "Invalid input"
            return false
        }
        if (binding.etExpDate.text.toString().isEmpty()) {
            binding.etExpDate.error = "Invalid input"
            return false
        }
        if (binding.etSecurityCode.text.toString().isEmpty()) {
            binding.etSecurityCode.error = "Invalid input"
            return false
        }
        return true
    }
}