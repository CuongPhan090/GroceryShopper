package com.example.groceryshopper.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.groceryshopper.fragments.PaymentInfoFragment
import com.example.groceryshopper.fragments.ShippingInfoFragment

class TabsAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ShippingInfoFragment()
            1 -> PaymentInfoFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Shipping Info"
            1 -> "Payment Info"
            else -> super.getPageTitle(position)
        }
    }
}