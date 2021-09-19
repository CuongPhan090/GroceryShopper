package com.example.groceryshopper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopper.databinding.HolderOrderDateBinding
import com.example.groceryshopper.databinding.HolderOrderHistoryBinding
import com.example.groceryshopper.holders.OrderDateHolder
import com.example.groceryshopper.holders.OrderHistoryHolder
import com.example.groceryshopper.models.OrderDate
import com.example.groceryshopper.models.OrderEachProduct
import java.lang.RuntimeException

class OrderHistoryAndDateAdapter(val orders: ArrayList<Any>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ORDER_DATE) {
            val layoutInflate = LayoutInflater.from(parent.context)
            val bind = HolderOrderDateBinding.inflate(layoutInflate, parent, false)
            return OrderDateHolder(bind)
        }
        if (viewType == VIEW_TYPE_ORDER_HISTORY) {
            val layoutInflate = LayoutInflater.from(parent.context)
            val bind = HolderOrderHistoryBinding.inflate(layoutInflate, parent, false)
            return OrderHistoryHolder(bind)
        }
        throw RuntimeException("Unable to create view holder")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OrderDateHolder -> {
                holder.bind(orders[position] as OrderDate)
            }
            is OrderHistoryHolder -> {
                holder.bind(orders[position] as OrderEachProduct)
            }
        }
    }

    override fun getItemCount() = orders.size

    override fun getItemViewType(position: Int): Int {
        val obj = orders[position]
        when (obj) {
            is OrderDate -> return VIEW_TYPE_ORDER_DATE
            is OrderEachProduct -> return VIEW_TYPE_ORDER_HISTORY
        }
        return super.getItemViewType(position)
    }

    companion object {
        const val VIEW_TYPE_ORDER_DATE = 100
        const val VIEW_TYPE_ORDER_HISTORY = 200
    }
}