package com.cgutu.pops.manager

import com.cgutu.pops.core.model.Order
import io.reactivex.Single

interface IOrdersManager {

    fun getOrders(status: String): Single<List<Order>>
}