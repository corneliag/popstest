package com.cgutu.pops.core.model

import com.google.gson.annotations.SerializedName

class Order {

    @SerializedName("orderId")
    lateinit var oderId: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("priceFinal")
    lateinit var price: String

    @SerializedName("orderStatus")
    lateinit var status: String

    // @SerializedName("paidTime")
    //lateinit var date: Timestamp
}

enum class OrderStatus(val code: String) {
    PRINTED(code = "printed"), PAID(code = "paid"), DELIVERED(code = "delivered"), CANCELED(code = "canceled"), SENT(
        code = "sent"
    ),
    ERROR(code = "error");

    companion object {
        fun getTaskType(code: String): OrderStatus = OrderStatus.values().find { it.code == code }
            ?: throw IllegalArgumentException("The searched value is not a type of OrderStatus")
    }
}