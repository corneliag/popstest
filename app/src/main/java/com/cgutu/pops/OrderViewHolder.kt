package com.cgutu.pops

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cgutu.pops.core.model.OrderStatus

class OrderViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: OrderItem) {
        view.findViewById<TextView>(R.id.emailView).text = data.email
        view.findViewById<TextView>(R.id.dateView).text = data.date
        view.findViewById<TextView>(R.id.priceView).text = data.price
        val orderStatusView = view.findViewById<TextView>(R.id.statusView)
        orderStatusView.text = data.status

        when (data.status) {
            OrderStatus.PAID.code -> {
                orderStatusView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.status_paid
                    )
                )
            }
            OrderStatus.ERROR.code -> {
                orderStatusView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.status_error
                    )
                )
            }
            OrderStatus.DELIVERED.code -> {
                orderStatusView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.status_delivered
                    )
                )
            }
            OrderStatus.SENT.code -> {
                orderStatusView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.status_info
                    )
                )
            }
        }
    }
}