package com.example.groceryshopper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryshopper.databinding.FragmentUserProfileBinding

class FragmentUserProfile: Fragment() {
    lateinit var binding : FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.etUserName.setText(arguments?.getString("userName", ""))
        binding.etUserEmail.setText(arguments?.getString("userEmail", ""))
        binding.etUserPassword.setText(arguments?.getString("userPassword", ""))
        binding.etUserPhoneNumber.setText(arguments?.getString("mobilePhone", ""))

        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener{
            saveUserData()
        }
    }

    private fun saveUserData() {
        activity?.supportFragmentManager?.popBackStack()
//        parentFragmentManager.popBackStack()
    }
}
