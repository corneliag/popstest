package com.cgutu.pops

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter :
    ListAdapter<OrderItemRecyclerItem, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<OrderItemRecyclerItem>() {
            override fun areItemsTheSame(
                oldItem: OrderItemRecyclerItem,
                newItem: OrderItemRecyclerItem
            ): Boolean {
                if (oldItem.getType() == newItem.getType()) {
                    return oldItem.isSameItem(newItem)
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: OrderItemRecyclerItem,
                newItem: OrderItemRecyclerItem
            ): Boolean {
                return oldItem.isSameContent(newItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder(getViewInflated(parent, R.layout.viewholder_order))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (data is OrderItem) {
            holder as OrderViewHolder
            holder.bind(data)
        } else {
            throw IllegalArgumentException("Type unknown")
        }
    }

    private fun getViewInflated(parent: ViewGroup, @LayoutRes layout: Int) = LayoutInflater.from(
        parent.context
    ).inflate(layout, parent, false)
}